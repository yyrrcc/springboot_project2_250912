package com.mycompany.p2.comment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long>{
	
	// 게시판 글 번호로 댓글 목록 불러오기
	public List<CommentEntity> findByBoardId(Long id);

}
