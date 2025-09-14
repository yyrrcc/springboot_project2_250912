package com.mycompany.p2.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
//	private UserSignupValid userSignupValid;
	
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

	
}
