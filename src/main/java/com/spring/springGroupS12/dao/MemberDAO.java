package com.spring.springGroupS12.dao;

import org.apache.ibatis.annotations.Param;

import com.spring.springGroupS12.vo.MemberVO;

public interface MemberDAO {

	int getTotRecCnt(@Param("flag") String flag);

	MemberVO getMemberMid(@Param("mid") String mid);

	MemberVO getMemberNickName(@Param("nickName") String nickName);

	int setSignUpMember(@Param("vo") MemberVO vo);

	MemberVO getMemberEmail(@Param("email") String email);

	int setMemberTempPwd(@Param("mid") String mid, @Param("tempPwd") String tempPwd);

	void setLastDateUpdate(@Param("mid") String mid);

	void setKakaoMemberInput(@Param("mid") String mid, @Param("pwd") String pwd, @Param("nickName") String nickName, @Param("age") int age, @Param("email") String email);

}
