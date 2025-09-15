package com.mycompany.p2.board;

import java.time.LocalDateTime;

import com.mycompany.p2.user.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Entity
@SequenceGenerator(
		name = "PETBOARD_SEQ_GENERATOR",
		sequenceName = "PETBOARD_SEQ",
		initialValue = 1,
		allocationSize = 1
		)
@Table(name = "petboard")
public class BoardEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PETBOARD_SEQ_GENERATOR")
	private Long id; // 기본키

	@Column(length = 200)
	private String title; // 제목
	
	@Column(length = 800)
	private String content; // 내용
	
	private Integer hit; // 조회수
	
	private LocalDateTime regdate; // 작성일
	
	// 게시판:유저(N:1) 관계
	@ManyToOne
	private UserEntity writer;
}
