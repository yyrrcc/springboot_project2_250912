package com.mycompany.p2.comment;

import java.time.LocalDateTime;

import com.mycompany.p2.board.BoardEntity;
import com.mycompany.p2.user.UserEntity;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
// dto + 유효성 검증
public class CommentDto {

	private Long id; // 기본키
	
	@NotBlank(message = "내용이 공란입니다.")
	private String content; // 댓글내용
	
	private LocalDateTime regdate; // 댓글작성일
	
	// 답변:게시글(N:1)
	private BoardEntity board;
	
	// 답변:유저(N:1)
	private UserEntity user;
	
}
