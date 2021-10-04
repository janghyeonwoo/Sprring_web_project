package com.example.spring02.controller.admin;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.spring02.model.member.dto.MemberDTO;
import com.example.spring02.service.admin.AdminService;

@Controller
@RequestMapping("/admin/*")
public class AdminController {
	@Inject
	AdminService adminService;
	
	
	@RequestMapping("login.do")
	public String login() {
		return "admin/login";
	}
	@RequestMapping("login_check.do")
	public ModelAndView login_check(MemberDTO dto, HttpSession session, ModelAndView mav) {
		String name = adminService.loginCheck(dto);
		if(name!=null) { //로그인성공
			session.setAttribute("admin_userid", dto.getUserid());
			session.setAttribute("admin_name", name);
			session.setAttribute("userid", dto.getUserid());
			session.setAttribute("name", name);
			mav.setViewName("admin/admin");
			mav.addObject("message", "success");
			/*로그3*/
		}else {
			mav.setViewName("admin/login");
			mav.addObject("message","error");
		}
		return mav;
	}
	//관리자 로그아웃 처리
	@RequestMapping("logout.do")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/admin/login.do";
	}
}
