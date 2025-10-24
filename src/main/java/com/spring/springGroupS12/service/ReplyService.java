package com.spring.springGroupS12.service;

import java.util.List;

import com.spring.springGroupS12.vo.ReplyVO;

public interface ReplyService {

	List<ReplyVO> getReply(int parentIdx);
	
	int setReply(ReplyVO vo);

	ReplyVO getBoardParentReplyCheck(int parentIdx);

	ReplyVO getBoardParentReplyIdxCheck(int idx);

	void setReplyOrderUp(int parentIdx, int reOrder);

	int setReplyUpdate(ReplyVO vo);

	int setReplyDelete(int idx);

	ReplyVO getReview(String part, int parentIdx);

	List<ReplyVO> getProductReplyList(String part, int parentIdx);

	int setReviewDelete(int idx);

}
