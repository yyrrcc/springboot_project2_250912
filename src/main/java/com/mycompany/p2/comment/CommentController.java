package com.mycompany.p2.comment;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mycompany.p2.user.UserEntity;
import com.mycompany.p2.user.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/comment")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	@Autowired
	private UserService userService;
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping(value = "/add")
	public String write(@Valid CommentDto commentDto, BindingResult result, Principal principal) {
		if (result.hasErrors()) {
			return "boardDetail";
		}
		Long id = commentDto.getBoard().getId();
		UserEntity userEntity = userService.findByUserid(principal.getName());
		commentDto.setUser(userEntity);
		commentService.write(commentDto);
		return String.format("redirect:/board/view/%s", id);
	}
	
}
