package com.spring.springGroupS12.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.springGroupS12.vo.PageVO;

@Controller
public class MessageController {
	@RequestMapping(value = "/Message/{msgFlag}", method = RequestMethod.GET)
	public String MessageGet(HttpSession session, Model model, PageVO pVO,
			@PathVariable String msgFlag,
			@RequestParam(name="mid", defaultValue = "", required = false) String mid,
			@RequestParam(name="idx", defaultValue = "0", required = false) int idx,
			@RequestParam(name="deliveryIdx", defaultValue = "", required = false) String deliveryIdx,
			@RequestParam(name="title", defaultValue = "", required = false) String title) {
		if(msgFlag.equals("wrongAccess")) {
			model.addAttribute("message", "잘못된 접근입니다.");
			model.addAttribute("url", "/");
		}
		else if(msgFlag.equals("SignUpOk")) {
			model.addAttribute("message", mid+"님 회원가입되었습니다.");
			model.addAttribute("url", "member/Login");
		}
		else if(msgFlag.equals("SignUpNo")) {
			model.addAttribute("message", "회원가입에 실패했습니다.");
			model.addAttribute("url", "member/SignUp");
		}
		else if(msgFlag.equals("minor")) {
			model.addAttribute("message", "미성년자는 가입하실 수 없습니다.");
			model.addAttribute("url", "/main");
		}
		else if(msgFlag.equals("idDuplication")) {
			model.addAttribute("message", "중복된 아이디입니다.");
			model.addAttribute("url", "member/SignUp");
		}
		else if(msgFlag.equals("midSameSearch")) {
			model.addAttribute("message", "같은 아이디가 존재합니다.\\n다른 이메일로 가입하시거나\\n가입한 아이디로 로그인 해주세요.");
			model.addAttribute("url", "/member/Login");
		}
		else if(msgFlag.equals("nickNameDuplication")) {
			model.addAttribute("message", "중복된 닉네임입니다.");
			model.addAttribute("url", "member/SignUp");
		}
		else if(msgFlag.equals("loginOk")) {
			model.addAttribute("message", mid+"님 로그인되셨습니다.");
			model.addAttribute("url", "member/Main");
		}
		else if(msgFlag.equals("newLoginOk")) {
			model.addAttribute("message", mid+"님 로그인 되셨습니다.\\n임시 비밀번호가 발급되었습니다.");
			model.addAttribute("url", "/member/Main");
		}
		else if(msgFlag.equals("loginNo")) {
			model.addAttribute("message", "로그인에 실패했습니다.");
			model.addAttribute("url", "member/Login");
		}
		else if(msgFlag.equals("logoutOk")) {
			model.addAttribute("message", mid+"님 로그아웃 되셨습니다.");
			model.addAttribute("url", "member/Login");
		}
		else if(msgFlag.equals("memberUpdateOk")) {
			model.addAttribute("message", "정보가 수정되었습니다.");
			model.addAttribute("url", "/member/Main");
		}
		else if(msgFlag.equals("memberUpdateNo")) {
			model.addAttribute("message", "정보 수정에 실패했습니다.");
			model.addAttribute("url", "/member/MemberUpdate?mid="+mid);
		}
		else if(msgFlag.equals("pwdChangeOk")) {
			session.removeAttribute("sMid");
			session.removeAttribute("sNickName");
			session.removeAttribute("sLevel");
			session.removeAttribute("sStrLevel");
			session.removeAttribute("sLastDate");
			session.removeAttribute("sEmailKey");
			session.removeAttribute("sAccessToken");
			
			model.addAttribute("message", "비밀번호가 변경되었습니다.");
			model.addAttribute("url", "/member/Login");
		}
		else if(msgFlag.equals("pwdChangeNo")) {
			model.addAttribute("message", "비밀번호가 변경되지 않았습니다.\\n다시 시도해주세요.");
			model.addAttribute("url", "/member/MemberPwdCheck");
		}
		else if(msgFlag.equals("boardInputOk")) {
			model.addAttribute("message", "게시글이 등록되었습니다.");
			model.addAttribute("url", "board/BoardList");
		}
		else if(msgFlag.equals("boardInputNo")) {
			model.addAttribute("message", "게시글 등록에 실패했습니다.");
			model.addAttribute("url", "board/BoardInput");
		}
		else if(msgFlag.equals("boardUpdateOk")) {
			model.addAttribute("message", "게시글을 수정했습니다.");
			if(pVO.getSearch() != null) model.addAttribute("url", "/board/BoardContent?idx="+idx+"&pag="+pVO.getPag()+"&pageSize="+pVO.getPageSize()+"&search="+pVO.getSearch()+"&searchStr="+pVO.getSearchStr());
			else model.addAttribute("url", "/board/BoardContent?idx="+idx+"&pag="+pVO.getPag()+"&pageSize="+pVO.getPageSize());
		}
		else if(msgFlag.equals("boardUpdateNo")) {
			model.addAttribute("message", "게시글 수정에 실패했습니다.");
			if(pVO.getSearch() != null) model.addAttribute("url", "/board/BoardUpdate?idx="+idx+"&pag="+pVO.getPag()+"&pageSize="+pVO.getPageSize()+"&search="+pVO.getSearch()+"&searchStr="+pVO.getSearchStr());
			else model.addAttribute("url", "/board/BoardUpdate?idx="+idx+"&pag="+pVO.getPag()+"&pageSize="+pVO.getPageSize());
		}
		else if(msgFlag.equals("boardDeleteOk")) {
			model.addAttribute("message", "게시글을 삭제했습니다.");
			if(pVO.getSearch() != null) model.addAttribute("url", "/board/BoardSearchList?&pag="+pVO.getPag()+"&pageSize="+pVO.getPageSize()+"&search="+pVO.getSearch()+"&searchStr="+pVO.getSearchStr());
			else model.addAttribute("url", "/board/BoardList?&pag="+pVO.getPag()+"&pageSize="+pVO.getPageSize());
		}
		else if(msgFlag.equals("boardDeleteNo")) {
			model.addAttribute("message", "게시글 삭제에 실패했습니다.");
			if(pVO.getSearch() != null) model.addAttribute("url", "/board/BoardContent?idx="+idx+"&pag="+pVO.getPag()+"&pageSize="+pVO.getPageSize()+"&search="+pVO.getSearch()+"&searchStr="+pVO.getSearchStr());
			else model.addAttribute("url", "/board/BoardContent?idx="+idx+"&pag="+pVO.getPag()+"&pageSize="+pVO.getPageSize());
		}
		else if(msgFlag.equals("pwdInputNo")) {
			model.addAttribute("message", "비밀번호를 입력해주세요.");
			model.addAttribute("url", "board/BoardList");
		}
		else if(msgFlag.equals("boardPwdNo")) {
			model.addAttribute("message", "게시글의 비밀번호가 다릅니다.");
			model.addAttribute("url", "board/BoardList");
		}
		else if(msgFlag.equals("productOk")) {
			model.addAttribute("message", "상품이 등록되었습니다.");
			model.addAttribute("url", "shop/Goods");
		}
		else if(msgFlag.equals("productSubOk")) {
			model.addAttribute("message", "상품 등록이 신청되었습니다.\\n관리자가 승인할 때까지 기다려주세요.");
			model.addAttribute("url", "shop/Goods");
		}
		else if(msgFlag.equals("productNo")) {
			model.addAttribute("message", "상품등록에 실패했습니다.");
			model.addAttribute("url", "shop/ProductAdd");
		}
		else if(msgFlag.equals("subScriptOk")) {
			model.addAttribute("message", "신청이 등록되었습니다.\\n관리자가 승인할 때까지 기다려주세요.");
			model.addAttribute("url", "member/SubScript");
		}
		else if(msgFlag.equals("subScriptNo")) {
			model.addAttribute("message", "신청에 실패했습니다.");
			model.addAttribute("url", "member/SubScript");
		}
		else if(msgFlag.equals("subScriptDup")) {
			model.addAttribute("message", "이미 신청하셨습니다.\\n관리자의 승인을 기다려주세요.");
			model.addAttribute("url", "member/SubScript");
		}
		else if(msgFlag.equals("cartEmpty")) {
			model.addAttribute("message", "장바구니가 비어있습니다.\\n상품을 추가해주세요.");
			model.addAttribute("url", "shop/Goods");
		}
		else if(msgFlag.equals("deliveryOk")) {
			model.addAttribute("message", "주문이 완료되었습니다.");
			model.addAttribute("url", "shop/DeliveryOk?deliveryIdx="+deliveryIdx);
		}
		else if(msgFlag.equals("deliveryNo")) {
			model.addAttribute("message", "주문에 실패했습니다.\\n잠시 후, 다시 시도해주세요.");
			model.addAttribute("url", "shop/Goods");
		}
		else if(msgFlag.equals("lackQuantity")) {
			model.addAttribute("message", title+"\\n상품의 재고가 부족합니다.\\n재고를 확인 후, 다시 주문해주세요.");
			model.addAttribute("url", "shop/Goods");
		}
		return "include/message";
	}
}
