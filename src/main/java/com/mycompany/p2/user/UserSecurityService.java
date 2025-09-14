package com.mycompany.p2.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
//시큐리티가 로그인 시 사용할 서비스 (UserDetailsService 인터페이스 구현할 것)
public class UserSecurityService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
		Optional<UserEntity> optional = userRepository.findByUserid(userid);
		if (optional.isEmpty()) {
			throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
		}
		UserEntity user = optional.get();
		
		// 사용자의 권한 정보를 나타내는 GrantedAuthority 객체의 리스트
		List<GrantedAuthority> authorities = new ArrayList<>();
        if ("admin".equals(userid)) {
        	authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        } else {
        	authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }
        
        // 새로운 User 객체 생성해서 반환하기 (아이디, 비밀번호, 권한 리스트)
		return new User(user.getUserid(), user.getPassword(), authorities);
	}
	
	

}
