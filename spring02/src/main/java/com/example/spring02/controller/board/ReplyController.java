package com.example.spring02.controller.board;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.spring02.model.board.dto.ReplyDTO;
import com.example.spring02.service.board.Pager;
import com.example.spring02.service.board.ReplyService;

@RestController
@RequestMapping("/reply/*")
public class ReplyController {
	@Inject
	ReplyService replyService;
	
	@RequestMapping("insert.do")
	public void insert(ReplyDTO dto, HttpSession session) {
		String userid = (String) session.getAttribute("userid");
		dto.setReplyer(userid);
		replyService.create(dto);
	}
	
	@RequestMapping("list.do") 
	public ModelAndView list(int bno,@RequestParam(defaultValue ="1") int curPage,
			ModelAndView mav, HttpSession session) {
		int count =replyService.count(bno);
		Pager pager = new Pager(count,curPage);
		int start =pager.getPageBegin();
		int end = pager.getPageEnd();
		List<ReplyDTO> list = replyService.list(bno, start, end,session);
		mav.setViewName("board/reply_list");
		mav.addObject("list", list);
		mav.addObject("pager", pager);
		return mav;
	}

}
