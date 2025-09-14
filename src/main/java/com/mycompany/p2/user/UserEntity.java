package com.mycompany.p2.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SequenceGenerator(
		name = "PETUSER_SEQ_GENERATOR",
		sequenceName = "PETUSER_SEQ",
		initialValue = 1,
		allocationSize = 1
		)
@Table(name = "petuser")
public class UserEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PETUSER_SEQ_GENERATOR")
	private Long id; // 기본키
	
	@Column(unique = true)
	private String userid; // 아이디
	
	private String password; // 비밀번호
	
	private String username; // 이름
	
	private String pet; // 반려동물 여부
	
	@Column(unique = true)
	private String email; // 이메일
	
	@Enumerated(EnumType.STRING)
	@Column(name = "userlevel")
	private UserLevel level = UserLevel.LEVEL1; // 회원등급 (기본값 level1)

}
