package com.mycompany.p2.comment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.p2.user.DataNotFoundException;

@Service
public class CommentService {
	
	@Autowired
	private CommentRepository commentRepository;
	
	// 댓글 작성
	public void write(CommentDto commentDto) {	
		CommentEntity commentEntity = commentDto.toEntity();
		commentRepository.save(commentEntity);
	}
	
	// 댓글 목록
	public List<CommentEntity> getComment(Long id) {
		List<CommentEntity> comments = commentRepository.findByBoardId(id);
		return comments;
		
	}

}
