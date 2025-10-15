package com.spring.springGroupS12.service;

import java.util.ArrayList;
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
	public int setShoppingBag(String mid, String nickName, String email, String title, int orderQuantity, int price, String address, String productImage) {
		return deliveryDAO.setShoppingBag(mid, nickName, email, title, orderQuantity, price, address, productImage);
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

	@Override
	public void setShoppingBagLastUpdate(String idx, String orderQuantity) {
		if(idx.contains(",")) {
			String[] idxs = idx.split(",");
			String[] orderQuantitys = orderQuantity.split(",");
			for(int i=0; i<idxs[0].length(); i++) {
				deliveryDAO.setShoppingBagLastUpdate(idxs[i], orderQuantitys[i]);
			}
		}
		else deliveryDAO.setShoppingBagLastUpdate(idx, orderQuantity);
	}

	@Override
	public List<DeliveryVO> getShoppingBagLastList(String idx) {
		List<DeliveryVO> deliveryVOS = new ArrayList<DeliveryVO>();
		if(idx.contains(",")) {
			String[] idxs = idx.split(",");
			for(int i=0; i<idxs[0].length(); i++) {
				deliveryVOS.addAll(deliveryDAO.getShoppingBagLastList(idxs[i]));
			}
			return deliveryVOS;
		}
		return deliveryDAO.getShoppingBagLastList(idx);
	}

	@Override
	public void setDeliveryLastUpdate(String idx, String address, String deliverySW) {
		if(idx.contains(",")) {
			String[] idxs = idx.split(",");
			for(int i=0; i<idxs[0].length(); i++) {
				deliveryDAO.setDeliveryLastUpdate(idxs[i], address, deliverySW);
			}
		}
		else deliveryDAO.setDeliveryLastUpdate(idx, address, deliverySW);
	}

}
