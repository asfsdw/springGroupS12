package com.spring.springGroupS12.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.springGroupS12.vo.MemberVO;
import com.spring.springGroupS12.vo.SubScriptVO;

public interface MemberDAO {

	int getTotRecCnt(@Param("flag") String flag);

	MemberVO getMemberMid(@Param("mid") String mid);

	MemberVO getMemberNickName(@Param("nickName") String nickName);

	int setSignUpMember(@Param("vo") MemberVO vo);

	MemberVO getMemberEmail(@Param("email") String email);

	int setMemberTempPwd(@Param("mid") String mid, @Param("tempPwd") String tempPwd);

	void setLastDateUpdate(@Param("mid") String mid);

	void setKakaoMemberInput(@Param("mid") String mid, @Param("pwd") String pwd, @Param("nickName") String nickName, @Param("age") int age, @Param("email") String email);

	int setMemberDelete(@Param("mid") String mid);

	int setSubScript(@Param("vo") SubScriptVO vo);

	List<SubScriptVO> getSubScriptList(@Param("mid") String mid);

	int getSubScript(@Param("vo") SubScriptVO vo);

	List<MemberVO> getMemberList(@Param("startIndexNo") int startIndexNo, @Param("pageSize") int pageSize, @Param("level") int level);

	int setMemberUpdate(@Param("vo") MemberVO vo);

	int setMemberPointUp(@Param("mid") String mid);

	int setProductProgressSWUpdate(@Param("idx") int idx, @Param("subProgress") String subProgress);

	int setSubScriptDelete(@Param("idx") int idx);

	List<MemberVO> getMemberListAdmin(@Param("level") int level);

	List<MemberVO> getMemberSearch(@Param("search") String search, @Param("searchStr") String searchStr);

	int setMemberLevelUp(@Param("idx") int idx, @Param("level") int level);

}
