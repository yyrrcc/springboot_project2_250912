package com.mycompany.p2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity (prePostEnabled = true)
public class SecurityConfig {
	
	@Bean // 스프링 시큐리티 (인증되지 않은 모든 페이지 요청 허락, 로그인 및 로그아웃 설정)
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(authorize 
				-> authorize.requestMatchers("/**").permitAll())
		.formLogin(formLogin -> formLogin
				.loginPage("/user/login")
				.defaultSuccessUrl("/", true)
				.usernameParameter("userid") // 폼에서 아이디 input name="userid"일 때
		        .passwordParameter("password")) // password input name="password"일 때
		.logout(logout -> logout
				.logoutUrl("/user/logout")
				.logoutSuccessUrl("/")
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				);
		return http.build();
	}
	
	@Bean // 비밀번호 암호화
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean // 스프링 시큐리티의 인증 처리
	AuthenticationManager authenticationManager
	(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

}
