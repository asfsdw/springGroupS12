package com.spring.springGroupS12.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.springGroupS12.service.MemberService;
import com.spring.springGroupS12.service.ShopService;
import com.spring.springGroupS12.vo.ShopVO;
import com.spring.springGroupS12.vo.SubScriptVO;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	MemberService memberService;
	@Autowired
	ShopService shopService;
	
	// 관리자 메뉴.
	@GetMapping("/AdminMain")
	public String adminMainGet() {
		return "admin/adminMain";
	}
	
	// 신청 리스트.
	@GetMapping("/SubScriptList")
	public String subScriptListGet(Model model) {
		String mid = "전체";
		List<ShopVO> shopVOS = shopService.getProductSubList(mid);
		List<SubScriptVO> subVOS = memberService.getSubScriptList(mid);
		
		model.addAttribute("shopVOS", shopVOS);
		model.addAttribute("subVOS", subVOS);
		return "admin/subScript/subScriptList";
	}
}
