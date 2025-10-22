package com.spring.springGroupS12.service;

import java.util.List;

import com.spring.springGroupS12.vo.ComplaintVO;

public interface ComplaintService {
	
	ComplaintVO getComplaintIdx(int idx);

	int setComplaintInput(ComplaintVO vo);

	List<ComplaintVO> getComplaintList();

	int setComplaintProgressUpdate(int idx, String progress);

	int setComplaintDelete(int idx);

	int setComplaintParentUpdate(String part, int partIdx, String flag);

	ComplaintVO getComplaintPartIdx(int partIdx, String part);

}
