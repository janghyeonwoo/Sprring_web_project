package com.example.spring02.service.message;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.spring02.model.message.dao.MessageDAO;
import com.example.spring02.model.message.dao.PointDAO;
import com.example.spring02.model.message.dto.MessageDTO;

@Service
public class MessageServiceImpl implements MessageService {
	@Inject
	MessageDAO messageDao;
	@Inject
	PointDAO pointDao;
	
	@Transactional  //method 내부의 코드를 트랜잭션(거래처리 단위)으로 묶음 , 이메소드안에 모든 코드가 완료가 되어야 성공 아니면 롤백한다
	@Override
	public void addMessage(MessageDTO dto) {
		//서비스에서는 큰단위 dao는 세부 업무처리 
		messageDao.create(dto);
		pointDao.updatePoint(dto.getSender(),10);

	}

	@Override
	public MessageDTO readMessage(String usrid, int mid) {
		// TODO Auto-generated method stub
		return null;
	}

}
