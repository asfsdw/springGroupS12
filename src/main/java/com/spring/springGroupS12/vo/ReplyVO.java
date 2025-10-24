package com.spring.springGroupS12.vo;

import lombok.Data;

@Data
public class ReplyVO {
	private int idx;
	private int parentIdx;
	private String part;
	private String mid;
	private String nickName;
	private String content;
	private int reStep;
	private int reOrder;
	private int star;
	private String hostIP;
	private String replyDate;
}
