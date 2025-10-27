package com.spring.springGroupS12.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.springGroupS12.vo.DeliveryVO;

public interface DeliveryDAO {
	
	List<DeliveryVO> getShoppingBagList(@Param("mid") String mid);

	int setShoppingBag(@Param("parentIdx") int parentIdx, @Param("deliveryIdx") String deliveryIdx, @Param("mid") String mid, @Param("nickName") String nickName, @Param("email") String email, @Param("title") String title, @Param("orderQuantity") int orderQuantity, @Param("price") int price, @Param("address") String address, @Param("productImage") String productImage, @Param("deliverySW") String deliverySW, @Param("point") int point);

	List<DeliveryVO> getShoppingBag(@Param("mid") String mid);
	
	List<DeliveryVO> getShoppingBagIdx(@Param("deliveryIdx") String deliveryIdx);
	
	int setShoppingBagUpdate(@Param("orderQuantity") int orderQuantity, @Param("idx") int idx);

	int setShoppingBagDelete(@Param("idx") int idx);
	
	int setShoppingBagDeleteDeliveryIdx(@Param("deliveryIdx") String deliveryIdx);

	void setShoppingBagLastUpdate(@Param("idx") String idx, @Param("orderQuantity") String orderQuantity);

	List<DeliveryVO> getShoppingBagLastList(@Param("idx") String idx);

	int setDeliveryLastUpdate(@Param("idx") String idx, @Param("dVO") DeliveryVO dVO);

	DeliveryVO getShoppingBagDuplicat(@Param("mid") String mid, @Param("title") String title, @Param("flag") String flag);

	List<DeliveryVO> getDeliveryListDeliveryIdx(@Param("deliveryIdx") String deliveryIdx);

	List<DeliveryVO> getDeliveryListDeliveryMid(@Param("mid") String mid);

	List<DeliveryVO> getDeliveryListDeliveryDelivery(@Param("deliverySW") String deliverySW);

	int setDeliverySWUpdate(@Param("deliveryIdx") String deliveryIdx, @Param("deliverySW") String deliverySW);

	List<DeliveryVO> getDeliveryListMain(@Param("mid") String mid);

	List<DeliveryVO> getNewDelivery();

}
