package com.mycompany.p2.comment;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mycompany.p2.board.BoardEntity;
import com.mycompany.p2.board.BoardService;
import com.mycompany.p2.user.UserEntity;
import com.mycompany.p2.user.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/comment")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	@Autowired
	private BoardService boardService;
	@Autowired
	private UserService userService;
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping(value = "/add/{id}")
	public String comAdd(@PathVariable("id") Long id, @Valid CommentDto commentDto, BindingResult result, Principal principal) {
		if (result.hasErrors()) {
			return "boardDetail";
		}
		BoardEntity board = boardService.view(id); // 질문글 가져오기
		UserEntity user = userService.findByUserid(principal.getName()); // 로그인한 유저 엔티티 가져오기
		commentDto.setUser(user);
		commentService.write(board, commentDto.getContent(), user);
		return String.format("redirect:/board/view/%s", id);
	}
	
}
