package com.mycompany.p2.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	// 회원가입 및 비밀번호 암호화
	public void signup(UserSignupValid signupValid) {
		// 새로운 엔티티 객체 생성 
		UserEntity userEntity = new UserEntity();
		
		userEntity.setUserid(signupValid.getUserid());
		String crtptPassword = passwordEncoder.encode(signupValid.getPassword1());
		userEntity.setPassword(crtptPassword);
		userEntity.setUsername(signupValid.getUsername());
		userEntity.setPet(signupValid.getPet());
		userEntity.setEmail(signupValid.getEmail());
		
		userRepository.save(userEntity);
	}
	
	// userid로 정보 조회
	public UserEntity findByUserid(String userid) {
		Optional<UserEntity> optional = userRepository.findByUserid(userid);
		if (optional.isPresent()) {
			UserEntity userEntity = optional.get();
			return userEntity;
		} else {
			throw new DataNotFoundException("해당 유저를 찾을 수 없습니다");
		}
	}
	
	// 회원 탈퇴
	@Transactional
	public void deleteByUserid(String userid) {
		userRepository.deleteByUserid(userid);
	}
	
	

}
