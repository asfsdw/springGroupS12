package com.spring.springGroupS12.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.springGroupS12.dao.ComplaintDAO;
import com.spring.springGroupS12.vo.ComplaintVO;

@Service
public class ComplaintServiceImpl implements ComplaintService {
	@Autowired
	ComplaintDAO complaintDAO;
	
	@Override
	public ComplaintVO getComplaintIdx(int idx) {
		return complaintDAO.getComplaintIdx(idx);
	}

	@Override
	public int setComplaintInput(ComplaintVO vo) {
		return complaintDAO.setComplaintInput(vo);
	}

	@Override
	public List<ComplaintVO> getComplaintList(String progress) {
		return complaintDAO.getComplaintList(progress);
	}

	@Override
	public int setComplaintProgressUpdate(int idx, String progress) {
		return complaintDAO.setComplaintProgressUpdate(idx, progress);
	}

	@Override
	public int setComplaintDelete(int idx) {
		return complaintDAO.setComplaintDelete(idx);
	}

	@Override
	public int setComplaintParentUpdate(String part, int partIdx, String flag) {
		return complaintDAO.setComplaintParentUpdate(part, partIdx, flag);
	}

	@Override
	public ComplaintVO getComplaintPartIdx(int partIdx, String part) {
		return complaintDAO.getComplaintPartIdx(partIdx, part);
	}
}
