package com.spring.springGroupS12.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.spring.springGroupS12.vo.FileVO;
import com.spring.springGroupS12.vo.ShopVO;

public interface ShopService {

	int getTotRecCnt(String flag, String search, String searchStr);

	int setProductImage(MultipartFile fName, ShopVO vo, FileVO fVO);

	List<ShopVO> getProductList();

	ShopVO getProduct(int idx);

	List<ShopVO> getProductSubList(String mid);

	ShopVO getProductProductImage(String productImage);

	void setProductQuantityUpdate(int idx, int orderQuantity);

	void setProductQuantityRollback(int idx, int orderQuantity);

	int setProductOpenSWUpdate(int idx, String openSW);

	int setProductSubDelete(int idx);

}
