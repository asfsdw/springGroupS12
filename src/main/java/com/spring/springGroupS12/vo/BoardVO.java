package com.spring.springGroupS12.vo;

import lombok.Data;

@Data
public class BoardVO {
	private int idx;
	private String mid;
	private String nickName;
	private String title;
	private String content;
	private String pwd;
	private String openSW;
	private int views;
	private int good;
	private String complaint;
	private String boardDate;
	
	private int hourDiff;	// 새글 알림을 위해 24시간인지 확인하기 위한 변수.
	private int dateDiff;	// 날짜 출력을 위해 0, 1, …출력.
	private int replyCnt; // 게시글의 달린 댓글 개수 가져오는 변수.
	
	private String cpContent; // 관리자 메뉴에서 신고 이유를 보기 위한 변수.
	private String cpDate;		// 관리자 메뉴에서 신고 날짜를 보기 위한 변수.
}
