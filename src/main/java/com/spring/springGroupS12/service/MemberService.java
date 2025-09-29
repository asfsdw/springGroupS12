package com.spring.springGroupS12.service;

import java.util.List;

import com.spring.springGroupS12.vo.MemberVO;

public interface MemberService {

	MemberVO getMemberMid(String mid);

	MemberVO getMemberNickName(String nickName);

	int setSignUpMember(MemberVO vo);

	List<MemberVO> getMemberEmail(String email);

	int setMemberTempPwd(String mid, String tempPwd);

	void setLastDateUpdate(String mid);

}
