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
import com.spring.springGroupS12.vo.SubScriptVO;

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
		MemberVO vo = memberService.getMemberEmail(email);
		if(vo != null) res = 1;
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
	public MemberVO memberIdFindPost(String email) {
		MemberVO vo = memberService.getMemberEmail(email);
		return vo;
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
		String tempPwd = UUID.randomUUID().toString().substring(0, 8);
		int res = memberService.setMemberTempPwd(mid, tempPwd);
		// 비밀번호를 임시 비밀번호로 수정에 성공했을 때.
		if(res != 0) {
			projectProvide.mailSend(email, "임시 비밀번호입니다.", "임시 비밀번호: "+tempPwd);
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
		if(vo != null && vo.getUserDelete().equals("활동") && passwordEncode.matches(pwd, vo.getPwd())) {
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
	// 카카오 로그인.
	@GetMapping("/KakaoLogin")
	public String kakaoLoginPost(HttpSession session, 
			String nickName, String email, String ageRange, String accessToken) throws MessagingException {
		// 카카오에서는 연령대(30~39...) 처리.
		if(ageRange.length()>1) ageRange = ageRange.substring(0,1)+"0";
		if(ageRange.length()<2 || ageRange.equals("10")) return "redirect:/Message/minor";
		session.setAttribute("sAccessToken", accessToken);
		
		MemberVO vo = memberService.getMemberEmail(email);
		
		// 신규회원인지에 대한 정의(신규회원 OK, 기존회원 NO).
		String newMember = "NO";
		// DB에 존재하지 않는 이메일이면 회원가입처리.
		if(vo == null) {
			// 아이디를 이메일에서 가져온다.
			String mid = email.substring(0, email.indexOf("@"));
			vo = memberService.getMemberMid(mid);
			// 아이디가 이미 존재한다면 로그인 유도.
			if(vo != null) return "redirect:/Message/midSameSearch";
			
			// 임시 비밀번호 처리.
			String pwd = UUID.randomUUID().toString().substring(0, 8);
			// 연령대 int 처리.
			int age = Integer.parseInt(ageRange);
			// 새로 발급된 비밀번호를 암호화 시켜서 DB에 회원가입 처리.
			memberService.setKakaoMemberInput(mid, passwordEncode.encode(pwd), nickName, age, email);
			
			// 새로 발급받은 임시 비밀번호를 메일로 전송.
			projectProvide.mailSend(email, "임시 비밀번호입니다.", "임시 비밀번호: "+pwd);
			
			// 새로 가입처리된 회원의 정보를 다시 vo에 담아준다.
			vo = memberService.getMemberMid(mid);
			
			// 비밀번호를 새로 발급처리했을때 sLogin세션을 발생시켜주고, memberMain창에 비밀번호 변경메세지를 지속적으로 뿌려준다.
			session.setAttribute("sLoginNew", "OK");
			
			newMember = "OK";
		}
		else if(vo.getUserDelete().equals("삭제")) return "redirect:/Message/loginNo";
		
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
		
		// 로그인 완료후 모든 처리가 끝나면 필요한 메세지처리후 memberMain으로 보낸다.
		if(newMember.equals("NO")) return "redirect:/Message/loginOk?mid="+vo.getMid();
		else return "redirect:/Message/newLoginOk?mid="+vo.getMid();
	}
	
	// 사용자 전용 방.
	@GetMapping("/Main")
	public String mainGet(HttpSession session, Model model) {
		String mid = session.getAttribute("sMid").toString();
		MemberVO mVO = memberService.getMemberMid(mid);
		model.addAttribute("mVO", mVO);
		return "member/main";
	}
	
	// 신청 관련.
	@GetMapping("SubScript")
	public String subScriptGet(HttpSession session, Model model) {
		String mid = session.getAttribute("sMid").toString();
		List<SubScriptVO> vos = memberService.getSubScriptList(mid);
		
		model.addAttribute("vos", vos);
		return "member/subScript";
	}
	// 신청.
	@PostMapping("SubScript")
	public String subScriptPost(SubScriptVO vo) {
		int res = memberService.setSubScript(vo);
		if(res != 0) return "redirect:/Message/subScriptOk";
		else return "redirect:/Message/subScriptNo";
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
		session.removeAttribute("sAccessToken");
		return "redirect:/Message/logoutOk?mid="+mid;
	}
	
	// 회원탈퇴.
	@ResponseBody
	@PostMapping("/Delete")
	public int memberDeletePost(HttpSession session) {
		String mid = session.getAttribute("sMid").toString();
		int res = memberService.setMemberDelete(mid);
		if(res != 0) {
			session.removeAttribute("sMid");
			session.removeAttribute("sNickName");
			session.removeAttribute("sLevel");
			session.removeAttribute("sStrLevel");
			session.removeAttribute("sLastDate");
			session.removeAttribute("sEmailKey");
			session.removeAttribute("sAccessToken");
			return res;
		}
		else return res;
	}
}
