package com.example.spring02.model.message.dao;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.example.spring02.model.member.dao.MemberDAO;
import com.example.spring02.model.member.dto.MemberDTO;
import com.example.spring02.model.message.dto.MessageDTO;

@Repository //현재 클래스를 스프링에서 관리
public class MessageDAOImpl implements MessageDAO {
	@Inject // mybatis로 sql실행을 하기 위한 객체를 주입(연결)
	SqlSession sqlSession;

	@Override
	public void create(MessageDTO dto) {
		sqlSession.insert("message.create",dto);
		
	}

	@Override
	public MessageDTO readMessage(int mid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateState(int mid) {
		// TODO Auto-generated method stub
		
	}
	


}
