package com.mycompany.p2.comment;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.p2.board.BoardEntity;
import com.mycompany.p2.user.DataNotFoundException;
import com.mycompany.p2.user.UserEntity;

@Service
public class CommentService {
	
	@Autowired
	private CommentRepository commentRepository;
	
	// 댓글 작성 (질문글 번호, 답변 내용, 글쓴이)
	public void write(BoardEntity board, String content, UserEntity user) {	
		CommentEntity commentEntity = new CommentEntity();
		commentEntity.setUser(user);
		commentEntity.setBoard(board);
		commentEntity.setContent(content);
		commentEntity.setRegdate(LocalDateTime.now());
		commentRepository.save(commentEntity);
	}
	
	// 댓글 목록
	public List<CommentEntity> getComment(Long id) {
		List<CommentEntity> comments = commentRepository.findByBoardId(id);
		return comments;
	}

}
