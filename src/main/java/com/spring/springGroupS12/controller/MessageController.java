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
		if(msgFlag.equals("SignUpOk")) {
			model.addAttribute("message", mid+"님 회원가입되었습니다.");
			model.addAttribute("url", "member/Login");
		}
		else if(msgFlag.equals("SignUpNo")) {
			model.addAttribute("message", "회원가입에 실패했습니다.");
			model.addAttribute("url", "member/SignUp");
		}
		else if(msgFlag.equals("idDuplication")) {
			model.addAttribute("message", "중복된 아이디입니다.");
			model.addAttribute("url", "member/SignUp");
		}
		else if(msgFlag.equals("nickNameDuplication")) {
			model.addAttribute("message", "중복된 닉네임입니다.");
			model.addAttribute("url", "member/SignUp");
		}
		else if(msgFlag.equals("loginOk")) {
			model.addAttribute("message", mid+"님 로그인되셨습니다.");
			model.addAttribute("url", "member/Main");
		}
		else if(msgFlag.equals("loginNo")) {
			model.addAttribute("message", "로그인에 실패했습니다.");
			model.addAttribute("url", "member/Login");
		}
		else if(msgFlag.equals("logoutOk")) {
			model.addAttribute("message", mid+"님 로그아웃 되셨습니다.");
			model.addAttribute("url", "member/Login");
		}
		return "include/message";
	}
}
