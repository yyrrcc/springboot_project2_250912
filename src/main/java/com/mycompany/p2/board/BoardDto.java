package com.mycompany.p2.board;

import java.time.LocalDateTime;

import com.mycompany.p2.user.UserEntity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
// dto에서 유효성 검증까지
public class BoardDto {
	
	private Long id; // 기본키
	
	@NotBlank(message = "제목이 공란입니다.")
	@Size(max = 50, message = "제목은 최대 50자까지 가능합니다.")
	private String title; // 제목
	
	@NotEmpty(message = "내용이 공란입니다.")
	@Size(min = 3, message = "내용은 최소 3자 이상이어야 합니다.")
	private String content; // 내용
	
	private Integer hit  = 0; // 조회수
	
	private LocalDateTime regdate; // 작성일
	
	private UserEntity writer;
	
	
	// 엔티티 변환 메서드
	public BoardEntity toEntity() {
		BoardEntity boardEntity = new BoardEntity();
		boardEntity.setId(id);
		boardEntity.setTitle(title);
		boardEntity.setContent(content);
		boardEntity.setHit(hit);
		boardEntity.setRegdate(LocalDateTime.now());
		boardEntity.setWriter(writer);
		return boardEntity;
	}
	

}
