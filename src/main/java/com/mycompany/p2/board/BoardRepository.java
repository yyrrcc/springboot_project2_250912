package com.mycompany.p2.board;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.mycompany.p2.user.UserEntity;


public interface BoardRepository extends JpaRepository<BoardEntity, Long>{
	
	// 아이디로 글 목록 조회하기?
	public List<BoardEntity> findByWriter(UserEntity writer);

}
