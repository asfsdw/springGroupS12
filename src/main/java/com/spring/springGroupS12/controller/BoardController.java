package com.spring.springGroupS12.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.springGroupS12.common.Pagination;
import com.spring.springGroupS12.service.BoardService;
import com.spring.springGroupS12.vo.BoardVO;
import com.spring.springGroupS12.vo.FileVO;
import com.spring.springGroupS12.vo.PageVO;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	BoardService boardService;
	@Autowired
	Pagination pagination;
	
	// 게시판 전체글.
	@GetMapping("/BoardList")
	public String boardListGet(Model model, PageVO pVO, BoardVO vo) {
		pVO.setSection("board");
		pVO = pagination.pagination(pVO);
		
		List<BoardVO> vos = boardService.getBoardList();
		
		model.addAttribute("pVO", pVO);
		model.addAttribute("vos", vos);
		return "board/boardList";
	}
	
	// 글쓰기 관련.
	@GetMapping("/BoardInput")
	public String boardInputGet() {
		return "board/boardInput";
	}
	// 글 작성.
	@PostMapping("/BoardInput")
	public String boardInputPost(MultipartHttpServletRequest mFile, Model model,PageVO pVO,BoardVO vo, FileVO fVO,
			@RequestParam(name = "fName", defaultValue = "", required = false)String fName) {
		int res = 0;
		// 파일 이름이 넘어오지 않았으면(업로드할 파일이 존재하지 않으면).
		if(fName.equals("")) res = boardService.setBoardInput(vo);
		// 파일 이름이 넘어왔으면.
		else res = boardService.uploadBoardInput(mFile, vo, fVO);
		
		pVO.setSection("board");
		pVO = pagination.pagination(pVO);
		model.addAttribute("pVO", pVO);
		if(res != 0) return "redirect:/Message/boardInputOk";
		else return "redirect:/Message/boardInputNo";
	}
	
	// 글보기.
	@GetMapping("/BoardContent")
	public String boardContentGet(Model model, PageVO pVO, BoardVO vo) {
		vo = boardService.getBoard(vo.getIdx());
		model.addAttribute("pVO", pVO);
		model.addAttribute("vo", vo);
		return "board/boardContent";
	}
}