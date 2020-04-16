package com.example.spring02.service.member;

import javax.servlet.http.HttpSession;

import com.example.spring02.model.member.dto.MemberDTO;

public interface MemberService {
	//HttpSession 사용자 인증정보를 처리하는 클래스
	public String loginCheck(MemberDTO dto, HttpSession session);
	public void logout(HttpSession session);

}
