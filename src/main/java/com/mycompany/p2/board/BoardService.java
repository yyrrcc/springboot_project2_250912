package com.mycompany.p2.board;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.p2.user.DataNotFoundException;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	
	// 글 목록
	public List<BoardEntity> getList() {
		return boardRepository.findAll();
	}
	
	// 글 작성 (유효성 검증 포함)
	public void write(BoardDto boardDto) {
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
