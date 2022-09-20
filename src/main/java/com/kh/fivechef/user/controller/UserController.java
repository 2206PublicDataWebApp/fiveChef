package com.kh.fivechef.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kh.fivechef.user.domain.User;
import com.kh.fivechef.user.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService uService;
	
	@RequestMapping(value="/user/userJoinView.kh", method=RequestMethod.GET) //회원가입창 이동
	public String userJoinView() {
		return "user/join";
	}
	
	@RequestMapping(value="/user/register.kh", method=RequestMethod.POST) //회원가입
	public ModelAndView userJoin(ModelAndView mv,
			@ModelAttribute User user,
			@RequestParam("userId") String userId,
			@RequestParam("userPwd") String userPwd,
			@RequestParam("userName") String userName,
			@RequestParam("userPhone") String userPhone,
			@RequestParam("userEmail") String userEmail,
			@RequestParam("userBirth") String userBirth,
			Model model) {
		try {
			int result = uService.registerUser(user);
			if(result > 0) {
				mv.setViewName("/user/login");
			} else {
				mv.addObject("msg", "회원가입에 실패했습니다.").setViewName("common/errorPage");
			}
		} catch(Exception e) {
			mv.addObject("msg", e.toString()).setViewName("common/errorPage");
		}
		return mv;
	}
	
	@RequestMapping(value="/user/loginView.kh", method=RequestMethod.GET) //로그인 페이지 이동
	public String userLoginView() {
		return "/user/login";
	}
	
	@RequestMapping(value="/user/login.kh", method=RequestMethod.POST) //회원 로그인
	public ModelAndView userLogin(ModelAndView mv, @RequestParam("user-id") String userId, @RequestParam("user-pwd") String userPwd, HttpServletRequest request) {
		try {
			User user = new User(userId, userPwd);
			User loginUser = uService.loginUser(user);
			if(loginUser != null) {
				HttpSession session = request.getSession();
				session.setAttribute("loginUser", loginUser);
				mv.setViewName("redirect:/home.kh");
			} else {
				request.setAttribute("msg", "로그인 실패");
				request.setAttribute("url", "/user/loginView.kh");
				mv.setViewName("/user/alert");
			}
			
		} catch(Exception e) {
			mv.addObject("msg", e.toString()).setViewName("common/errorPage");
		}
		return mv;
	}
	
	@RequestMapping(value="/user/logout.kh", method=RequestMethod.GET) //로그아웃 -> 메인페이지 이동
	public ModelAndView userLogout(ModelAndView mv, HttpServletRequest request) {
		HttpSession session = request.getSession();
		if(session != null) {
			session.invalidate();
			request.setAttribute("msg", "로그아웃이 완료되었습니다.");
			request.setAttribute("url", "/home.kh");
			mv.setViewName("/user/alert");
		} else {
			mv.addObject("msg", "로그아웃 실패").setViewName("common/errorPage");
		}
		return mv;
	}
	
	@RequestMapping(value="/user/myPage.kh", method=RequestMethod.GET) //회원정보 보기
	public ModelAndView showMyPage(ModelAndView mv, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute("loginUser");
			String userId = user.getUserId();
			User uOne = uService.printOneUser(userId);
			mv.addObject("user", uOne);
			mv.setViewName("user/myPage");
		} catch (Exception e){
			mv.addObject("msg", e.toString()).setViewName("common/errorPage");
		}
		return mv;
	}
	
	@RequestMapping(value="/user/remove.kh", method=RequestMethod.GET) //회원탈퇴
	public ModelAndView removeUser(ModelAndView mv, HttpSession session, HttpServletRequest request) {
		try {	
			User user = (User)session.getAttribute("loginUser");
			String userId = user.getUserId();
			int result = uService.removeUser(userId);
			if (result > 0) {
				request.setAttribute("msg", "회원탈퇴가 완료되었습니다.");
				request.setAttribute("url", "/user/logout.kh");
				mv.setViewName("/user/alert");	
			} else {
				request.setAttribute("msg", "회원탈퇴가 정상적으로 이루어지지 않았습니다.");
				request.setAttribute("url", "user/myPage.kh");
				mv.setViewName("/user/alert");
			}
		} catch (Exception e) {
			mv.addObject("msg", e.toString()).setViewName("common/errorPage");
		}
		return mv;
	}
	
	@RequestMapping(value="/user/modify.kh", method=RequestMethod.POST) //회원 정보 수정
	public ModelAndView modifyUser(ModelAndView mv,
			@ModelAttribute User user,
			HttpServletRequest request,
			@RequestParam("userId") String userId,
			@RequestParam("userPwd") String userPwd,
			@RequestParam("userName") String userName,
			@RequestParam("userPhone") String userPhone,
			@RequestParam("userEmail") String userEmail,
			@RequestParam("userBirth") String userBirth,
			Model model) {
		try {
			int result = uService.modifyUser(user);
			if(result > 0) {
				request.setAttribute("msg", "회원님의 정보가 수정되었습니다.");
				request.setAttribute("url", "/user/myPage.kh");
				mv.setViewName("user/alert");
			} else {
				mv.addObject("msg", "정보 수정 실패").setViewName("common/errorPage");
			}
		} catch(Exception e) {
			mv.addObject("msg", e.toString()).setViewName("common/errorPage");
		}
		return mv;
	}
}