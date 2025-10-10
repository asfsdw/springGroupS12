package com.spring.springGroupS12.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.springGroupS12.vo.BoardVO;
import com.spring.springGroupS12.vo.FileVO;

public interface BoardService {

	int getTotRecCnt(String flag, String search, String searchStr);

	List<BoardVO> getBoardList(int startIndexNo, int pageSize, String search, String searchStr);
	
	BoardVO getBoard(int idx);
	
	int setBoardInput(BoardVO vo);
	
	int setUploadBoardInput(MultipartHttpServletRequest mFile, BoardVO vo, FileVO fVO);

	void setGood(int idx, int goodCnt);

	void setContentView(int idx);

	List<BoardVO> getBoardBest(int startIndexNo, int pageSize, String search, String searchStr);

	int setBoardUpdate(BoardVO vo);

	int setUpdateBoardInput(MultipartHttpServletRequest mFile, BoardVO vo, FileVO fVO);

	int setBoardDelete(int idx);

}
