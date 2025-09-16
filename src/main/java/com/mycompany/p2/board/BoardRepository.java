package com.mycompany.p2.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long>{
	
	// 페이징 : 글 목록 불러오기
	Page<BoardEntity> findAll(Pageable pageable);
	// 페이징 : 글 목록 + 검색결과
	Page<BoardEntity> findAll(Specification<BoardEntity> spec, Pageable pageable);



}
