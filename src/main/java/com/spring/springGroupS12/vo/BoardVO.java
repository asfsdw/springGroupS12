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
	private int views;
	private int good;
	private String openSW;
	private String complaint;
	private String boardDate;
}
