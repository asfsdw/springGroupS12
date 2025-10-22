package com.spring.springGroupS12.vo;

import lombok.Data;

@Data
public class ComplaintVO {
	private int idx;
	private String part;
	private int partIdx;
	private String parentTitle;
	private String cpMid;
	private String cpContent;
	private String cpDate;
	private String progress;
	
	private String title;
	private String nickName;
	private String mid;
	private String content;
	private String complaint; // board 테이블의 NO, OK.
	private String complaintSW;	// S(신고각하), H(신고허용), D(글삭제).
	
	private int newCount;
	private String flag;
}
