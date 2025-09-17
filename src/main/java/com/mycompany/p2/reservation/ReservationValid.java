package com.mycompany.p2.reservation;

import java.time.LocalDate;

import com.mycompany.p2.user.UserEntity;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
// dto 겸 유효성 체크
public class ReservationValid {
	
	private Long id; // 기본키
	
	@NotBlank(message = "보호자 이름은 필수 항목입니다.")
	private String ownername; // 보호자 이름
	
	@NotBlank(message = "반려동물 이름은 필수 항목입니다.")
	private String petname; // 반려동물 이름
	
	private LocalDate resdate; // 예약날짜
	
	private String restime; // 예약시간
	
	private String symptom; // 증상 설명
	
	// 예약:유저(N:1)
	private UserEntity user;

}
