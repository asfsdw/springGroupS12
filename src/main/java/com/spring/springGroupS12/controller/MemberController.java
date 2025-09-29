package com.spring.springGroupS12.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

import com.spring.springGroupS12.common.ProjectProvide;
import com.spring.springGroupS12.service.MemberService;
import com.spring.springGroupS12.vo.MemberVO;

@Controller
@RequestMapping("/member")
public class MemberController {
	@Autowired
	MemberService memberService;
	@Autowired
	ProjectProvide projectProvide;
	@Autowired
	BCryptPasswordEncoder passwordEncode;
	
	// 회원가입관련.
	@GetMapping("/SignUp")
	public String signUpGet(Model model) {
		// 나이 확인을 위해 현재 년도를 보낸다.
		model.addAttribute("year",LocalDateTime.now().getYear());
		return "member/signUp";
	}
	// 아이디 체크.
	@ResponseBody
	@PostMapping("/SignUpIdCheck")
	public int signUpIdCheckPost(String mid) {
		int res = 0;
		// 아이디로 회원을 찾아본다.
		MemberVO vo = memberService.getMemberMid(mid);
		if(vo != null) res = 1;
		return res;
	}
	// 닉네임 체크.
	@ResponseBody
	@PostMapping("/SignUpNickNameCheck")
	public int signUpNickNameCheckPost(String nickName) {
		int res = 0;
		// 닉네임으로 회원을 찾아본다.
		MemberVO vo = memberService.getMemberNickName(nickName);
		if(vo != null) res = 1;
		return res;
	}
	// 이메일 체크.
	@ResponseBody
	@PostMapping("/MemberEmailCheck")
	public int memberEmailCheckPost(String email) {
		int res = 0;
		List<MemberVO> vos = memberService.getMemberEmail(email);
		if(vos.size() > 0) res = 1;
		return res;
	}
	//이메일로 인증번호 발송.
	@ResponseBody
	@PostMapping("/SignUpEmailCheck")
	public int signUpEmailCheckPost(HttpSession session, String email) throws MessagingException {
		String emailKey = UUID.randomUUID().toString().substring(0, 8);
		// 세션에 인증번호 저장.
		session.setAttribute("sEmailKey", emailKey);
		// 메일 전송 메소드 불러오기.
		projectProvide.mailSend(email, "이메일 인증키입니다.", "이메일 인증키: "+emailKey);
		return 1;
	}
	// 인증번호 확인.
	@ResponseBody
	@PostMapping("/SignUpEmailCheckOk")
	public int signUpEmailCheckOkPost(HttpSession session, String checkKey) {
		// 세션에 저장한 인증번호와 비교.
		String emailKey = (String)session.getAttribute("sEmailKey");
		if(emailKey.equals(checkKey)) {
			session.removeAttribute("sEmailKey");
			return 1;
		}
		else return 0;
	}
	// 인증번호 제한시간(2분).
	@ResponseBody
	@PostMapping("/SignUpEmailCheckNo")
	public void signUpEmailCheckNoPost(HttpSession session) {
		// 인증번호 삭제.
		session.removeAttribute("sEmeilKey");
	}
	// 회원등록.
	@PostMapping("/SignUp")
	public String signUpPost(MultipartFile fName, MemberVO vo, HttpSession session) {
		// 아이디, 닉네임 중복 확인.
		if(memberService.getMemberMid(vo.getMid()) != null) return "redirect:/Message/idDuplication";
		if(memberService.getMemberNickName(vo.getNickName()) != null) return "redirect:/Message/nickNameDuplication";
		
		// 비밀번호 암호화.
		vo.setPwd(passwordEncode.encode(vo.getPwd()));
		
		// 프로필 사진 처리.
		if(fName.getOriginalFilename().equals("")) vo.setMyImage("noimage.jpg");
		else vo.setMyImage(projectProvide.fileUpload(fName, vo.getMid(), "member"));
		
		// DB에 회원정보 저장.
		try {
			int res = memberService.setSignUpMember(vo);
			if(res != 0) return "redirect:/Message/SignUpOk?mid="+vo.getMid();
			else return "redirect:/Message/SignUpNo";
		} catch (Exception e) {
		} return "redirect:/Message/SignUpNo";
	}
	
	// 로그인 괸련.
	@GetMapping("/Login")
	public String loginGet(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(int i=0; i<cookies.length; i++) {
				if(cookies[i].getName().equals("cMid")) {
					request.setAttribute("mid", cookies[i].getValue());
					break;
				}
			}
		}
		return "member/login";
	}
	// 아이디 찾기.
	@ResponseBody
	@PostMapping("/MemberIdFind")
	public List<MemberVO> memberIdFindPost(String email) {
		List<MemberVO> vos = memberService.getMemberEmail(email);
		return vos;
	}
	// 아이디 확인.
	@ResponseBody
	@PostMapping("/MemberIdCheck")
	public MemberVO memberIdCheckPost(String mid) {
		MemberVO vo = memberService.getMemberMid(mid);
		return vo;
	}
	// 임시 비밀번호 발급.
	@ResponseBody
	@PostMapping("/MemberTempPwd")
	public int memberTempPwdPost(String mid, String email) throws MessagingException {
		String tempPwd = UUID.randomUUID().toString().substring(0, 4);
		int res = memberService.setMemberTempPwd(mid, tempPwd);
		// 비밀번호를 임시비밀번호로 수정에 성공했을 때.
		if(res != 0) {
			projectProvide.mailSend(email, "임시비밀번호입니다.", "임시 비밀번호: "+tempPwd);
			return 1;
		}
		else return 0;
	}
	// 로그인.
	@PostMapping("/Login")
	public String loginPost(HttpSession session, HttpServletRequest request, HttpServletResponse response,
			@RequestParam(name = "mid", defaultValue = "", required = false) String mid,
			@RequestParam(name = "pwd", defaultValue = "", required = false) String pwd,
			@RequestParam(name = "idSave", defaultValue = "", required = false) String idSave) {
		MemberVO vo = memberService.getMemberMid(mid);
		if(vo != null && vo.getDelete().equals("활동") && passwordEncode.matches(pwd, vo.getPwd())) {
			String strLevel = "";
			if(vo.getLevel() == 0) strLevel = "관리자";
			else if(vo.getLevel() == 1) strLevel = "우수회원";
			else if(vo.getLevel() == 2) strLevel = "정회원";
			else if(vo.getLevel() == 3) strLevel = "준회원";
			
			// 로그인 세션처리.
			session.setAttribute("sMid", vo.getMid());
			session.setAttribute("sNickName", vo.getNickName());
			session.setAttribute("sLevel", vo.getLevel());
			session.setAttribute("sStrLevel", strLevel);
			
			// 마지막 방문일 처리.
			session.setAttribute("sLastDate", vo.getLastDate());
			memberService.setLastDateUpdate(vo.getMid());
			
			// 아이디 저장 쿠키처리.
			if(idSave.equals("on")) {
				Cookie cookieMid = new Cookie("cMid", mid);
				cookieMid.setPath("/");
				// 쿠키의 만료시간을 7일로 지정
				cookieMid.setMaxAge(60*60*24*7);
				response.addCookie(cookieMid);
			}
			else {
				Cookie[] cookies = request.getCookies();
				if(cookies != null) {
					for(int i=0; i<cookies.length; i++) {
						if(cookies[i].getName().equals("cMid")) {
							cookies[i].setPath("/");
							cookies[i].setMaxAge(0);
							response.addCookie(cookies[i]);
							break;
						}
					}
				}
			}
		}
		else return "redirect:/Message/loginNo";
		return "redirect:/Message/loginOk?mid="+vo.getMid();
	}
	
	// 사용자 전용 방.
	@GetMapping("/Main")
	public String mainGet(HttpSession session, Model model) {
		String mid = session.getAttribute("sMid").toString();
		MemberVO mVO = memberService.getMemberMid(mid);
		model.addAttribute("mVO", mVO);
		return "member/main";
	}
	
	// 로그아웃.
	@GetMapping("/Logout")
	public String logoutGet(HttpSession session) {
		String mid = (String)session.getAttribute("sMid");
		session.removeAttribute("sMid");
		session.removeAttribute("sNickName");
		session.removeAttribute("sLevel");
		session.removeAttribute("sStrLevel");
		session.removeAttribute("sLastDate");
		session.removeAttribute("sEmailKey");
		return "redirect:/Message/logoutOk?mid="+mid;
	}
}
