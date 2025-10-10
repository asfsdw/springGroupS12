package com.spring.springGroupS12.service;

import com.spring.springGroupS12.vo.ComplaintVO;

public interface ComplaintService {

	int setComplaintInput(ComplaintVO vo);

	void setBoardComplaintOk(int partIdx);

}
