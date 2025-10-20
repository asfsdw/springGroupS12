package com.spring.springGroupS12.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.springGroupS12.service.DeliveryService;
import com.spring.springGroupS12.service.MemberService;
import com.spring.springGroupS12.service.ShopService;
import com.spring.springGroupS12.vo.DeliveryVO;
import com.spring.springGroupS12.vo.ShopVO;
import com.spring.springGroupS12.vo.SubScriptVO;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	MemberService memberService;
	@Autowired
	ShopService shopService;
	@Autowired
	DeliveryService deliveryService;
	
	// 관리자 메뉴.
	@GetMapping("/AdminMain")
	public String adminMainGet() {
		return "admin/adminMain";
	}
	
	// 신청 리스트.
	@GetMapping("/SubScriptList")
	public String subScriptListGet(Model model,
			@RequestParam(name = "mid", defaultValue = "전체", required = false)String mid) {
		List<ShopVO> shopVOS = shopService.getProductSubList(mid);
		List<SubScriptVO> subVOS = memberService.getSubScriptList(mid);
		
		model.addAttribute("shopVOS", shopVOS);
		model.addAttribute("subVOS", subVOS);
		return "admin/subScript/subScriptList";
	}
	// 신청현황 변경.
	@ResponseBody
	@PostMapping("/OpenSWChange")
	public int openSWChangePost(String part, int idx, String flag) {
		if(part.equals("shop")) {
			if(flag.equals("삭제")) return shopService.setProductSubDelete(idx);
			return shopService.setProductOpenSWUpdate(idx, flag);
		}
		else if(part.equals("sub")) {
			if(flag.equals("삭제")) return memberService.setSubScriptDelete(idx);
			return memberService.setProductProgressSWUpdate(idx, flag);
		}
		return 0;
	}
	
	// 배송 리스트.
	@GetMapping("/DeliveryList")
	public String deliveryListGet(Model model,
			@RequestParam(name = "deliverySW", defaultValue = "전체", required = false)String deliverySW) {
		List<DeliveryVO> dVOS = deliveryService.getDeliveryListDeliveryDelivery(deliverySW);
		
		model.addAttribute("dVOS", dVOS);
		return "admin/shop/deliveryList";
	}
	// 배송현황 변경.
	@ResponseBody
	@PostMapping("DeliverySWChange")
	public int deliverySWChangePost(String deliveryIdx, String deliverySW) {
		return deliveryService.setDeliverySWUpdate(deliveryIdx, deliverySW);
	}
	// 배송내역 삭제.
	@ResponseBody
	@PostMapping("/DeliveryDelete")
	public int deliveryDeletePost(String deliveryIdx) {
		return deliveryService.setShoppingBagDeleteDeliveryIdx(deliveryIdx);
	}
	
	// 멤버 목록.
	@GetMapping("MemberList")
	public String memberListGet() {
		return "admin/member/memberList";
	}
}
