package com.mycompany.p2.board;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.mycompany.p2.user.UserController;
import com.mycompany.p2.user.UserEntity;
import com.mycompany.p2.user.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;
	@Autowired
	private UserService userService;

	
	// 글 목록
	@GetMapping(value = "/list")
	public String list(Model model) {
		List<BoardEntity> boardLists = boardService.getList();
		model.addAttribute("boardLists", boardLists);
		return "boardList";
	}
	
	// 글 작성 폼
	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/write")
	public String write(BoardDto boardDto, Principal principal) {
		// 로그인 된 유저의 아이디를 통해 엔티티 가져오기
		UserEntity userEntity = userService.findByUserid(principal.getName()); // principal.getName() : 로그인한 유저의 아이디를 가져옴
		boardDto.setWriter(userEntity);
		return "boardWrite";
	}
	
	// 글 작성 후
	@PreAuthorize("isAuthenticated()")
	@PostMapping(value = "/write")
	public String write(@Valid BoardDto boardDto, BindingResult result, Principal principal) {
		if (result.hasErrors()) {
			return "boardWrite";
		}
		UserEntity userEntity = userService.findByUserid(principal.getName());
		boardDto.setWriter(userEntity);
		boardService.write(boardDto);
		return "redirect:/board/list";
	}
	
	// 글 상세보기
	@GetMapping(value = "/view")
	public String view() {
		return "boardDetail";
	}
	
	@GetMapping(value = "/edit")
	public String edit() {
		return "boardEdit";
	}

}
