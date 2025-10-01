package com.spring.springGroupS12.service;

import java.util.List;

import com.spring.springGroupS12.vo.ReplyVO;

public interface ReplyService {

	List<ReplyVO> getReply(int parentIdx);
	
	int setReply(ReplyVO vo);

	ReplyVO getBoardParentReplyCheck(int parentIdx);

	ReplyVO getBoardParentReplyIdxCheck(int idx);

	void setReplyOrderUp(int parentIdx, int re_order);

	int setReplyUpdate(ReplyVO vo);

	int setReplyDelete(int idx);

}
