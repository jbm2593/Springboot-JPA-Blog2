package com.cos.blog2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//인증 안된사람은 /auth를 타고 
//인증된사람들은 /auth를 타지않음

//인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/ **허용
//그냥 주소가 /이면 index.jsp 허용
//static 이하에 있는 /js/**, /css**, /image/**

@Controller
public class UserController {
	
	@GetMapping("/auth/joinForm")
	public String joinForm() {	
		return "user/joinForm";
	}
	
//	@GetMapping("/auth/loginForm")
//	public String loginForm() {	
//		return "user/loginForm";
//	}
	
	@GetMapping("/user/updateForm")
	public String updateForm() {	
		return "user/updateForm";
	}
	
	@GetMapping("/auth/loginForm")
	public String login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "exception", required = false) String exception, Model model) {
		model.addAttribute("error", error);
		model.addAttribute("exception", exception);
		return "/user/loginForm";
	}
}
