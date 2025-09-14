package com.mycompany.p2.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	
	private Long id; // 기본키
	private String userid; // 아이디
	private String password; // 비밀번호
	private String username; // 이름
	private String pet; // 반려동물 여부
	private String email; // 이메일
	private UserLevel level; // 회원등급
	
	public UserDto(String userid, String password, String username, String pet, String email) {
		super();
		this.userid = userid;
		this.password = password;
		this.username = username;
		this.pet = pet;
		this.email = email;
	}
	
	

}
