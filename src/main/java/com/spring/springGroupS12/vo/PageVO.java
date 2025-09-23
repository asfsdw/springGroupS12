package com.spring.springGroupS12.vo;

import lombok.Data;

@Data
public class PageVO {
	private int pag;
	private int pageSize;
	private int totRecCnt;
	private int totPage;
	private int startIndexNo;
	private int curScrStartNo;
	private int blockSize;
	private int curBlock;
	private int lastBlock;
	
	private String section;		// 방명록, 게시판, 자료실 어떤 곳에서 쓰는지.
	private String part;			// 학습, 여행, 음식 등 말머리.
	private String search;		// 글제목, 글쓴이, 글내용 등
	private String searchStr;	// 검색어.
	private String searchKr;
	private String flag;			// flag는 totRecCnt에서 조건을 줄 때 사용한다(7일 이내 새글 등).
}