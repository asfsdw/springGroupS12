package com.spring.springGroupS12.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.springGroupS12.vo.DeliveryVO;

public interface DeliveryDAO {
	
	List<DeliveryVO> getShoppingBagList(@Param("mid") String mid);

	int setShoppingBag(@Param("mid") String mid, @Param("nickName") String nickName, @Param("title") String title, @Param("orderQuantity") int orderQuantity, @Param("price") int price, @Param("productImage") String productImage);

	DeliveryVO getShoppingBag(@Param("mid") String mid, @Param("title") String title);

	int setShoppingBagUpdate(@Param("orderQuantity") int orderQuantity, @Param("idx") int idx);

	int setShoppingBagDelete(@Param("idx") int idx);

}
