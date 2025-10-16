package com.spring.springGroupS12.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
	public int setShoppingBag(int parentIdx, String deliveryIdx, String mid, String nickName, String email, String title, int orderQuantity, int price, String address, String productImage, String deliverySW) {
		return deliveryDAO.setShoppingBag(parentIdx, deliveryIdx, mid, nickName, email, title, orderQuantity, price, address, productImage, deliverySW);
	}

	@Override
	public DeliveryVO getShoppingBag(String mid) {
		return deliveryDAO.getShoppingBag(mid);
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
			for(int i=0; i<idxs.length; i++) {
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
			for(int i=0; i<idxs.length; i++) {
				deliveryVOS.addAll(deliveryDAO.getShoppingBagLastList(idxs[i]));
			}
			return deliveryVOS;
		}
		return deliveryDAO.getShoppingBagLastList(idx);
	}

	@Override
	public int setDeliveryLastUpdate(String idx, DeliveryVO dVO) {
		int res = 0;
		String address = dVO.getAddress();
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String deliveryIdx = sdf.format(today)+UUID.randomUUID().toString().substring(0, 8);
		
		if(idx.contains(",")) {
			String[] idxs = idx.split(",");
			String[] titles = dVO.getTitle().split(",");
			for(int i=0; i<idxs.length; i++) {
				dVO = deliveryDAO.getShoppingBagDuplicat(dVO.getMid(), titles[i]);
				dVO.setDeliveryIdx(deliveryIdx);
				dVO.setAddress(address);
				dVO.setDeliverySW("준비중");
				
				res = deliveryDAO.setDeliveryLastUpdate(idxs[i], dVO);
			}
		}
		else {
			dVO = deliveryDAO.getShoppingBagDuplicat(dVO.getMid(), dVO.getTitle());
			dVO.setDeliveryIdx(deliveryIdx);
			dVO.setAddress(address);
			dVO.setDeliverySW("준비중");
			
			res = deliveryDAO.setDeliveryLastUpdate(idx, dVO);
		}
		
		return res;
	}

	@Override
	public DeliveryVO getShoppingBagDuplicat(String mid, String title) {
		return deliveryDAO.getShoppingBagDuplicat(mid, title);
	}

}
