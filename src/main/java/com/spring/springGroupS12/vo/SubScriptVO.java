package com.spring.springGroupS12.vo;

import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class SubScriptVO {
	private int idx;
	private String mid;
	private String nickName;
	@Size(min=10, message="신청내용은 최소 10글자 이상 쓰셔야합니다.")
	private String subContent;
	private String subDate;
	private String subProgress;
	
	private String levelUp;
	private String subEtc;
	
	private String title;
	private String shopDate;
	private String openSW;
}
