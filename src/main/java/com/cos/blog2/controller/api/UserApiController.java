package com.cos.blog2.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog2.dto.ResponseDto;
import com.cos.blog2.model.User;
import com.cos.blog2.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@Api(tags = {"회원과 관련된 API"})
@RestController
public class UserApiController {
	
	@Autowired
	private UserService userService;
	
//	@Autowired
//	private HttpSession session; //이렇게 추가해서 해도 됌.
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@ApiOperation(value = "회원가입 API", notes = "회원가입 역할을 합니다.")
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) { //username, password, email
			System.out.println("UserApiController : save 함수 호출됨");
			
			userService.회원가입(user);
			 return new ResponseDto<Integer>(HttpStatus.OK.value(),1); //자바오브젝트를 JSON으로 변환해서 리턴(Jackson)
	}
	
	@ApiOperation(value = "회원 수정 API", notes = "회원 수정 역할을 합니다.")
	@PutMapping("/user")
	public ResponseDto<Integer> update(@RequestBody User user){ // key = value, x-www-form-urlencoded 
		userService.회원수정(user);

		/*
		 *여기서는 트랜잭션이 종료되기 때문에 DB에 값은 변경이 됐음.
		 *하지만 세션값은 변경되지 않은 상태이기 때문에 우리가 직접 세션값을 변경해줄 것임.
		 *내가 강제로 Authentication 객체를 만들 순 없기 때문에 AuthenticationManager 를 통해 Authentication 객체를 생성해야 함.
		 
		 *username과 password를 통해 다시 UsernamePasswordAuthenicatinToken 발급후 AuthenticationManager에게 던져서 
		 *새로 덮어쓸(?)authentiction객체를 만든다. 그렇다면 로그인할때랑 똑같은 로직을 타는거랑 같다고 생각하면 된다(발급된 토큰 중 username을 PrincipalDetailService에게 던져 다시 객체를 DB에서 꺼내오고 등등..)
		 
		*1. AuthenticationManager 객체 활용하기 위해 SecurityConfig에 생성하고 @Bean을 통해 스프링이 IoC로 관리하게 함
		*2. AuthenticationManager 객체를 활용해 수정된 username과 password로 새로운 토큰을 만들어서 Authentication 객체를 생성함
		*3. HttpSession의 SecurityContextHolder 안에 SecurityContext 안에 있는 Authentication 객체를 우리가 만든 새로운 Authentication으로 덮어씀(?)
		*/
		
		//세션등록
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
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
