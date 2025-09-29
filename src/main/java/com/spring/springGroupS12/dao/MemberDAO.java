package com.spring.springGroupS12.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.springGroupS12.vo.MemberVO;

public interface MemberDAO {

	MemberVO getMemberMid(@Param("mid") String mid);

	MemberVO getMemberNickName(@Param("nickName") String nickName);

	int setSignUpMember(@Param("vo") MemberVO vo);

	List<MemberVO> getMemberEmail(@Param("email") String email);

	int setMemberTempPwd(@Param("mid") String mid, @Param("tempPwd") String tempPwd);

	void setLastDateUpdate(@Param("mid") String mid);

}
