package com.spring.springGroupS12.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.springGroupS12.service.MemberService;
import com.spring.springGroupS12.service.ReplyService;
import com.spring.springGroupS12.vo.ReplyVO;

@Controller
public class ReplyController {
	@Autowired
	ReplyService replyService;
	@Autowired
	MemberService memberService;
	
	// 댓글 입력.
	@ResponseBody
	@PostMapping("/ReplyInput")
	public int replyInputPost(ReplyVO vo,
			@RequestParam(name = "replyIdx", defaultValue = "0", required = false)int replyIdx) {
		if(vo.getMid().equals("")) {
			vo.setMid("noMember");
			vo.setNickName("비회원");
		}
		vo.setPart("board");
		vo.setRe_step(1);
		vo.setRe_order(1);
		
		// 부모댓글은 re_step=1, re_order=1. 단, 대댓글의 경우는 부모댓글보다 큰 re_order전부 +1, 자신은 부모댓글의 re_step, re_order +1처리.
		ReplyVO replyParentVO = replyService.getBoardParentReplyCheck(vo.getParentIdx());
		
		// 대댓글의 부모 idx를 받아와서 설정.
		ReplyVO parentvo = replyService.getBoardParentReplyIdxCheck(replyIdx);
		
		// 부모의 re_order보다 큰 re_order +1. 단, 같은 boardIdx에 한해서.
		replyService.setReplyOrderUp(parentvo.getParentIdx(), parentvo.getRe_order());
		
		// 자기 re_step, re_order는 부모 +1.
		vo.setRe_step(parentvo.getRe_step()+1);
		vo.setRe_order(parentvo.getRe_order()+1);
		// 첫댓글이면 re_order =1.
		if(replyParentVO != null) vo.setRe_order(replyParentVO.getRe_order()+1);
		return replyService.setReply(vo);
	}
	// 댓글 수정.
	@ResponseBody
	@PostMapping("/ReplyUpdate")
	public int replyUpdatePost(ReplyVO vo) {
		return replyService.setReplyUpdate(vo);
	}
	// 댓글 삭제.
	@ResponseBody
	@PostMapping("/ReplyDelete")
	public int replyDeletePost(int idx) {
		return replyService.setReplyDelete(idx);
	}
	
	// 리뷰 입력.
	@Transactional
	@ResponseBody
	@PostMapping("/ReviewInput")
	public String reviewInputPost(HttpSession session, ReplyVO vo) {
		int res = 0;
		vo.setPart("shop");
		
		ReplyVO searchVO = replyService.getReview(vo.getPart(), vo.getParentIdx());
		if(searchVO != null) return "-1";
		res = replyService.setReply(vo);
		if(session.getAttribute("reviewPointGet"+vo.getMid()+"_"+vo.getParentIdx()) == null && res != 0) {
			res = memberService.setMemberPointUp(vo.getMid());
			if(res != 0) session.setAttribute("reviewPointGet"+vo.getMid()+"_"+vo.getParentIdx(), "reviewPointOk"+vo.getMid()+"_"+vo.getParentIdx());
		}
		else return "-2";
		
		return res+"";
	}
	// 리뷰 삭제.
	@ResponseBody
	@PostMapping("/ReviewDelete")
	public int reviewDelete(int idx) {
		return replyService.setReviewDelete(idx);
	}
}
