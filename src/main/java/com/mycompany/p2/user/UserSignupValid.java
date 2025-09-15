package com.mycompany.p2.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

// 회원가입 유효성 검증
@Getter @Setter
public class UserSignupValid {
	
	@NotBlank(message = "아이디는 필수 항목입니다.")
	@Size (min = 3, max = 20, message = "아이디는 3자 이상 20자 이하입니다.")
	private String userid; // 아이디
	
	@NotBlank(message = "비밀번호 필수 항목입니다.")
	@Size (min = 3, message = "비밀번호는 최소 3자 이상이어야 합니다.")
	private String password1; // 비밀번호
	
	@NotBlank(message = "비밀번호 확인은 필수 항목입니다.")
	private String password2; // 비밀번호 확인
	
	@NotBlank(message = "이름은 필수 항목입니다.")
	private String username; // 이름
	
	@NotEmpty(message = "반려동물 선택은 필수 항목입니다.")
	private String pet; // 반려동물 여부
	
	@NotBlank(message = "이메일은 필수 항목입니다.")
	@Email
	private String email; // 이메일

}
