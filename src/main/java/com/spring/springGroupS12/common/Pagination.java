package com.spring.springGroupS12.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.springGroupS12.service.BoardService;
import com.spring.springGroupS12.service.MemberService;
import com.spring.springGroupS12.service.ShopService;
import com.spring.springGroupS12.vo.PageVO;

@Service
public class Pagination {
	@Autowired
	MemberService memberService;
	@Autowired
	BoardService boardService;
	@Autowired
	ShopService shopService;
	
	public PageVO pagination(PageVO vo) {
		vo.setSection(vo.getSection() == null ? "" : vo.getSection());
		vo.setPart(vo.getPart() == null ? "전체" : vo.getPart());
		vo.setSearch(vo.getSearch());
		vo.setSearchStr(vo.getSearchStr());
		vo.setFlag(vo.getFlag() == null ? "" : vo.getFlag());
		
		if(vo.getSearch() != null) {
			if(vo.getSearch().equals("title")) vo.setSearchKr("글제목");
			else if(vo.getSearch().equals("nickName")) vo.setSearchKr("닉네임");
			else if(vo.getSearch().equals("content")) vo.setSearchKr("글내용");
		}
		
		// 아무 값도 안 줬을 때 기본값이 0이기 때문에 삼항연산자의 조건을 0으로 준다.
		vo.setPag(vo.getPag()==0 ? 1 : vo.getPag());
		vo.setPageSize(vo.getPageSize()==0 ? 10 : vo.getPageSize());
		vo.setTotRecCnt(vo.getTotRecCnt() == 0 ? 1 : vo.getTotRecCnt());
		
		if(vo.getSection().equals("member")) vo.setTotRecCnt(memberService.getTotRecCnt(vo.getFlag()));
		else if(vo.getSection().equals("board")) {
			if(vo.getSearch() == null) vo.setTotRecCnt(boardService.getTotRecCnt(vo.getFlag(), "", ""));
			else vo.setTotRecCnt(boardService.getTotRecCnt(vo.getFlag(), vo.getSearch(), vo.getSearchStr()));
		}
		else if(vo.getSection().equals("boardBest")) {
			if(vo.getSearch() == null) vo.setTotRecCnt(boardService.getTotRecCnt("best", "", ""));
			else vo.setTotRecCnt(boardService.getTotRecCnt(vo.getFlag(), vo.getSearch(), vo.getSearchStr()));
		}
		else if(vo.getSection().equals("shop")) {
			if(vo.getSearch() == null) vo.setTotRecCnt(shopService.getTotRecCnt(vo.getFlag(), "", ""));
			else vo.setTotRecCnt(shopService.getTotRecCnt(vo.getFlag(), vo.getSearch(), vo.getSearchStr()));
		}
		/*
		else if(vo.getSection().equals("admin")) {
			if(vo.getSearch() == null) vo.setTotRecCnt(adminService.getTotRecCnt(vo.getFlag(), "", ""));
			else vo.setTotRecCnt(adminService.getTotRecCnt(vo.getFlag(), vo.getSearch(), vo.getSearchStr()));
		}
		*/
		
		vo.setTotPage((int)Math.ceil((double)vo.getTotRecCnt()/vo.getPageSize()));
		vo.setStartIndexNo((vo.getPag()-1) * vo.getPageSize());
		vo.setCurScrStartNo(vo.getTotRecCnt() - vo.getStartIndexNo());
		
		vo.setBlockSize(3);
		vo.setCurBlock((vo.getPag()-1)/vo.getBlockSize());
		vo.setLastBlock((vo.getTotPage()-1)/vo.getBlockSize());
		
		return vo;
	}
}
