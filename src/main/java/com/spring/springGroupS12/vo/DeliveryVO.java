package com.spring.springGroupS12.vo;

import lombok.Data;

@Data
public class DeliveryVO {
	private int idx;
	private int parentIdx;
	private String deliveryIdx;
	private String mid;
	private String nickName;
	private String email;
	private String title;
	private int orderQuantity;
	private int price;
	private String address;
	private String productImage;
	private String deliverySW;
	private String orderDate;
	
	private String imp_uid;				// 고유ID
	private String merchant_uid;	// 상점 거래 ID
	private int paid_amount;		// 결제 금액
	private String apply_num;			// 카드 승인번호
}
