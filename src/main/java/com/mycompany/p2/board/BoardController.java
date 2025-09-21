package com.mycompany.p2.board;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mycompany.p2.comment.CommentEntity;
import com.mycompany.p2.comment.CommentService;
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
	@Autowired
	private CommentService commentService;

	// ******** 로그인한 유저의 아이디를 통해서 해당 유저의 엔티티를 가져오기
	public UserEntity getLoginUserEntity(Principal principal) {
		UserEntity userEntity = userService.findByUserid(principal.getName());
		return userEntity;
	}
	
	// 글 목록
//	@GetMapping(value = "/list")
//	public String list(Model model) {
//		List<BoardEntity> boardLists = boardService.getBoardLists();
//		model.addAttribute("boardLists", boardLists);
//		return "boardList";
//	}
	
	// 글 목록 + (페이징 구현 예정)
	@GetMapping(value = "/list")
	//@PageableDefault(page = 1) : page는 기본적으로 1페이지를 보여준다
	public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "kw", defaultValue = "") String kw) {
		Page<BoardEntity> paging = boardService.getBoardLists(page, kw);
		model.addAttribute("paging", paging);
		model.addAttribute("kw", kw); // 검색어
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
		boardService.create(boardDto);
		return "redirect:/board/list";
	}
	
	// 글 상세보기 (댓글 추가)
	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/view/{id}")
	public String view(@PathVariable("id") Long id, Model model, Principal principal) {
		BoardEntity board = boardService.view(id);
		List<CommentEntity> comments = commentService.getComment(id);
		UserEntity user = getLoginUserEntity(principal); // 메서드 만들어서 사용함
		model.addAttribute("board", board);
		model.addAttribute("comments", comments);
		model.addAttribute("user", user);
		return "boardDetail";
	}
	
	// 글 수정하기 폼
	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/edit/{id}")
	// 엔티티가 아닌 valid(dto)를 불러올 것
	public String edit(@PathVariable("id") Long id, BoardDto boardDto) {
		BoardEntity boardEntity = boardService.view(id);
		boardDto.setId(boardEntity.getId());
		boardDto.setWriter(boardEntity.getWriter());
		boardDto.setTitle(boardEntity.getTitle());
		boardDto.setContent(boardEntity.getContent());
		return "boardEdit";
	}
	
	// 글 수정한 후
	@PreAuthorize("isAuthenticated()")
	@PostMapping(value = "/edit/{id}")
	public String edit(@PathVariable("id") Long id, @Valid BoardDto boardDto,
			BindingResult result, Principal principal, Model model) {
		if (result.hasErrors()) {
			//model.addAttribute("board", boardService.view(id));
			model.addAttribute("board", boardDto);
			return "boardEdit";
		}		
		BoardEntity board = boardService.view(id);
		boardService.edit(board, boardDto.getTitle(), boardDto.getContent());
		return String.format("redirect:/board/view/%s", id);
	}
	
	// 글 삭제
	@PreAuthorize("isAuthenticated()")
	@PostMapping(value = "/delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		boardService.delete(id);
		return "redirect:/board/list";
	}
	
	// 게시글 추천
	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/like/{id}")
	public String like(@PathVariable("id") Long id, Principal principal) {
		BoardEntity boardEntity = boardService.view(id);
		UserEntity userEntity = userService.findByUserid(principal.getName());
		boardService.like(boardEntity, userEntity);
		return String.format("redirect:/board/view/%s", id);
	}

}
