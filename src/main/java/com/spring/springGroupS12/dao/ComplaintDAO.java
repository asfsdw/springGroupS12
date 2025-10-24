package com.spring.springGroupS12.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.springGroupS12.vo.ComplaintVO;

public interface ComplaintDAO {
	
	ComplaintVO getComplaintIdx(@Param("idx") int idx);

	int setComplaintInput(@Param("vo") ComplaintVO vo);

	List<ComplaintVO> getComplaintList(@Param("progress") String progress);

	int setComplaintProgressUpdate(@Param("idx") int idx, @Param("progress") String progress);

	int setComplaintDelete(@Param("idx") int idx);

	int setComplaintParentUpdate(@Param("part") String part, @Param("partIdx") int partIdx, @Param("flag") String flag);

	ComplaintVO getComplaintPartIdx(@Param("partIdx") int partIdx, @Param("part") String part);

}
