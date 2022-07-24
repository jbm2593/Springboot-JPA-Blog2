package com.cos.blog2.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog2.dto.ResponseDto;
import com.cos.blog2.model.RoleType;
import com.cos.blog2.model.User;
import com.cos.blog2.service.UserService;

@RestController
public class UserApiController {
	
	@Autowired
	private UserService userService;
	
//	@Autowired
//	private HttpSession session; //이렇게 추가해서 해도 됌.
	
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) { //username, password, email
			System.out.println("UserApiController : save 함수 호출됨");
			
			userService.회원가입(user);
			 return new ResponseDto<Integer>(HttpStatus.OK.value(),1); //자바오브젝트를 JSON으로 변환해서 리턴(Jackson)
	}
	
	//전통적인 로그인 방식
	/*
	 * @PostMapping("api/user/login") public ResponseDto<Integer> login(@RequestBody
	 * User user, HttpSession session){
	 * System.out.println("UserApiController : login 호출됨"); User principal =
	 * userService.로그인(user); //principal (접근주체)
	 * 
	 * if(principal != null) { session.setAttribute("principal", principal); }
	 * 
	 * return new ResponseDto<Integer>(HttpStatus.OK.value(),1); //자바오브젝트를 JSON으로
	 * 변환해서 리턴(Jackson) }
	 */
}
