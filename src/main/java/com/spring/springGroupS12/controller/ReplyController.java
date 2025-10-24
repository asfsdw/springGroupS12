package com.spring.springGroupS12.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.springGroupS12.service.MemberService;
import com.spring.springGroupS12.service.ReplyService;
import com.spring.springGroupS12.vo.ReplyVO;

@Controller
@RequestMapping("/reply")
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
		vo.setReStep(1);
		vo.setReOrder(1);
		
		// 부모댓글은 reStep=1, reOrder=1. 단, 대댓글의 경우는 부모댓글보다 큰 reOrder전부 +1, 자신은 부모댓글의 reStep, reOrder +1처리.
		if(replyIdx != 0) {
			// 대댓글의 부모 idx를 받아와서 설정.
			ReplyVO parentVO = replyService.getBoardParentReplyIdxCheck(replyIdx);
			
			// 부모의 reOrder보다 큰 reOrder +1. 단, 같은 parentIdx에 한해서.
			replyService.setReplyOrderUp(parentVO.getParentIdx(), parentVO.getReOrder());
			
			// 자기 reStep, reOrder는 부모 +1.
			vo.setReStep(parentVO.getReStep()+1);
			vo.setReOrder(parentVO.getReOrder()+1);
		}
		
		ReplyVO replyParentVO = replyService.getBoardParentReplyCheck(vo.getParentIdx());
		if(replyIdx == 0 && replyParentVO != null) {
			vo.setReOrder(replyParentVO.getReOrder()+1);
		}
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
	public int reviewInputPost(HttpSession session, ReplyVO vo) {
		int res = 0;
		vo.setPart("shop");
		
		ReplyVO searchVO = replyService.getReview(vo.getPart(), vo.getParentIdx());
		if(searchVO != null) return -1;
		res = replyService.setReply(vo);
		if(session.getAttribute("reviewPointGet"+vo.getMid()+"_"+vo.getParentIdx()) == null && res != 0) {
			res = memberService.setMemberPointUp(vo.getMid());
			if(res != 0) session.setAttribute("reviewPointGet"+vo.getMid()+"_"+vo.getParentIdx(), "reviewPointOk"+vo.getMid()+"_"+vo.getParentIdx());
			return 2;
		}
		else if(session.getAttribute("reviewPointGet"+vo.getMid()+"_"+vo.getParentIdx()) != null) return -2;
		else return res;
	}
	// 리뷰 삭제.
	@ResponseBody
	@PostMapping("/ReviewDelete")
	public int reviewDelete(int idx) {
		return replyService.setReviewDelete(idx);
	}
}
