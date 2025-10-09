package com.spring.springGroupS12.dao;

import org.apache.ibatis.annotations.Param;

import com.spring.springGroupS12.vo.ShopVO;

public interface ShopDAO {

	int getTotRecCnt(@Param("flag") String flag, @Param("search") String search, @Param("searchStr") String searchStr);

	int setProductInput(@Param("vo") ShopVO vo);

}
