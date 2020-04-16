package com.example.spring02.model.member.dao;

import org.springframework.stereotype.Repository;

import com.example.spring02.model.member.dto.MemberDTO;


public interface MemberDAO {
	public String loginCheck(MemberDTO dto);

}
