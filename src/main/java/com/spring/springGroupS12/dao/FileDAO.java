package com.spring.springGroupS12.dao;

import org.apache.ibatis.annotations.Param;

import com.spring.springGroupS12.vo.FileVO;

public interface FileDAO {

	int setFile(@Param("fVO") FileVO fVO);

	FileVO getFile(@Param("parentIdx") int parentIdx);

	int getParentIdx(@Param("flag") String flag);

}
