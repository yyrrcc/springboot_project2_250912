package com.mycompany.p2.user;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	// 회원가입 폼
	@GetMapping(value = "/signup")
	public String signup(Model model) {
		model.addAttribute("userSignupValid", new UserSignupValid());
		return "signup";
	}
	
	// 회원가입
	@PostMapping(value = "/signup")
	public String signup(@Valid UserSignupValid userSignupValid, BindingResult result, Model model) {
		if (!userSignupValid.getPassword1().equals(userSignupValid.getPassword2())) {
			result.rejectValue("password2", "pwIncorrect", "비밀번호 확인이 일치하지 않습니다.");
		}
		if (result.hasErrors()) {
			model.addAttribute("userSignupValid", userSignupValid);
			return "signup";
		}
		try {
			userService.signup(userSignupValid);
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			result.reject("signupError", "이미 사용중인 아이디나 이메일입니다.");
			model.addAttribute("userSignupValid", userSignupValid);
			return "signup";
		} catch (Exception e) {
			e.printStackTrace();
			result.reject("signupError", "회원가입 실패입니다.");
			model.addAttribute("userSignupValid", userSignupValid);
			return "signup";
		}
		return "redirect:/user/login";
	}
	
	// 로그인 (로그인 처리는 스프링 시큐리티에서 해줌)
	@GetMapping(value = "/login")
	public String login() {
		return "login";
	}

	// 마이페이지
	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/mypage")
	// 엔티티가 아닌 dto(valid)를 이용할 것! 엔티티로 일단 정보 가져온 후 dto(valid) 이용
	public String mypage(UserSignupValid userSignupValid, Model model, Principal principal) {
		UserEntity userEntity = userService.findByUserid(principal.getName());
		if (!userEntity.getUserid().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		userSignupValid.setUserid(userEntity.getUserid());
		userSignupValid.setUsername(userEntity.getUsername());
		userSignupValid.setPet(userEntity.getPet());
		userSignupValid.setEmail(userEntity.getEmail());
		return "mypage";
	}
	
	// 마이페이지 수정
	@PreAuthorize("isAuthenticated()")
	@PostMapping(value = "/mypage/update")
	public String mypageUpdate(@Valid UserSignupValid userSignupValid, BindingResult result, Model model, Principal principal) {
		if (!userSignupValid.getPassword1().equals(userSignupValid.getPassword2())) {
			result.rejectValue("password2", "pwIncorrect", "비밀번호 확인이 일치하지 않습니다.");
		}
		if (result.hasErrors()) {
			model.addAttribute("userSignupValid", userSignupValid);
			return "mypage";
		}
		try {
			userService.update(principal.getName(), userSignupValid);
			// 회원정보 수정 성공 자바스크립트 하고 싶음
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			result.reject("updateError", "이미 사용중인 이메일입니다.");
			model.addAttribute("userSignupValid", userSignupValid);
			return "mypage";
		} catch (Exception e) {
			e.printStackTrace();
			result.reject("updateError", "회원 정보 수정 실패입니다.");
			model.addAttribute("userSignupValid", userSignupValid);
			return "mypage";
		}
		return "redirect:/user/mypage";
	}
	
	
	// 회원탈퇴
	@PostMapping(value = "/mypage/delete")
	public String delete(Principal principal, HttpServletRequest request, HttpServletResponse response) {
		userService.deleteByUserid(principal.getName());
		// 세션 무효화 및 SecurityContext 초기화
	    SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
	    logoutHandler.logout(request, response, SecurityContextHolder.getContext().getAuthentication());
	    // 안전하게 SecurityContext 비우기
		SecurityContextHolder.clearContext();
		return "redirect:/";
	}
	
}
