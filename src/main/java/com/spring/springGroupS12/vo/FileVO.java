package com.spring.springGroupS12.vo;

import lombok.Data;

@Data
public class FileVO {
	private int idx;
	private int parentIdx;
	private String part;
	private String oFileName;
	private String sFileName;
	private String fileSize;
}
