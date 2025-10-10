package com.spring.springGroupS12.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.springGroupS12.vo.FileVO;

public interface FileDAO {

	int setFile(@Param("fVO") FileVO fVO);

	FileVO getFile(@Param("part") String part, @Param("parentIdx") int parentIdx);

	int getParentIdx(@Param("flag") String flag);

	List<FileVO> getFileList(@Param("part") String part);

	int setFileUpdate(@Param("fVO") FileVO fVO);

}
