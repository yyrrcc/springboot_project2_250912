package com.mycompany.p2.comment;

import java.time.LocalDateTime;

import com.mycompany.p2.board.BoardEntity;
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

@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@SequenceGenerator(
		name = "PETCOMMENT_SEQ_GENERATOR",
		sequenceName = "PETCOMMENT_SEQ",
		initialValue = 1,
		allocationSize = 1)
@Table(name = "PETCOMMENT")
public class CommentEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PETCOMMENT_SEQ_GENERATOR")
	private Long id; // 기본키
	
	@Column(length = 300)
	private String content; // 댓글내용
	
	private LocalDateTime regdate; // 댓글작성일
	
	// 답변:게시글(N:1)
	@ManyToOne
	private BoardEntity board;
	
	// 답변:유저(N:1)
	@ManyToOne
	private UserEntity user;

}
