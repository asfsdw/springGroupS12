package com.spring.springGroupS12.dao;

import org.apache.ibatis.annotations.Param;

import com.spring.springGroupS12.vo.ComplaintVO;

public interface ComplaintDAO {

	int setComplaintInput(@Param("vo") ComplaintVO vo);

	void setBoardComplaintOk(@Param("partIdx") int partIdx);

}
