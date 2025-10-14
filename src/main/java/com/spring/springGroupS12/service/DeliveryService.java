package com.spring.springGroupS12.service;

import java.util.List;

import com.spring.springGroupS12.vo.DeliveryVO;

public interface DeliveryService {
	
	List<DeliveryVO> getShoppingBagList(String mid);

	int setShoppingBag(String mid, String title, String nickName, int orderQuantity, int price, String productImage);

	DeliveryVO getShoppingBag(String mid, String title);

	int setShoppingBagUpdate(int orderQuantity, int idx);

	int setShoppingBagDelete(int idx);

}
