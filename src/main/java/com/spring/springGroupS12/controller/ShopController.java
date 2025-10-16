package com.spring.springGroupS12.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
import com.spring.springGroupS12.service.MemberService;
import com.spring.springGroupS12.service.ShopService;
import com.spring.springGroupS12.vo.DeliveryVO;
import com.spring.springGroupS12.vo.FileVO;
import com.spring.springGroupS12.vo.MemberVO;
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
	@Autowired
	MemberService memberService;
	
	// 상품 리스트.
	@GetMapping("/Goods")
	public String goodsGet(Model model, PageVO pVO) {
		pVO.setSection("shop");
		pVO = pagination.pagination(pVO);
		List<ShopVO> vos = shopService.getProductList();
		
		model.addAttribute("pVO", pVO);
		model.addAttribute("vos", vos);
		return "shop/goods";
	}
	
	// 상품 등록 관련.
	@GetMapping("/ProductAdd")
	public String productAddGet() {
		return "shop/productAdd";
	}
	// 상품 등록.
	@PostMapping("/ProductAdd")
	public String productAddPost(MultipartFile fName, ShopVO vo, FileVO fVO) {
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
	public String productAddSubGet() {
		return "shop/productAdd";
	}
	//상품 등록 신청.
	@PostMapping("/ProductAddSub")
	public String productAddSubPost(MultipartFile fName, ShopVO vo, FileVO fVO) {
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
	
	// 상품 구매.
	@PostMapping("/Product")
	public String ProductPost(Model model, DeliveryVO dVO,
			@RequestParam(name = "idxs", defaultValue = "", required = false)String idxs,
			@RequestParam(name = "orderQuantitys", defaultValue = "", required = false)String orderQuantitys) {
		if(!dVO.getMid().equals("")) {
			DeliveryVO searchdVO = deliveryService.getShoppingBag(dVO.getMid());
			if(searchdVO != null) {
				// 장바구니에서 변경한 구매개수 반영.
				deliveryService.setShoppingBagLastUpdate(idxs, orderQuantitys);
				
				// 장바구니 안의 상품 리스트.
				List<DeliveryVO> deliveryVOS = deliveryService.getShoppingBagLastList(idxs);
				
				model.addAttribute("deliveryVOS", deliveryVOS);
			}
			else {
				MemberVO mVO = memberService.getMemberMid(dVO.getMid());
				dVO.setNickName(mVO.getNickName());
				dVO.setEmail(mVO.getEmail());
				dVO.setAddress(mVO.getAddress());
				
				model.addAttribute("dVO", dVO);
			}
		}
		else {
			dVO.setMid("noMember");
			model.addAttribute("dVO", dVO);
		}
		return "shop/productBuy";
	}
	
	// 장바구니 관련.
	@GetMapping("/ShoppingBag")
	public String shoppingBagGet(Model model,
			@RequestParam(name = "mid", defaultValue = "", required = false)String mid) {
		List<DeliveryVO> vos = deliveryService.getShoppingBagList(mid);
		
		if(vos.size() == 0) return "redirect:/Message/cartEmpty";
		
		model.addAttribute("vos", vos);
		return "shop/shoppingBag";
	}
	// 장바구니 등록.
	@ResponseBody
	@PostMapping("/ShoppingBag")
	public int shoppingBagPost(@RequestParam(name = "mid", defaultValue = "", required = false)String mid,
			@RequestParam(name = "nickName", defaultValue = "", required = false)String nickName,
			@RequestParam(name = "idx", defaultValue = "", required = false)int idx,
			@RequestParam(name = "orderQuantity", defaultValue = "", required = false) int orderQuantity) {
		MemberVO vo = memberService.getMemberMid(mid);
		ShopVO sVO = shopService.getProduct(idx);
		
		// 장바구니에 같은 상품이 있으면 수량만 증가.
		DeliveryVO dVO = deliveryService.getShoppingBagDuplicat(mid, sVO.getTitle());
		if(dVO != null) return deliveryService.setShoppingBagUpdate(orderQuantity, dVO.getIdx());
		
		return deliveryService.setShoppingBag(idx, "", mid, vo.getNickName(), vo.getEmail(), sVO.getTitle(), orderQuantity, sVO.getPrice(), vo.getAddress(), sVO.getProductImage(), "대기중");
	}
	// 장바구니 상품 삭제.
	@ResponseBody
	@PostMapping("/ShoppingBagDelete")
	public int shoppingBagDeletePost(int idx) {
		return deliveryService.setShoppingBagDelete(idx);
	}
	
	// 결재.
	@PostMapping("/Buy")
	public String buyPost(Model model, DeliveryVO dVO,
			@RequestParam(name = "idxs", defaultValue = "", required = false)String idxs) {
		if(dVO.getMid().contains(",")) {
			dVO.setMid(dVO.getMid().substring(0,dVO.getMid().indexOf(",")));
			dVO.setNickName(dVO.getNickName().substring(0,dVO.getNickName().indexOf(",")));
			dVO.setEmail(dVO.getEmail().substring(0,dVO.getEmail().indexOf(",")));
		}
		
		model.addAttribute("dVO", dVO);
		model.addAttribute("idxs", idxs);
		
		return "shop/paymentOk";
	}
	// 결재완료.
	@Transactional
	@GetMapping("/PaymentResult")
	public String paymentResultGet(Model model, DeliveryVO dVO,
			@RequestParam(name = "idxs", defaultValue = "", required = false)String idxs) {
		int res = 0;
		
		if(dVO.getMid().equals("noMember")) {
			Date today = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String deliveryIdx = sdf.format(today)+UUID.randomUUID().toString().substring(0, 8);
			
			deliveryService.setShoppingBag(dVO.getIdx(), deliveryIdx, dVO.getMid(), "비회원", "", dVO.getTitle(), dVO.getOrderQuantity(), dVO.getPrice(), dVO.getAddress(), dVO.getProductImage(), "준비중");
			
			shopService.setProductQuantityUpdate(dVO.getIdx(), dVO.getOrderQuantity());
			
			model.addAttribute("deliveryIdx", deliveryIdx);
			res = 1;
		}
		else {
			res = deliveryService.setDeliveryLastUpdate(idxs, dVO);
			if(idxs.contains(",")) dVO = deliveryService.getShoppingBagDuplicat(dVO.getMid(), dVO.getTitle().substring(0,dVO.getTitle().indexOf(",")));
			else dVO = deliveryService.getShoppingBagDuplicat(dVO.getMid(), dVO.getTitle());
			
			model.addAttribute("deliveryIdx", dVO.getDeliveryIdx());
		}
		
		if(res != 0) return "redirect:/Message/deliveryOk";
		else return "redirect:/Message/deliveryNo";
	}
	// 주문완료화면.
	@GetMapping("/DeliveryOk")
	public String deliveryOkGet(String deliveryIdx) {
		System.out.println(deliveryIdx);
		return "";
	}
}
