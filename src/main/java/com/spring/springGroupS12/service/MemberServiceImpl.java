package com.spring.springGroupS12.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.springGroupS12.dao.MemberDAO;
import com.spring.springGroupS12.vo.MemberVO;
import com.spring.springGroupS12.vo.SubScriptVO;

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	MemberDAO memberDAO;

	@Override
	public int getTotRecCnt(String flag) {
		return memberDAO.getTotRecCnt(flag);
	}
	
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
	public MemberVO getMemberEmail(String email) {
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

	@Override
	public void setKakaoMemberInput(String mid, String pwd, String nickName, int age, String email) {
		memberDAO.setKakaoMemberInput(mid, pwd, nickName, age, email);
	}

	@Override
	public int setMemberDelete(String mid) {
		return memberDAO.setMemberDelete(mid);
	}

	@Override
	public int setSubScript(SubScriptVO vo) {
		return memberDAO.setSubScript(vo);
	}

	@Override
	public List<SubScriptVO> getSubScriptList(String mid) {
		return memberDAO.getSubScriptList(mid);
	}

	@Override
	public int getSubScript(SubScriptVO vo) {
		return memberDAO.getSubScript(vo);
	}

	@Override
	public List<MemberVO> getMemberList(int startIndexNo, int pageSize, int level) {
		return memberDAO.getMemberList(startIndexNo, pageSize, level);
	}

	@Override
	public int setMemberUpdate(MemberVO vo) {
		return memberDAO.setMemberUpdate(vo);
	}

	@Override
	public int setMemberPointUp(String mid) {
		return memberDAO.setMemberPointUp(mid);
	}

	@Override
	public int setProductProgressSWUpdate(int idx, String subProgress) {
		return memberDAO.setProductProgressSWUpdate(idx, subProgress);
	}

	@Override
	public int setSubScriptDelete(int idx) {
		return memberDAO.setSubScriptDelete(idx);
	}

	@Override
	public List<MemberVO> getMemberListAdmin(int level) {
		return memberDAO.getMemberListAdmin(level);
	}

	@Override
	public List<MemberVO> getMemberSearch(String search, String searchStr) {
		return memberDAO.getMemberSearch(search, searchStr);
	}

	@Override
	public int setMemberLevelUp(int idx, int level) {
		return memberDAO.setMemberLevelUp(idx, level);
	}

}
