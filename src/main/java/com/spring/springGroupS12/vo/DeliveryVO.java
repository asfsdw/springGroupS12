package com.spring.springGroupS12.vo;

import lombok.Data;

@Data
public class DeliveryVO {
	private int idx;
	private String mid;
	private String nickName;
	private String title;
	private int orderQuantity;
	private int price;
	private String address;
	private String deliverySW;
	private String orderDate;
}
