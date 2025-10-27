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
import com.spring.springGroupS12.service.ComplaintService;
import com.spring.springGroupS12.service.DeliveryService;
import com.spring.springGroupS12.service.FileService;
import com.spring.springGroupS12.service.MemberService;
import com.spring.springGroupS12.service.ReplyService;
import com.spring.springGroupS12.service.ShopService;
import com.spring.springGroupS12.vo.ComplaintVO;
import com.spring.springGroupS12.vo.DeliveryVO;
import com.spring.springGroupS12.vo.FileVO;
import com.spring.springGroupS12.vo.MemberVO;
import com.spring.springGroupS12.vo.PageVO;
import com.spring.springGroupS12.vo.ReplyVO;
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
	@Autowired
	ReplyService replyService;
	@Autowired
	ComplaintService complaintService;
	
	// 상품 리스트.
	@GetMapping("/Goods")
	public String goodsGet(Model model, PageVO pVO,
			@RequestParam(name = "kategorie", defaultValue = "전체", required = false)String kategorie) {
		pVO.setSection("shop");
		pVO = pagination.pagination(pVO);
		
		List<ShopVO> vos = shopService.getProductList(pVO.getStartIndexNo(), pVO.getPageSize(), kategorie);
		
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
		if(vo.getTitle().length() > 50 || vo.getTitle() == null) return "redirect:/Message/wrongAccess";
		
		if(fName.getOriginalFilename().equals("")) return "redirect:/Message/wrongAccess";
		
		// 상품 등록.
		int res = 0;
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
		if(vo.getTitle().length() > 50 || vo.getTitle() == null) return "redirect:/Message/wrongAccess";
		
		if(fName.getOriginalFilename().equals("")) return "redirect:/Message/wrongAccess";
		
		// 상품 등록 신청.
		int res = 0;
		vo.setOpenSW("신청접수");
		res = shopService.setProductImage(fName, vo, fVO);
		
		if(res != 0) return "redirect:/Message/productSubOk";
		else return "redirect:/Message/productNo";
	}
	
	// 상품 상세보기.
	@GetMapping("/Product")
	public String productGet(Model model, ShopVO vo) {
		// 보는 사람 아이디.
		String mid = vo.getMid();
		vo = shopService.getProduct(vo.getIdx());
		// 구매한 상품이라면 리뷰 폼을 열어준다.
		DeliveryVO searchdVO = deliveryService.getShoppingBagDuplicat(mid, vo.getTitle(), "구매완료");
		// 상품의 리뷰 리스트.
		List<ReplyVO> rVOS = replyService.getProductReplyList("shop", vo.getIdx());
		if(rVOS.size() > 0) {
			double reviewAVG = 0.0;
			for(ReplyVO rvo : rVOS) {
				reviewAVG += rvo.getStar();
			}
			reviewAVG = reviewAVG / rVOS.size();
			
			model.addAttribute("reviewAVG", reviewAVG);
		}
		
		
		if(searchdVO != null) model.addAttribute("reviewSW", "on");
		model.addAttribute("vo", vo);
		model.addAttribute("rVOS", rVOS);
		return "shop/product";
	}
	// 상품 구매.
	@PostMapping("/Product")
	public String ProductPost(Model model, DeliveryVO dVO,
			@RequestParam(name = "idxs", defaultValue = "", required = false)String idxs,
			@RequestParam(name = "titles", defaultValue = "", required = false)String titles,
			@RequestParam(name = "orderQuantitys", defaultValue = "", required = false)String orderQuantitys) {
		// 구매할 개수와 재고 비교.
		// 상품 페이지에서 바로 구매할 때.
		if(idxs.equals("")) {
			ShopVO searchSVO = shopService.getProduct(dVO.getIdx());
			if(searchSVO.getQuantity() < dVO.getOrderQuantity()) {
				model.addAttribute("title", dVO.getTitle());
				return "redirect:/Message/lackQuantity?idx="+dVO.getIdx();
			}
		}
		// 장바구니에서 구매할 때.
		else {
			String[] idx = idxs.split(",");
			String[] title = titles.split(",");
			String[] orderQuantity = orderQuantitys.split(",");
			for(int i=0; i<idx.length; i++) {
				dVO = deliveryService.getShoppingBagDuplicat(dVO.getMid(), title[i], "대기중");
				ShopVO searchSVO = shopService.getProduct(dVO.getParentIdx());
				if(searchSVO.getQuantity() < Integer.parseInt(orderQuantity[i])) {
					model.addAttribute("title", title[i]);
					return "redirect:/Message/lackQuantity?idx="+dVO.getParentIdx();
				}
			}
		}
		
		MemberVO mVO = memberService.getMemberMid(dVO.getMid());
		// 회원이 구매.
		if(!dVO.getMid().equals("")) {
			List<DeliveryVO> searchdVOS = deliveryService.getShoppingBag(dVO.getMid());
			// 장바구니에 상품이 존재함.
			if(searchdVOS.size() > 0) {
				// 장바구니에서 구매할 경우.
				if(!idxs.equals("")) {
					// 장바구니에서 변경한 구매개수 반영.
					deliveryService.setShoppingBagLastUpdate(idxs, orderQuantitys);
					
					// 장바구니 안의 구매할 상품 리스트.
					List<DeliveryVO> deliveryVOS = deliveryService.getShoppingBagLastList(idxs);
					
					model.addAttribute("deliveryVOS", deliveryVOS);
				}
				// 상품화면에서 바로 구매할 경우.
				else {
					for(DeliveryVO searchdVO : searchdVOS) {
						// 상품화면에서 바로 구매눌렀는데 이미 장바구니에 있는 경우.
						if(dVO.getTitle().equals(searchdVO.getTitle())) {
							// 구매할 때의 구매개수 반영.
							deliveryService.setShoppingBagLastUpdate(searchdVO.getIdx()+"", dVO.getOrderQuantity()+"");
							
							// 장바구니 안의 구매할 상품 리스트.
							List<DeliveryVO> deliveryVOS = deliveryService.getShoppingBagLastList(searchdVO.getIdx()+"");
							
							model.addAttribute("deliveryVOS", deliveryVOS);
						}
						// 상품화면에서 바로 구매눌렀는데 장바구니에 없는 경우.
						else {
							dVO.setNickName(mVO.getNickName());
							dVO.setEmail(mVO.getEmail());
							dVO.setAddress(mVO.getAddress());
							
							model.addAttribute("dVO", dVO);
						}
					}
				}
			}
			// 장바구니가 비어있음.
			else {
				dVO.setNickName(mVO.getNickName());
				dVO.setEmail(mVO.getEmail());
				dVO.setAddress(mVO.getAddress());
				
				model.addAttribute("dVO", dVO);
			}
			
		}
		// 비회원 구매.
		else {
			dVO.setMid("noMember");
			model.addAttribute("dVO", dVO);
		}
		
		model.addAttribute("point", mVO==null?0:mVO.getPoint());
		return "shop/productBuy";
	}
	
	// 장바구니 등록.
	@ResponseBody
	@PostMapping("/ShoppingBag")
	public int shoppingBagPost(@RequestParam(name = "mid", defaultValue = "", required = false)String mid,
			@RequestParam(name = "nickName", defaultValue = "", required = false)String nickName,
			@RequestParam(name = "idx", defaultValue = "0", required = false)int idx,
			@RequestParam(name = "orderQuantity", defaultValue = "0", required = false) int orderQuantity) {
		MemberVO vo = memberService.getMemberMid(mid);
		ShopVO sVO = shopService.getProduct(idx);
		
		// 장바구니에 같은 상품이 있으면 수량만 변경.
		DeliveryVO dVO = deliveryService.getShoppingBagDuplicat(mid, sVO.getTitle(), "대기중");
		if(dVO != null) return deliveryService.setShoppingBagUpdate(orderQuantity, dVO.getIdx());
		
		return deliveryService.setShoppingBag(idx, "", mid, vo.getNickName(), vo.getEmail(), sVO.getTitle(), orderQuantity, sVO.getPrice(), vo.getAddress(), sVO.getProductImage(), "대기중", 0);
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
		// 포인트 백엔드 검사(포인트를 사용했는데 100단위가 아닐 경우.
		if(dVO.getUsedPoint() != 0 && dVO.getUsedPoint()%100 != 0) return "redirec:/Message/wrongAccess";
		
		// 결재금액.
		int totPrice = 0;
		if(dVO.getMid().contains(",")) {
			dVO.setMid(dVO.getMid().substring(0,dVO.getMid().indexOf(",")));
			dVO.setNickName(dVO.getNickName().substring(0,dVO.getNickName().indexOf(",")));
			dVO.setEmail(dVO.getEmail().substring(0,dVO.getEmail().indexOf(",")));
			
			String[] titles = dVO.getTitle().split(",");
			for(int i=0; i<titles.length; i++) {
				DeliveryVO priceVO = deliveryService.getShoppingBagDuplicat(dVO.getMid(), titles[i], "대기중");
				totPrice += priceVO.getPrice()*priceVO.getOrderQuantity();
			}
			if(dVO.getUsedPoint() != 0)
			totPrice = totPrice - (dVO.getUsedPoint()/10);
		}
		else totPrice = dVO.getPrice() - (dVO.getUsedPoint()/10);
		
		model.addAttribute("dVO", dVO);
		model.addAttribute("idxs", idxs);
		model.addAttribute("totPrice", totPrice);
		
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
			
			deliveryService.setShoppingBag(dVO.getIdx(), deliveryIdx, dVO.getMid(), "비회원", "", dVO.getTitle(), dVO.getOrderQuantity(), dVO.getPrice(), dVO.getAddress(), dVO.getProductImage(), "준비중", dVO.getUsedPoint());
			
			shopService.setProductQuantityUpdate(dVO.getIdx(), dVO.getOrderQuantity());
			
			model.addAttribute("deliveryIdx", deliveryIdx);
			res = 1;
		}
		else {
			// 배송 DB 업데이트.
			if(!idxs.equals("")) res = deliveryService.setDeliveryLastUpdate(idxs, dVO);
			else res = deliveryService.setDeliveryLastUpdate(dVO.getIdx()+"", dVO);
			
			// 주문번호 가져오기.
			if(idxs.contains(",")) dVO = deliveryService.getShoppingBagDuplicat(dVO.getMid(), dVO.getTitle().substring(0,dVO.getTitle().indexOf(",")), "준비중");
			else dVO = deliveryService.getShoppingBagDuplicat(dVO.getMid(), dVO.getTitle(), "준비중");
			
			model.addAttribute("deliveryIdx", dVO.getDeliveryIdx());
		}
		
		if(res != 0) return "redirect:/Message/deliveryOk";
		else return "redirect:/Message/deliveryNo";
	}
	
	// 주문완료화면.
	@GetMapping("/DeliveryOk")
	public String deliveryOkGet(Model model, String deliveryIdx,
			@RequestParam(name = "mid", defaultValue = "", required = false)String mid) {
		List<DeliveryVO> vos = null;
		if(mid.equals("")) vos = deliveryService.getDeliveryListDeliveryIdx(deliveryIdx);
		else vos = deliveryService.getDeliveryListDeliveryMid(mid);
		
		model.addAttribute("vos", vos);
		return "shop/deliveryOk";
	}
	
	// 주문취소.
	@Transactional
	@ResponseBody
	@PostMapping("/DeliveryCancel")
	public int deliveryCancelPost(String deliveryIdx, int usedPoint) {
		List<DeliveryVO> vos = deliveryService.getShoppingBagIdx(deliveryIdx);
		String mid = "";
		for(DeliveryVO vo : vos) {
			shopService.setProductQuantityRollback(vo.getParentIdx(), vo.getOrderQuantity());
			if(mid.equals("")) mid = vo.getMid();
		}
		if(usedPoint != 0) memberService.setMemberPointRollback(mid, usedPoint);
		return deliveryService.setShoppingBagDeleteDeliveryIdx(deliveryIdx);
	}
	// 구매완료.
	@Transactional
	@ResponseBody
	@PostMapping("/DeliveryComp")
	public int deliveryCompPost(String deliveryIdx) {
		return deliveryService.setDeliverySWUpdate(deliveryIdx, "구매완료");
	}
	
	// 상품 신고.
	@ResponseBody
	@PostMapping("/ShopComplaint")
	public int shopComplaintPost(ComplaintVO vo) {
		int res = 0;
		res = complaintService.setComplaintInput(vo);
		if(res != 0) complaintService.setComplaintParentUpdate(vo.getPart(), vo.getPartIdx(), "OK");
		return res;
	}
	
	// 상품 수정 관련.
	@GetMapping("/ProductUpdate")
	public String shopUpdateGet(Model model, ShopVO vo) {
		vo = shopService.getProduct(vo.getIdx());
		
		model.addAttribute("vo", vo);
		return "shop/productUpdate";
	}
	// 상품 수정.
	@PostMapping("/ProductUpdate")
	public String shopUpdatePost(ShopVO vo) {
		// 백엔드 검사.
		if(vo.getMid().length() > 20 || vo.getMid() == null) return "redirect:/Message/wrongAccess";
		if(vo.getNickName().length() > 20 || vo.getNickName() == null) return "redirect:/Message/wrongAccess";
		if(vo.getKategorie().length() > 10 || vo.getKategorie() == null) return "redirect:/Message/wrongAccess";
		if(vo.getTitle().length() > 50 || vo.getTitle() == null) return "redirect:/Message/wrongAccess";
		
		// 상품 수정.
		int res = shopService.setProductUpdate(vo);
		if(res != 0) return "redirect:/shop/Product?idx="+vo.getIdx();
		else return "redirect:/Message/productUpdateNo?idx="+vo.getIdx();
	}
}
