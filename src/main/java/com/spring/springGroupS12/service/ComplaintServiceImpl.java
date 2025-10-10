package com.spring.springGroupS12.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.springGroupS12.dao.ComplaintDAO;
import com.spring.springGroupS12.vo.ComplaintVO;

@Service
public class ComplaintServiceImpl implements ComplaintService {
	@Autowired
	ComplaintDAO complaintDAO;

	@Override
	public int setComplaintInput(ComplaintVO vo) {
		return complaintDAO.setComplaintInput(vo);
	}

	@Override
	public void setBoardComplaintOk(int partIdx) {
		complaintDAO.setBoardComplaintOk(partIdx);
	}
}
