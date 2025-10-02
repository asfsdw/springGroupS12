package com.spring.springGroupS12.service;

import com.spring.springGroupS12.vo.MemberVO;

public interface MemberService {

	int getTotRecCnt(String flag);

	MemberVO getMemberMid(String mid);
	
	MemberVO getMemberNickName(String nickName);
	
	int setSignUpMember(MemberVO vo);
	
	MemberVO getMemberEmail(String email);
	
	int setMemberTempPwd(String mid, String tempPwd);
	
	void setLastDateUpdate(String mid);
	
	void setKakaoMemberInput(String mid, String pwd, String nickName, int age, String email);

	int setMemberDelete(String mid);

}
