package com.spring.springGroupS12.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.springGroupS12.dao.DeliveryDAO;
import com.spring.springGroupS12.vo.DeliveryVO;

@Service
public class DeliveryServiceImpl implements DeliveryService {
	@Autowired
	DeliveryDAO deliveryDAO;
	
	@Override
	public List<DeliveryVO> getShoppingBagList(String mid) {
		return deliveryDAO.getShoppingBagList(mid);
	}

	@Override
	public int setShoppingBag(String mid, String nickName, String title, int orderQuantity, int price, String productImage) {
		return deliveryDAO.setShoppingBag(mid, nickName, title, orderQuantity, price, productImage);
	}

	@Override
	public DeliveryVO getShoppingBag(String mid, String title) {
		return deliveryDAO.getShoppingBag(mid, title);
	}

	@Override
	public int setShoppingBagUpdate(int orderQuantity, int idx) {
		return deliveryDAO.setShoppingBagUpdate(orderQuantity, idx);
	}

	@Override
	public int setShoppingBagDelete(int idx) {
		return deliveryDAO.setShoppingBagDelete(idx);
	}
}
