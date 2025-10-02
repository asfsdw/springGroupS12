package com.spring.springGroupS12.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.springGroupS12.vo.BoardVO;

public interface BoardDAO {
	
	int getTotRecCnt(@Param("flag") String flag, @Param("search") String search, @Param("searchStr") String searchStr);
	
	List<BoardVO> getBoardList(@Param("startIndexNo") int startIndexNo, @Param("pageSize") int pageSize, @Param("search") String search, @Param("searchStr") String searchStr);

	BoardVO getBoard(@Param("idx") int idx);
	
	int getParentIdx();
	
	int setBoardInput(@Param("vo") BoardVO vo);

	void setGood(@Param("idx") int idx, @Param("goodCnt") int goodCnt);

	void contentView(@Param("idx") int idx);

	List<BoardVO> getBoardBest(@Param("startIndexNo") int startIndexNo, @Param("pageSize") int pageSize, @Param("search") String search, @Param("searchStr") String searchStr);

}
