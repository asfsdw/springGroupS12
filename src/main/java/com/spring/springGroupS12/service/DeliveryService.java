package com.spring.springGroupS12.service;

import java.util.List;

import com.spring.springGroupS12.vo.DeliveryVO;

public interface DeliveryService {
	
	List<DeliveryVO> getShoppingBagList(String mid);

	int setShoppingBag(String mid, String nickName, String email, String title, int orderQuantity, int price, String address, String productImage);

	DeliveryVO getShoppingBag(String mid, String title);

	int setShoppingBagUpdate(int orderQuantity, int idx);

	int setShoppingBagDelete(int idx);

	void setShoppingBagLastUpdate(String idx, String orderQuantity);

	List<DeliveryVO> getShoppingBagLastList(String idx);

	void setDeliveryLastUpdate(String idx, String address, String deliverySW);

}
