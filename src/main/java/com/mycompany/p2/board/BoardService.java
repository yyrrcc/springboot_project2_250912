package com.mycompany.p2.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	
	// 글 목록
	public List<BoardEntity> getList() {
		return boardRepository.findAll();
	}
	
	// 글 작성
	public void write(BoardDto boardDto) {
		BoardEntity boardEntity = boardDto.toEntity();
		boardRepository.save(boardEntity);
	}

}
