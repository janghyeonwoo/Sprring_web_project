package com.example.spring02.service.memo;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.example.spring02.model.memo.dao.MemoDAO;
import com.example.spring02.model.memo.dto.MemoDTO;
@Service //bean으로 만듬
public class MemoServiceImpl implements MemoService {
	@Inject  //여기서 memoDAO는 인터페이스여서 객체를 만들수없는데 사용할수 있는 이유는 어노테이션때문이다. 
	MemoDAO memoDao; //스프링에서 생성한 DAO객체가 연결됨
	@Override
	public List<MemoDTO> list() {
		 
		return memoDao.list();
	}

	@Override
	public void insert(MemoDTO dto) {
		// TODO Auto-generated method stub

	}

	@Override
	public void insert(String writer, String memo) {
		memoDao.insert(writer,memo);

	}

	@Override
	public MemoDTO memo_view(int idx) {
		
		return memoDao.memo_view(idx);
	}

	@Override
	public void update(MemoDTO dto) {
		memoDao.memo_update(dto);
		

	}

	@Override
	public void delete(int idx) {
		memoDao.memo_delete(idx);

	}

}
