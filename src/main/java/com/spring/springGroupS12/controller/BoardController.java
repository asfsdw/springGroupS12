package com.spring.springGroupS12.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.springGroupS12.common.Pagination;
import com.spring.springGroupS12.common.SecurityUtil;
import com.spring.springGroupS12.service.BoardService;
import com.spring.springGroupS12.service.ComplaintService;
import com.spring.springGroupS12.service.FileService;
import com.spring.springGroupS12.service.ReplyService;
import com.spring.springGroupS12.vo.BoardVO;
import com.spring.springGroupS12.vo.ComplaintVO;
import com.spring.springGroupS12.vo.FileVO;
import com.spring.springGroupS12.vo.PageVO;
import com.spring.springGroupS12.vo.ReplyVO;

@SuppressWarnings("unchecked")
@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	BoardService boardService;
	@Autowired
	Pagination pagination;
	@Autowired
	FileService fileService;
	@Autowired
	ReplyService replyService;
	@Autowired
	ComplaintService complaintService;
	
	// 게시판 전체글.
	@GetMapping("/BoardList")
	public String boardListGet(Model model, PageVO pVO, BoardVO vo) {
		pVO.setSection("board");
		pVO = pagination.pagination(pVO);
		
		List<BoardVO> vos = boardService.getBoardList(pVO.getStartIndexNo(), pVO.getPageSize(), "", "");
		
		model.addAttribute("pVO", pVO);
		model.addAttribute("vos", vos);
		return "board/boardList";
	}
	@GetMapping("/BoardBest")
	public String boardBestGet(Model model, PageVO pVO, BoardVO vo) {
		pVO.setSection("boardBest");
		pVO = pagination.pagination(pVO);
		
		List<BoardVO> vos = boardService.getBoardBest(pVO.getStartIndexNo(), pVO.getPageSize(), "", "");
		
		model.addAttribute("pVO", pVO);
		model.addAttribute("vos", vos);
		model.addAttribute("btnSW", "on");
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
		// 제목에 대하여 html 태그를 사용할 수 없도록 처리.
		String title = vo.getTitle();
		title = title.replace("<", "&lt");
		title = title.replace(">", "&gt");
		vo.setTitle(title);
		
		// 비밀글이면 비밀번호 암호화처리.
		if(vo.getOpenSW().equals("비공개")) {
			String salt = UUID.randomUUID().toString().substring(0, 4);
			SecurityUtil securityUtil = new SecurityUtil();
			vo.setPwd(salt+securityUtil.encryptSHA256(salt+vo.getPwd()));
		}
		
		// 파일 이름이 넘어오지 않았거나 글 내용에 src가 없으면(업로드할 파일이 존재하지 않으면).
		if(fName.equals("") && !vo.getContent().contains("src=\"/")) res = boardService.setBoardInput(vo);
		// 파일 이름이 넘어왔으면.
		else res = boardService.setUploadBoardInput(mFile, vo, fVO);
		
		pVO.setSection("board");
		pVO = pagination.pagination(pVO);
		model.addAttribute("pVO", pVO);
		if(res != 0) return "redirect:/Message/boardInputOk";
		else return "redirect:/Message/boardInputNo";
	}
	
	// 글보기.
	@GetMapping("/BoardContent")
	public String boardContentGet(Model model, HttpSession session, PageVO pVO, BoardVO vo, FileVO fVO, ReplyVO reVO,
			@RequestParam(name = "password", defaultValue = "", required = false)String password) {
		vo = boardService.getBoard(vo.getIdx());
		if(vo.getOpenSW().equals("비공개")) {
			if(password.equals("")) return "redirect:/Message/pwdInputNo";
			else {
				SecurityUtil securityUtil = new SecurityUtil();
				String salt = vo.getPwd().substring(0,4);
				if(!vo.getPwd().equals(salt+securityUtil.encryptSHA256(salt+password))) return "redirect:/Message/boardPwdNo";
			}
		}
		
		// 조회수 증가.
		List<String> contentView = (List<String>)session.getAttribute("sContentIdx");
		if(contentView == null) contentView = new ArrayList<String>();
		String contentViewTemp = "board"+session.getAttribute("sMid")+vo.getIdx();
		if(!contentView.contains(contentViewTemp)) {
			boardService.setContentView(vo.getIdx());
			contentView.add(contentViewTemp);
		}
		session.setAttribute("sContentIdx", contentView);
		
		vo = boardService.getBoard(vo.getIdx());
		fVO = fileService.getFile("board", vo.getIdx());
		List<ReplyVO> reVOS = replyService.getReply(vo.getIdx());
		
		model.addAttribute("pVO", pVO);
		model.addAttribute("vo", vo);
		model.addAttribute("fVO", fVO);
		model.addAttribute("reVOS", reVOS);
		return "board/boardContent";
	}
	//좋아요, 싫어요 처리.
	@ResponseBody
	@PostMapping("/GoodCheckPlusMinus")
	public int goodCheckPlusMinusPost(HttpSession session, int idx, int goodCnt) {
		// 세션에 저장한 조회수 증가 리스트 객체 불러온다.
		List<String> contentView = (List<String>)session.getAttribute("sContentIdx");
		if(contentView == null) contentView = new ArrayList<String>();
		String contentViewTemp = "boardGood"+session.getAttribute("sMid")+idx;
		if(!contentView.contains(contentViewTemp)) {
			boardService.setGood(idx, goodCnt);
			contentView.add(contentViewTemp);
			return 1;
		}
		return 0;
	}
	
	// 게시글 수정 관련.
	@GetMapping("/BoardUpdate")
	public String boardUpdateGet(Model model, PageVO pVO, int idx) {
		pVO.setSection("board");
		pVO = pagination.pagination(pVO);
		
		BoardVO vo = boardService.getBoard(idx);
		// 원본 글의 이미지 파일 복사(board => ckeditor).
		if(vo.getContent().indexOf("src=\"/") != -1) fileService.imgBackup(vo.getContent());
		
		// 원본 글에 업로드한 파일이 존재하는지.
		FileVO fVO = fileService.getFile("board", idx);
		
		model.addAttribute("vo", vo);
		model.addAttribute("fVO", fVO);
		model.addAttribute("pVO", pVO);
		
		return "board/boardUpdate";
	}
	// 게시글 수정.
	@PostMapping("/BoardUpdate")
	public String boardUpdatePost(HttpServletResponse response, MultipartHttpServletRequest mFile, Model model, PageVO pVO, BoardVO vo, FileVO fVO,
			@RequestParam(name = "fName", defaultValue = "", required = false)String fName) {
		pVO.setSection("board");
		pVO = pagination.pagination(pVO);
		
		int res = 0;
		// 제목에 대하여 html 태그를 사용할 수 없도록 처리.
		String title = vo.getTitle();
		title = title.replace("<", "&lt");
		title = title.replace(">", "&gt");
		vo.setTitle(title);
		
		// 비밀글이면 비밀번호 암호화하고 비밀번호 업데이트.
		if(vo.getOpenSW().equals("비공개")) {
			String salt = UUID.randomUUID().toString().substring(0, 4);
			SecurityUtil securityUtil = new SecurityUtil();
			vo.setPwd(salt+securityUtil.encryptSHA256(salt+vo.getPwd()));
			
			// 비밀번호 업데이트.
			res = boardService.setBoardUpdate(vo);
		}
		
		// 수정된 title과 content가 원본자료와 완전히 동일하다면 파일을 수정할 필요가 없다.
		BoardVO originVO = boardService.getBoard(vo.getIdx());
		if(!vo.getTitle().equals(originVO.getTitle()) && !vo.getContent().equals(originVO.getContent())) {
			// 원본 이미지 삭제(업로드보다 삭제가 빠르기 때문에 삭제 먼저).
			if(originVO.getContent().indexOf("src=\"/") != -1) fileService.imgDelete("board", originVO.getContent());
			// 원본 파일 삭제.
			if((fVO = fileService.getFile("board", vo.getIdx())) != null) fileService.fileRemove("board", fVO);
			
			// 파일 이름이 넘어오지 않았거나 글 내용에 src가 없으면(업로드할 파일이 존재하지 않으면).
			if(fName.equals("") && !vo.getContent().contains("src=\"/")) res = boardService.setBoardUpdate(vo);
			// 파일 이름이 넘어왔으면.
			else res = boardService.setUpdateBoardInput(mFile, vo, fVO);
		}
		
		model.addAttribute("idx", vo.getIdx());
		model.addAttribute("pag", pVO.getPag());
		model.addAttribute("pageSize", pVO.getPageSize());
		model.addAttribute("search", pVO.getSearch());
		model.addAttribute("searchStr", pVO.getSearchStr());
		
		if(res != 0) return "redirect:/Message/boardUpdateOk";
		else return "redirect:/Message/boardUpdateNo";
	}
	
	// 게시글 삭제.
	@GetMapping("/BoardDelete")
	public String boardDeleteGet(Model model, PageVO pVO, BoardVO vo, FileVO fVO) {
		vo = boardService.getBoard(vo.getIdx());
		
		// 게시글에 src가 존재하면 삭제.
		if(vo.getContent().indexOf("src=\"/") != -1) fileService.imgDelete("board", vo.getContent());
		// 게시글에 첨부파일이 존재하면 삭제.
		if((fVO = fileService.getFile("board", vo.getIdx())) != null) fileService.fileRemove("board", fVO);
		
		// src를 삭제한 후, DB에서 게시글 삭제.
		int res = boardService.setBoardDelete(vo.getIdx());
		
		model.addAttribute("idx", vo.getIdx());
		model.addAttribute("pag", pVO.getPag());
		model.addAttribute("pageSize", pVO.getPageSize());
		model.addAttribute("search", pVO.getSearch());
		model.addAttribute("searchStr", pVO.getSearchStr());
		
		if(res != 0) return "redirect:/Message/boardDeleteOk";
		else return "redirect:/Message/boardDeleteNo";
	}
	
	//게시글 신고.
	@ResponseBody
	@PostMapping("/BoardComplaint")
	public int boardComplaintPost(ComplaintVO vo) {
		int res = 0;
		res = complaintService.setComplaintInput(vo);
		if(res != 0) complaintService.setBoardComplaintOk(vo.getPartIdx());
		return res;
	}
	
	// 게시글 검색.
	@GetMapping("/BoardSearchList")
	public String boardSearchListPost(Model model, PageVO pVO) {
		pVO.setSection("board");
		pVO = pagination.pagination(pVO);
		
		List<BoardVO> vos = boardService.getBoardList(pVO.getStartIndexNo(), pVO.getPageSize(), pVO.getSearch(), pVO.getSearchStr());
		
		model.addAttribute("vos", vos);
		model.addAttribute("pVO", pVO);
		
		return "board/boardSearchList";
	}
}