package com.mycompany.p2.reservation;

import java.time.LocalDateTime;
import java.util.List;

import com.mycompany.p2.comment.CommentEntity;
import com.mycompany.p2.user.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@SequenceGenerator(
		name = "PETRESERVATION_SEQ_GENERATOR",
		sequenceName = "PETRESERVATION_SEQ",
		initialValue = 1,
		allocationSize = 1
		)
@Table(name = "petreservation")
public class ReservationEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PETRESERVATION_SEQ_GENERATOR")
	private Long id; // 기본키
	
	@NotBlank(message = "보호자 이름은 필수 항목입니다.")
	private String ownername; // 보호자 이름
	
	@NotBlank(message = "반려동물 이름은 필수 항목입니다.")
	private String petname; // 반려동물 이름
	
	private LocalDateTime resdate; // 예약날짜
	
	private String restime; // 예약시간
	
	@Column(length = 300)
	private String symptom; // 증상 설명
	
	// 예약:유저(N:1)
	@ManyToOne
	private UserEntity user;

}
