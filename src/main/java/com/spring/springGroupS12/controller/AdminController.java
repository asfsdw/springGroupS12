package com.spring.springGroupS12.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.spring.springGroupS12.vo.PageVO;
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
	public String adminMainGet(Model model) {
		int newSubScript = 0;
		int newMember = 0;
		int newProduct = 0;
		int newDelivery = 0;
		
		List<SubScriptVO> subVOS = memberService.getNewSubScript();
		if(subVOS != null) newSubScript += subVOS.size();
		List<ShopVO> shopVOS = shopService.getNewProductSub();
		if(shopVOS != null) newSubScript += shopVOS.size();
		List<MemberVO> memberVOS = memberService.getNewMember();
		if(memberVOS != null) newMember = memberVOS.size();
		List<ShopVO> productVOS = shopService.getNewProduct();
		if(productVOS != null) newProduct = productVOS.size();
		List<DeliveryVO> deliveryVOS = deliveryService.getNewDelivery();
		if(deliveryVOS != null) newDelivery = deliveryVOS.size();
		
		model.addAttribute("newSubScript", newSubScript);
		model.addAttribute("newMember", newMember);
		model.addAttribute("newProduct", newProduct);
		model.addAttribute("newDelivery", newDelivery);
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
		System.out.println("part: "+part);
		System.out.println("flag: "+flag);
		System.out.println("idx: "+idx);
		System.out.println("idxs: "+idxs);
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
	public String productListGet(Model model,
			@RequestParam(name = "idx", defaultValue = "0", required = false)int idx) {
		List<ShopVO> vos = null;
		ShopVO vo = null;
		if(idx == 0) {
			vos = shopService.getProductList(0, 0, "전체");
			model.addAttribute("vos", vos);
		}
		else if(idx != 0) {
			vo = shopService.getProduct(idx);
			model.addAttribute("vo", vo);
		}
		
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
		else if(!idxs.equals("")) return memberService.setMemberLevelUp(Integer.parseInt(idxs), level);
		return memberService.setMemberLevelUp(idx, level);
	}
	
	// 신고목록.
	@GetMapping("/ComplaintList")
	public String complaintListGet(Model model,
			@RequestParam(name = "progress", defaultValue = "신고접수", required = false)String progress) {
		List<ComplaintVO> vos = complaintService.getComplaintList(progress);
		
		model.addAttribute("vos", vos);
		model.addAttribute("progress", progress);
		return "admin/complaint/complaintList";
	}
	// 신고상태 변경.
	@Transactional
	@ResponseBody
	@PostMapping("/ComplaintChange")
	public int complaintChangePost(String progress, String part,
			@RequestParam(name = "idx", defaultValue = "0", required = false)int idx,
			@RequestParam(name = "idxs", defaultValue = "", required = false)String idxs) {
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
	
	// 파일 정리 관련.
	@GetMapping("/FileManagement")
	public String fileManagementGet(HttpServletRequest request, Model model, PageVO pVO) {
		// part를 넣기위해 설정.
		pVO = pagination.pagination(pVO);
			
		String realPath = "";
		if(pVO.getPart().equals("전체")) realPath = request.getSession().getServletContext().getRealPath("/resources/data");
		else realPath = request.getSession().getServletContext().getRealPath("/resources/data/"+pVO.getPart());
		
		String[] files = new File(realPath).list();
		
		// 파일의 총 수.
		pVO.setTotRecCnt(files.length);
		// totPage를 구하기 위해 다시 설정.
		pVO = pagination.pagination(pVO);
		
		// 페이징 처리.
		// 한 페이지에 보여줄 파일 정보를 담을 배열 생성(크기는 전체 파일 갯수만큼).
		String[] file = new String[files.length];
		// startIndexNo(pag-1 * pageSize)부터 startIndexNo+pageSize만큼 반복문을 돌린다.
		// pag=1, pageSize=10일 경우(0~9까지. pag=2일 경우 10~20까지.)
		for(int i=pVO.getStartIndexNo(); i<pVO.getStartIndexNo()+pVO.getPageSize(); i++) {
			// i가 전체 파일 갯수보다 작을 때까지만 돌려야한다(배열의 인덱스는 0부터 시작하고 length는 1부터 시작하니까).
			if(i < files.length) file[i] = files[i];
			// i가 전체 파일 갯수보다 크면 반복문 탈출.
			else break;
		}
		
		model.addAttribute("pVO", pVO);
		model.addAttribute("files", file);
		return "admin/folder/fileManagement";
	}
	@ResponseBody
	@PostMapping("/FileManagement")
	public int fileManagementPost(HttpServletRequest request, Model model, PageVO pVO, String fileName, String fNames) {
		// fileName = 삭제버튼으로 파일을 삭제할 경우의 파일이름. fNames = 선택삭제로 파일을 삭제할 경우의 파일이름.
		int res = 0;
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/"+pVO.getPart()+"/");
		String[] fName = null;
		// 삭제할 파일이 여러개일 때.
		if(fNames.contains("/")) {
			fName = fNames.split("/");
		}
		
		// 삭제 버튼으로 삭제했을 때.
		if(fileName != "") {
			File file = new File(realPath+fileName);
			if(!file.isDirectory()) file.delete();
			res = 1;
		}
		// 여러 파일 삭제.
		else if(fName != null) {
			for(int i=0; i<fName.length; i++) {
				File file = new File(realPath+fName[i]);
				if(!file.isDirectory()) {
					file.delete();
				}
			}
			res = 1;
		}
		// 선택을 하나만 했을 경우.
		else {
			if(!fNames.contains("/")) {
				File file = new File(realPath+fNames);
				if(!file.isDirectory()) file.delete();
			}
			res = 1;
		}
		
		model.addAttribute("pVO", pVO);
		
		return res;
	}
}