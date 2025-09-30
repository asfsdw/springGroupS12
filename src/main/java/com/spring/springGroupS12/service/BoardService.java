package com.spring.springGroupS12.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.springGroupS12.vo.BoardVO;
import com.spring.springGroupS12.vo.FileVO;

public interface BoardService {

	int getTotRecCnt(String flag, String search, String searchStr);

	List<BoardVO> getBoardList();
	
	int setBoardInput(BoardVO vo);
	
	int uploadBoardInput(MultipartHttpServletRequest mFile, BoardVO vo, FileVO fVO);

	BoardVO getBoard(int idx);

}
