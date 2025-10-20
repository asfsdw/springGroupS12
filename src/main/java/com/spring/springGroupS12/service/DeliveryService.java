package com.spring.springGroupS12.service;

import java.util.List;

import com.spring.springGroupS12.vo.DeliveryVO;

public interface DeliveryService {
	
	List<DeliveryVO> getShoppingBagList(String mid);

	int setShoppingBag(int parentIdx, String deliveryIdx, String mid, String nickName, String email, String title, int orderQuantity, int price, String address, String productImage, String deliverySW);

	List<DeliveryVO> getShoppingBag(String mid);
	
	List<DeliveryVO> getShoppingBagIdx(String deliveryIdx);

	int setShoppingBagUpdate(int orderQuantity, int idx);

	int setShoppingBagDelete(int idx);

	int setShoppingBagDeleteDeliveryIdx(String deliveryIdx);
	
	void setShoppingBagLastUpdate(String idx, String orderQuantity);

	List<DeliveryVO> getShoppingBagLastList(String idx);

	int setDeliveryLastUpdate(String idx, DeliveryVO dVO);

	DeliveryVO getShoppingBagDuplicat(String mid, String title, String flag);

	List<DeliveryVO> getDeliveryListDeliveryIdx(String deliveryIdx);

	List<DeliveryVO> getDeliveryListDeliveryMid(String mid);

	List<DeliveryVO> getDeliveryListDeliveryDelivery(String deliverySW);

	int setDeliverySWUpdate(String deliveryIdx, String deliverySW);

	List<DeliveryVO> getDeliveryListMain(String mid);

}
