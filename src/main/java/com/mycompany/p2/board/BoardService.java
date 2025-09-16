package com.mycompany.p2.board;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.mycompany.p2.comment.CommentEntity;
import com.mycompany.p2.user.DataNotFoundException;
import com.mycompany.p2.user.UserEntity;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	
	// 글 목록
//	public List<BoardEntity> getBoardLists() {
//		return boardRepository.findAll();
//	}
	
	// 모든 글 목록 + 페이징 (1페이지 당 3개 보이고, 페이지 블럭은 5개씩)
	public Page<BoardEntity> getBoardLists(int page, String kw) {
		int pageSize = 10; // 1페이지 당 보이는 게시글 수
		Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Direction.DESC, "id")); // 현재 페이지, 1페이지 당 보이는 게시글 수, id(기본키로) 내림차순 정렬
		Specification<BoardEntity> spec = search(kw);
		// 검색어 없이 리스트 조회
		//Page<BoardEntity> allPage = boardRepository.findAll(pageable);
		// 모든 글 개수 가져오기
		//long totalpage = boardRepository.count();
		// Page 구현체 이용하기
		//Page<BoardEntity> getBoard = new PageImpl<>(allPage, pageable, totalpage);
		return boardRepository.findAll(spec, pageable);
	}
	
	
	// 검색 기능 메서드
	private Specification<BoardEntity> search(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<BoardEntity> b, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);  // 중복을 제거 
                Join<BoardEntity, UserEntity> u1 = b.join("writer", JoinType.LEFT);
                Join<BoardEntity, CommentEntity> a = b.join("commentList", JoinType.LEFT);
                Join<CommentEntity, UserEntity> u2 = a.join("user", JoinType.LEFT);
                return cb.or(cb.like(b.get("title"), "%" + kw + "%"), // 제목 
                        cb.like(b.get("content"), "%" + kw + "%"),      // 내용 
                        cb.like(u1.get("username"), "%" + kw + "%"),    // 질문 작성자 
                        cb.like(a.get("content"), "%" + kw + "%"),      // 답변 내용 
                        cb.like(u2.get("username"), "%" + kw + "%"));   // 답변 작성자 
            }
        };
    }
	
	
	
	// 글 작성 (유효성 검증 포함)
	public void create(BoardDto boardDto) {
		BoardEntity boardEntity = boardDto.toEntity();
		boardRepository.save(boardEntity);
	}
	
	// 기본키 이용해서 글 상세보기
	public BoardEntity view(Long id) {
		Optional<BoardEntity> optional = boardRepository.findById(id);
		if (optional.isPresent()) {
			BoardEntity boardEntity = optional.get();
			updateHit(boardEntity);
			return boardEntity;
		} else {
			throw new DataNotFoundException("해당 글을 찾을 수 없습니다.");
		}
	}
	
	// 게시글 조회수 증가
	public void updateHit(BoardEntity boardEntity) {
		boardEntity.setHit(boardEntity.getHit() + 1);
		boardRepository.save(boardEntity);
	}
	
	// 글 수정하기
	public void edit(BoardEntity boardEntity, String title, String content) {
		boardEntity.setTitle(title);
		boardEntity.setContent(content);
		boardEntity.setEditdate(LocalDateTime.now());
		boardRepository.save(boardEntity);
	}
	
	// 게시글 삭제
	public void delete(Long id) {
		boardRepository.deleteById(id);
	}
	
	

}
