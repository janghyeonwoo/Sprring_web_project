package com.example.spring02.service.board;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.example.spring02.model.board.dao.ReplyDAO;
import com.example.spring02.model.board.dto.ReplyDTO;

@Service
public class ReplyServiceImpl implements ReplyService {
	@Inject
	ReplyDAO replyDao;
	@Override
	public List<ReplyDTO> list(Integer bno, int start, int end, HttpSession session) {
		// 
		return replyDao.list(bno, start, end);
	}

	@Override
	public int count(int bno) {
		
		return replyDao.count(bno);
	}

	@Override
	public void create(ReplyDTO dto) {
		replyDao.create(dto);

	}

	@Override
	public void update(ReplyDTO dto) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Integer rno) {
		// TODO Auto-generated method stub

	}

	@Override
	public ReplyDTO detail(int rno) {
		// TODO Auto-generated method stub
		return null;
	}

}
