package com.spring.springGroupS12.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.springGroupS12.dao.FileDAO;
import com.spring.springGroupS12.vo.FileVO;

@Service
public class FileServiceImpl implements FileService {
	@Autowired
	FileDAO fileDAO;

	@Override
	public FileVO getFile(int parentIdx) {
		return fileDAO.getFile(parentIdx);
	}
}
