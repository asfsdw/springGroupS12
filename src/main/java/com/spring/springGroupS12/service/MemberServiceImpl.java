package com.spring.springGroupS12.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.springGroupS12.dao.MemberDAO;
import com.spring.springGroupS12.vo.MemberVO;

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	MemberDAO memberDAO;

	@Override
	public MemberVO getMemberMid(String mid) {
		return memberDAO.getMemberMid(mid);
	}

	@Override
	public MemberVO getMemberNickName(String nickName) {
		return memberDAO.getMemberNickName(nickName);
	}

	@Override
	public int setSignUpMember(MemberVO vo) {
		return memberDAO.setSignUpMember(vo);
	}

	@Override
	public List<MemberVO> getMemberEmail(String email) {
		return memberDAO.getMemberEmail(email);
	}

	@Override
	public int setMemberTempPwd(String mid, String tempPwd) {
		return memberDAO.setMemberTempPwd(mid, tempPwd);
	}

	@Override
	public void setLastDateUpdate(String mid) {
		memberDAO.setLastDateUpdate(mid);
	}
}
