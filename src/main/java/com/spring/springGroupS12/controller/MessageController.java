package com.spring.springGroupS12.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MessageController {
	@RequestMapping(value = "/Message/{msgFlag}", method = RequestMethod.GET)
	public String MessageGet(Model model,
			@PathVariable String msgFlag,
			@RequestParam(name="mid", defaultValue = "", required = false) String mid) {
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
		else if(msgFlag.equals("boardInputOk")) {
			model.addAttribute("message", "게시글이 등록되었습니다.");
			model.addAttribute("url", "board/BoardList");
		}
		else if(msgFlag.equals("boardInputNo")) {
			model.addAttribute("message", "게시글 등록에 실패했습니다.");
			model.addAttribute("url", "board/BoardInput");
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
		else if(msgFlag.equals("productNo")) {
			model.addAttribute("message", "상품등록에 실패했습니다.");
			model.addAttribute("url", "shop/ProductAdd");
		}
		return "include/message";
	}
}
