package com.spring.springGroupS12.service;

import java.util.List;

import com.spring.springGroupS12.vo.FileVO;

public interface FileService {

	FileVO getFile(String part, int parentIdx);

	List<FileVO> getFileList(String part);

	void imgBackup(String content);

	void imgDelete(String part, String content);

	void fileRemove(String part, FileVO fVO);

}
