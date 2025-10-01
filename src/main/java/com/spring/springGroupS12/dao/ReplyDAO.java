package com.spring.springGroupS12.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.springGroupS12.vo.ReplyVO;

public interface ReplyDAO {

	List<ReplyVO> getReply(@Param("parentIdx") int parentIdx);
	
	int setReply(@Param("vo") ReplyVO vo);

	ReplyVO getBoardParentReplyCheck(@Param("parentIdx") int parentIdx);

	ReplyVO getBoardParentReplyIdxCheck(@Param("idx") int idx);

	void setReplyOrderUp(@Param("parentIdx") int parentIdx, @Param("re_order") int re_order);

	int setReplyUpdate(@Param("vo") ReplyVO vo);

	int setReplyDelete(@Param("idx") int idx);

}
