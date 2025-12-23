package com.spring.springGroupS12.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.springGroupS12.vo.ShopVO;

public interface ShopDAO {

	int getTotRecCnt(@Param("flag") String flag, @Param("search") String search, @Param("searchStr") String searchStr);

	int setProductInput(@Param("vo") ShopVO vo);

	List<ShopVO> getProductList(@Param("startIndexNo") int startIndexNo, @Param("pageSize") int pageSize, @Param("kategorie") String kategorie);

	ShopVO getProduct(@Param("idx") int idx);

	List<ShopVO> getProductSubList(@Param("mid") String mid);

	ShopVO getProductProductImage(@Param("productImage") String productImage);

	void setProductQuantityUpdate(@Param("idx") int idx, @Param("orderQuantity") int orderQuantity);

	void setProductQuantityRollback(@Param("idx") int idx, @Param("orderQuantity") int orderQuantity);

	int setProductOpenSWUpdate(@Param("idx") int idx, @Param("openSW") String openSW);

	int setProductSubDelete(@Param("idx") int idx);

	List<ShopVO> getProductListHome();

	int setProductUpdate(@Param("vo") ShopVO vo);

	List<ShopVO> getNewProductSub();

	List<ShopVO> getNewProduct();

	int setProductUpdateAdmin(@Param("vo") ShopVO vo);

	List<ShopVO> getProductListAdmin(@Param("startIndexNo") int startIndexNo, @Param("pageSize") int pageSize, @Param("openSW") String openSW);

}
