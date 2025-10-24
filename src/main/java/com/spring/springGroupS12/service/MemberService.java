package com.spring.springGroupS12.service;

import java.util.List;

import com.spring.springGroupS12.vo.MemberLoginStatVO;
import com.spring.springGroupS12.vo.MemberVO;
import com.spring.springGroupS12.vo.SubScriptVO;

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

	int setSubScript(SubScriptVO vo);

	List<SubScriptVO> getSubScriptList(String mid);

	int getSubScript(SubScriptVO vo);

	List<MemberVO> getMemberList(int startIndexNo, int pageSize, String search, String searchStr);

	int setMemberUpdate(MemberVO vo);

	int setMemberPointUp(String mid);

	int setProductProgressSWUpdate(int idx, String subProgress);

	int setSubScriptDelete(int idx);

	List<MemberVO> getMemberListAdmin(int level);

	List<MemberVO> getMemberSearch(String search, String searchStr);

	int setMemberLevelUp(int idx, int level);

	List<MemberLoginStatVO> getMemberStatList();

	void setLoginCnt(String mid, int loginCnt);

	void setMemberPointRollback(String mid, int point);

	List<SubScriptVO> getNewSubScript();

	List<MemberVO> getNewMember();

}
