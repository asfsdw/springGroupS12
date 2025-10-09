package com.spring.springGroupS12.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.spring.springGroupS12.common.Pagination;
import com.spring.springGroupS12.service.ShopService;
import com.spring.springGroupS12.vo.FileVO;
import com.spring.springGroupS12.vo.PageVO;
import com.spring.springGroupS12.vo.ShopVO;

@Controller
@RequestMapping("/shop")
public class ShopController {
	@Autowired
	ShopService shopService;
	@Autowired
	Pagination pagination;
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	// 상품 리스트.
	@GetMapping("/Goods")
	public String GoodsGet(Model model, PageVO pVO, ShopVO vo) {
		pVO.setSection("shop");
		pVO = pagination.pagination(pVO);
		model.addAttribute("pVO", pVO);
		return "shop/goods";
	}
	
	// 상품 등록 폼 이동.
	@GetMapping("/ProductAdd")
	public String ProductAddGet() {
		return "shop/productAdd";
	}
	// 상품 등록.
	@PostMapping("/ProductAdd")
	public String ProductAddPost(MultipartFile fName, ShopVO vo, FileVO fVO) {
		// 백엔드 검사.
		if(vo.getMid().length() > 20 || vo.getMid() == null) return "redirect:/Message/wrongAccess";
		if(vo.getNickName().length() > 20 || vo.getNickName() == null) return "redirect:/Message/wrongAccess";
		if(vo.getKategorie().length() > 10 || vo.getKategorie() == null) return "redirect:/Message/wrongAccess";
		if(vo.getTitle().length() > 30 || vo.getTitle() == null) return "redirect:/Message/wrongAccess";
		String pwd = passwordEncoder.encode(vo.getPwd());
		if(pwd.length() > 256 || vo.getPwd() == null) return "redirect:/Message/wrongAccess";
		
		if(fName.getOriginalFilename().equals("")) return "redirect:/Message/wrongAccess";
		
		// 상품 등록.
		int res = 0;
		vo.setPwd(pwd);
		if(vo.getContent().contains("src=\"/")) res = shopService.setProductImage(fName, vo, fVO);
		if(res != 0) return "redirect:/Message/productOk";
		else return "redirect:/Message/productNo";
	}
}
