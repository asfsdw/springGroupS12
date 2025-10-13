package com.spring.springGroupS12.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.spring.springGroupS12.common.Pagination;
import com.spring.springGroupS12.service.DeliveryService;
import com.spring.springGroupS12.service.FileService;
import com.spring.springGroupS12.service.ShopService;
import com.spring.springGroupS12.vo.DeliveryVO;
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
	@Autowired
	FileService fileService;
	@Autowired
	DeliveryService deliveryService;
	
	// 상품 리스트.
	@GetMapping("/Goods")
	public String GoodsGet(Model model, PageVO pVO) {
		pVO.setSection("shop");
		pVO = pagination.pagination(pVO);
		List<ShopVO> vos = shopService.getProductList();
		
		model.addAttribute("pVO", pVO);
		model.addAttribute("vos", vos);
		return "shop/goods";
	}
	
	// 상품 등록 관련.
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
		vo.setOpenSW("공개");
		res = shopService.setProductImage(fName, vo, fVO);
		
		if(res != 0) return "redirect:/Message/productOk";
		else return "redirect:/Message/productNo";
	}
	// 상품 등록 신청 폼.
	@GetMapping("/ProductAddSub")
	public String ProductAddSubGet() {
		return "shop/productAdd";
	}
	//상품 등록 신청.
	@PostMapping("/ProductAddSub")
	public String ProductAddSubPost(MultipartFile fName, ShopVO vo, FileVO fVO) {
		// 백엔드 검사.
		if(vo.getMid().length() > 20 || vo.getMid() == null) return "redirect:/Message/wrongAccess";
		if(vo.getNickName().length() > 20 || vo.getNickName() == null) return "redirect:/Message/wrongAccess";
		if(vo.getKategorie().length() > 10 || vo.getKategorie() == null) return "redirect:/Message/wrongAccess";
		if(vo.getTitle().length() > 30 || vo.getTitle() == null) return "redirect:/Message/wrongAccess";
		String pwd = passwordEncoder.encode(vo.getPwd());
		if(pwd.length() > 256 || vo.getPwd() == null) return "redirect:/Message/wrongAccess";
		
		if(fName.getOriginalFilename().equals("")) return "redirect:/Message/wrongAccess";
		
		// 상품 등록 신청.
		int res = 0;
		vo.setPwd(pwd);
		vo.setOpenSW("신청");
		res = shopService.setProductImage(fName, vo, fVO);
		
		if(res != 0) return "redirect:/Message/productSubOk";
		else return "redirect:/Message/productNo";
	}
	
	// 상품 상세보기.
	@GetMapping("/Product")
	public String productGet(Model model, ShopVO vo) {
		vo = shopService.getProduct(vo.getIdx());
		
		model.addAttribute("vo", vo);
		return "shop/product";
	}
	
	@GetMapping("/ShoppingBag")
	public String ShoppingBagGet(HttpSession session, Model model) {
		List<DeliveryVO> vos = deliveryService.getShoppingBagList(session.getAttribute("sMid").toString());
		
		model.addAttribute("vos", vos);
		return "shop/shoppingBag";
	}
	@ResponseBody
	@PostMapping("/ShoppingBag")
	public int ShoppingBagPost(@RequestParam(name = "mid", defaultValue = "", required = false)String mid,
			@RequestParam(name = "nickName", defaultValue = "", required = false)String nickName,
			@RequestParam(name = "idx", defaultValue = "", required = false)int idx,
			@RequestParam(name = "orderQuantity", defaultValue = "", required = false) int orderQuantity) {
		ShopVO sVO = shopService.getProduct(idx);
		
		// 장바구니에 같은 상품이 있으면 수량만 증가.
		DeliveryVO dVO = deliveryService.getShoppingBag(mid, sVO.getTitle());
		if(dVO != null) {
			return deliveryService.setShoppingBagUpdate(orderQuantity, dVO.getIdx());
		}
		
		return deliveryService.setShoppingBag(mid, nickName,sVO.getTitle(), orderQuantity, sVO.getPrice());
	}
}
