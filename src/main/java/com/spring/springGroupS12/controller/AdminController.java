package com.spring.springGroupS12.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.springGroupS12.common.Pagination;
import com.spring.springGroupS12.service.BoardService;
import com.spring.springGroupS12.service.ComplaintService;
import com.spring.springGroupS12.service.DeliveryService;
import com.spring.springGroupS12.service.MemberService;
import com.spring.springGroupS12.service.ShopService;
import com.spring.springGroupS12.vo.BoardVO;
import com.spring.springGroupS12.vo.ComplaintVO;
import com.spring.springGroupS12.vo.DeliveryVO;
import com.spring.springGroupS12.vo.MemberVO;
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
	@Autowired
	Pagination pagination;
	@Autowired
	ComplaintService complaintService;
	@Autowired
	BoardService boardService;
	
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
	@Transactional
	@ResponseBody
	@PostMapping("/OpenSWChange")
	public int openSWChangePost(String part, String flag,
			@RequestParam(name = "idx", defaultValue = "0", required = false)int idx,
			@RequestParam(name = "idxs", defaultValue = "", required = false)String idxs) {
		if(idxs.contains(",")) {
			String[] sParts = part.split(",");
			String[] sIdxs = idxs.split(",");
			int res = 0;
			try {
				for(int i=0; i<sIdxs.length; i++) {
					if(sParts[i].equals("shop")) {
						if(flag.equals("삭제")) res = shopService.setProductSubDelete(Integer.parseInt(sIdxs[i]));
						else res = shopService.setProductOpenSWUpdate(Integer.parseInt(sIdxs[i]), flag);
					}
					else if(sParts[i].equals("sub")) {
						if(flag.equals("삭제")) res = memberService.setSubScriptDelete(Integer.parseInt(sIdxs[i]));
						else res = memberService.setProductProgressSWUpdate(Integer.parseInt(sIdxs[i]), flag);
					}
				}
				return res;
			} catch (Exception e) {
				return -1;
			}
		}
		
		if(part.equals("shop")) {
			if(idxs.equals("")) {
				if(flag.equals("삭제")) return shopService.setProductSubDelete(idx);
				return shopService.setProductOpenSWUpdate(idx, flag);
			}
			else {
				if(flag.equals("삭제")) return shopService.setProductSubDelete(Integer.parseInt(idxs));
				return shopService.setProductOpenSWUpdate(Integer.parseInt(idxs), flag);
			}
		}
		else if(part.equals("sub")) {
			if(idxs.equals("")) {
				if(flag.equals("삭제")) return memberService.setSubScriptDelete(idx);
				return memberService.setProductProgressSWUpdate(idx, flag);
			}
			else {
				if(flag.equals("삭제")) return memberService.setSubScriptDelete(Integer.parseInt(idxs));
				return memberService.setProductProgressSWUpdate(Integer.parseInt(idxs), flag);
			}
		}
		return 0;
	}
	
	// 상품 리스트.
	@GetMapping("/ProductList")
	public String productListGet(Model model) {
		List<ShopVO> vos = shopService.getProductList();
		
		model.addAttribute("vos", vos);
		return "admin/shop/productList";
	}
	// 배송 리스트.
	@GetMapping("/DeliveryList")
	public String deliveryListGet(Model model,
			@RequestParam(name = "deliverySW", defaultValue = "전체", required = false)String deliverySW) {
		List<DeliveryVO> dVOS = deliveryService.getDeliveryListDeliveryDelivery(deliverySW);
		
		model.addAttribute("dVOS", dVOS);
		model.addAttribute("deliverySW", deliverySW);
		return "admin/shop/deliveryList";
	}
	// 배송현황 변경.
	@Transactional
	@ResponseBody
	@PostMapping("/DeliverySWChange")
	public int deliverySWChangePost(String deliveryIdx, String deliverySW) {
		if(deliveryIdx.contains(",")) {
			String[] deliveryIdxs = deliveryIdx.split(",");
			int res = 0;
			try {
				for(int i=0; i<deliveryIdxs.length; i++) {
					res = deliveryService.setDeliverySWUpdate(deliveryIdxs[i], deliverySW);
				}
				return res;
			} catch (Exception e) {
				return -1;
			}
		}
		return deliveryService.setDeliverySWUpdate(deliveryIdx, deliverySW);
	}
	// 배송내역 삭제.
	@ResponseBody
	@PostMapping("/DeliveryDelete")
	public int deliveryDeletePost(String deliveryIdx) {
		return deliveryService.setShoppingBagDeleteDeliveryIdx(deliveryIdx);
	}
	
	// 멤버 목록.
	@GetMapping("/MemberList")
	public String memberListGet(Model model,
			@RequestParam(name = "level", defaultValue = "100", required = false)int level) {
		List<MemberVO> mVOS = memberService.getMemberListAdmin(level);
		
		model.addAttribute("mVOS", mVOS);
		model.addAttribute("level", level);
		return "admin/member/memberList";
	}
	// 멤버 검색.
	@GetMapping("/MemberSearch")
	public String memberSearchGet(Model model, String search, String searchStr) {
		List<MemberVO> mVOS = memberService.getMemberSearch(search, searchStr);
		
		model.addAttribute("mVOS", mVOS);
		return "admin/member/memberSearch";
	}
	//회원 리스트에서 회원의 등급 변경.
	@ResponseBody
	@PostMapping("/MemberLevelChange")
	public int memberLevelChangePost(int level,
			@RequestParam(name = "idx", defaultValue = "0", required = false)int idx,
			@RequestParam(name = "idxs", defaultValue = "", required = false)String idxs) {
		if(idxs.contains(",")) {
			String[] sIdxs = idxs.split(",");
			int res = 0;
			try {
				for(int i=0; i<sIdxs.length; i++) {
					res = memberService.setMemberLevelUp(Integer.parseInt(sIdxs[i]), level);
				}
				return res;
			} catch (Exception e) {
				return -1;
			}
		}
		return memberService.setMemberLevelUp(idx, level);
	}
	
	// 신고목록.
	@GetMapping("/ComplaintList")
	public String complaintListGet(Model model) {
		List<ComplaintVO> vos = complaintService.getComplaintList();
		
		model.addAttribute("vos", vos);
		return "admin/complaint/complaintList";
	}
	// 신고상태 변경.
	@Transactional
	@ResponseBody
	@PostMapping("/ComplaintChange")
	public int complaintChangePost(String progress, String part,
			@RequestParam(name = "idx", defaultValue = "0", required = false)int idx,
			@RequestParam(name = "idxs", defaultValue = "", required = false)int idxs) {
		if(part.equals("게시판")) part = "board";
		else if(part.equals("굿즈")) part = "shop";
		int res = 0;
		try {
			if(progress.equals("신고반려")) {
				ComplaintVO searchVO = complaintService.getComplaintIdx(idx);
				// 신고 DB 업데이트.
				res = complaintService.setComplaintProgressUpdate(idx, progress);
				// 원본글 업데이트.
				res = complaintService.setComplaintParentUpdate(part, searchVO.getPartIdx(), "NO");
			}
			else if(progress.equals("삭제")) {
				ComplaintVO searchVO = complaintService.getComplaintIdx(idx);
				// 신고 삭제.
				res = complaintService.setComplaintDelete(idx);
				// 원본글 신고 해제.
				res = complaintService.setComplaintParentUpdate(part, searchVO.getPartIdx(), "NO");
			}
			else res = complaintService.setComplaintProgressUpdate(idx, progress);
		} catch (Exception e) {
			return -1;
		}
		return res;
	}
	// 신고된 게시글 리스트.
	@GetMapping("/ComplaintBoardList")
	public String complaintBoardListGet(Model model,
			@RequestParam(name = "idx", defaultValue = "0", required = false)int idx) {
		List<BoardVO> vos = boardService.getComplaintBoardList(idx);
		
		model.addAttribute("vos", vos);
		return "admin/complaint/complaintBoardList";
	}
	// 신고된 게시글 검색.
	@GetMapping("/ComplaintBoardSearch")
	public String complaintBoardSearchGet(Model model,
			@RequestParam(name = "search", defaultValue = "", required = false)String search,
			@RequestParam(name = "searchStr", defaultValue = "", required = false)String searchStr) {
		List<BoardVO> vos = boardService.getComplaintBoard(search, searchStr);
		
		model.addAttribute("vos", vos);
		return "admin/complaint/complaintBoardSearch";
	}
	// 신고된 게시글 상태 변경.
	@Transactional
	@ResponseBody
	@PostMapping("/ComplaintBoardOpenSWChange")
	public int complaintBoardOpenSWChangePost(String openSW,
			@RequestParam(name = "idx", defaultValue = "0", required = false)int idx,
			@RequestParam(name = "idxs", defaultValue = "", required = false)String idxs) {
		int res = 0;
		try {
			if(!openSW.equals("삭제")) {
				res = boardService.setBoardOpenSWUpdate(idx, openSW);
				
				ComplaintVO searchVO = complaintService.getComplaintPartIdx(idx, "board");
				res = complaintService.setComplaintProgressUpdate(searchVO.getIdx(), "처리완료");
			}
			else {
				res = boardService.setBoardDelete(idx);
			}
		} catch (Exception e) {
			return -1;
		}
		
		return res;
	}
}
