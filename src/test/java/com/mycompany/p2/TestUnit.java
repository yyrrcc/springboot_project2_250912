package com.mycompany.p2;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mycompany.p2.board.BoardEntity;
import com.mycompany.p2.board.BoardRepository;
import com.mycompany.p2.user.UserRepository;


@SpringBootTest
public class TestUnit {
	
	@Autowired
	private BoardRepository boardRepository;
	@Autowired
	UserRepository userRepository;
	

	@Test
	@DisplayName("더미넣기")
	void testJpa() {
        for (int i = 1; i <= 30; i++) {
        	BoardEntity boardEntity = new BoardEntity();
            String subject = String.format("테스트 데이터입니다:[%03d]", i);
            String content = "테스트중입니다.";
            
            boardEntity.setContent(content);
            boardEntity.setTitle(subject);
            boardEntity.setWriter(userRepository.findById(1L).get());
            boardEntity.setHit(0);
            boardEntity.setRegdate(LocalDateTime.now());
        
            boardRepository.save(boardEntity);
            
        }
    }

}
