package com.spring.springGroupS12.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.springGroupS12.dao.ReplyDAO;
import com.spring.springGroupS12.vo.ReplyVO;

@Service
public class ReplyServiceImpl implements ReplyService {
	@Autowired
	ReplyDAO replyDAO;
	
	@Override
	public List<ReplyVO> getReply(int parentIdx) {
		return replyDAO.getReply(parentIdx);
	}

	@Override
	public int setReply(ReplyVO vo) {
		return replyDAO.setReply(vo);
	}

	@Override
	public ReplyVO getBoardParentReplyCheck(int parentIdx) {
		return replyDAO.getBoardParentReplyCheck(parentIdx);
	}

	@Override
	public ReplyVO getBoardParentReplyIdxCheck(int idx) {
		return replyDAO.getBoardParentReplyIdxCheck(idx);
	}

	@Override
	public void setReplyOrderUp(int parentIdx, int re_order) {
		replyDAO.setReplyOrderUp(parentIdx, re_order);
	}

	@Override
	public int setReplyUpdate(ReplyVO vo) {
		return replyDAO.setReplyUpdate(vo);
	}

	@Override
	public int setReplyDelete(int idx) {
		return replyDAO.setReplyDelete(idx);
	}
}
