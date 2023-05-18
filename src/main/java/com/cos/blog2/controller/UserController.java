package com.cos.blog2.controller;

import java.util.UUID;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.cos.blog2.dto.DefaultMessageDto;
import com.cos.blog2.model.KakaoProfile;
import com.cos.blog2.model.OAuthToken;
import com.cos.blog2.model.User;
import com.cos.blog2.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


//인증 안된사람은 /auth를 타고 
//인증된사람들은 /auth를 타지않음

//인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/ **허용
//그냥 주소가 /이면 index.jsp 허용
//static 이하에 있는 /js/**, /css**, /image/**

@Controller
public class UserController {
	
	@Value("${cos.key}")
	private String cosKey;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	public OAuthToken oauthToken;
	
	private static final String SEND_SUCCESS_MSG = "메시지 전송에 성공했습니다.";
	private static final String SEND_FAIL_MSG = "메시지 전송에 실패했습니다.";

	private static final String SUCCESS_CODE = "0"; //kakao api에서 return해주는 success code 값

	
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

	/* ************카카오 로그인 구현************* */
	@GetMapping("/auth/kakao/callback")
	public  String kakaoCallback(String code) { // Data를 리턴해주는 컨트롤러 함수

		// POST 방식으로 key = value 데이터를 요청 (카카오쪽으로)
		// Retrofit2
		// OkHttp
		// RestTemplate

		RestTemplate rt = new RestTemplate();

		// HttpHeader 오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		// HttpBody 오브젝트 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "1e39c72b841ef80ff98bcaa9af519295");
		params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
		params.add("code", code);

		// HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

		// Http 요청하기 - POST 방식으로 - 그리고 response 변수의 응답 받음.
		ResponseEntity<String> response = rt.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST,
				kakaoTokenRequest, String.class);

		// @Gson, Json Simple, ObjectMapper
		ObjectMapper objectMapper = new ObjectMapper();
		oauthToken = null;
		try {
			oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		System.out.println("카카오 엑세스 토큰 : " + oauthToken.getAccess_token());

		/* *****************카카오 사용자 정보 가져오기************** */
		RestTemplate rt2 = new RestTemplate();

		// HttpHeader 오브젝트 생성
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		headers2.add("Authorization", "Bearer " + oauthToken.getAccess_token());
		// HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 = new HttpEntity<>(headers2);

		// Http 요청하기 - POST 방식으로 - 그리고 response 변수의 응답 받음.
		ResponseEntity<String> response2 = rt2.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST,
				kakaoProfileRequest2, String.class);
		System.out.println(response2.getBody());
		
		ObjectMapper objectMapper2 = new ObjectMapper();
		KakaoProfile kakaoProfile = null;
		try {
			kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		// User 오브젝트 : username, passwword, email
		 System.out.println("카카오 아이디(번호) : " + kakaoProfile.getId());
		 System.out.println("카카오 이메일 : " + kakaoProfile.getKakao_account().getEmail());
		 System.out.println("블로그서버 유저네임 : " + kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId());
		 System.out.println("블로그서버 이메일 : " + kakaoProfile.getKakao_account().getEmail());
		 UUID garbagePassword =  UUID.randomUUID();
		 // UUID란 -> 중복되지 않는 어떤 특정 값을 만들어내는 알고리즘
		 System.out.println("블로그서버 패스워드 : " + cosKey);
		 
		 User kakaouser = User.builder().
				 username(kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId())
				 .password(cosKey)
				 .email(kakaoProfile.getKakao_account().getEmail())
				 .oauth("kakao")
				 .build();
		 
		 //가입자 혹은 비가입자 체크 처리
		 User originUser = userService.userFind(kakaouser.getUsername()); //회원찾기

		 if(originUser.getUsername() == null) {
			 System.out.println("기존 회원이 아니기에 자동 회원가입을 진행합니다");
			 userService.userJoin(kakaouser); //회원가입
		 }
		 
		 System.out.println("자동 로그인을 진행합니다.");
		 //로그인처리
		 Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaouser.getUsername(),cosKey));
		 SecurityContextHolder.getContext().setAuthentication(authentication);

		return "redirect:/";
		
		//return "카카오 토큰 요청 완료 : 토큰요청에 대한 응답 :   " + response2.getBody();
	}
	
	/* *****************카카오 메시지 보내기( 나한테 보내기)************** */
	@GetMapping("/auth/kakao/message")
	public String kakaoMessage() { // Data를 리턴해주는 컨트롤러 함수
		System.out.println("@@@@@카카오 메시지 보내기 컨트롤러 진입@@@@");
		
		DefaultMessageDto myMsg = new DefaultMessageDto();
		myMsg.setBtnTitle("자세히보기");
		myMsg.setMobileUrl("");
		myMsg.setObjType("text");
		myMsg.setWebUrl("");
		myMsg.setText("메시지 테스트입니다.");
		
		JSONObject linkObj = new JSONObject();
		linkObj.put("web_url", myMsg.getWebUrl());
		linkObj.put("mobile_web_url", myMsg.getMobileUrl());
		
		System.out.println("myMsg : " + myMsg);
		System.out.println("linkObj : " + linkObj);

		JSONObject templateObj = new JSONObject();
		templateObj.put("object_type", myMsg.getObjType());
		templateObj.put("text", myMsg.getText());
		templateObj.put("link", linkObj);
		templateObj.put("button_title", myMsg.getBtnTitle());
		
		System.out.println("templateObj : " + templateObj);

		
		RestTemplate rt3 = new RestTemplate();
		
		System.out.println("카카오 메시지 보낼때 카카오 엑세스 토큰 : " + oauthToken.getAccess_token());
		
		// HttpHeader 오브젝트 생성
		HttpHeaders headers3 = new HttpHeaders();
		headers3.add("Authorization", "Bearer " + oauthToken.getAccess_token());
		System.out.println("headers3 : " + headers3);
		
		// HttpBody 오브젝트 생성
		MultiValueMap<String, String> params3 = new LinkedMultiValueMap<>();
		params3.add("template_object", templateObj.toString());
		System.out.println("params3 : " + params3);

		// HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoMassegeRequest3 = new HttpEntity<>(params3, headers3);

		// Http 요청하기 - POST 방식으로 - 그리고 response 변수의 응답 받음.
		ResponseEntity<String> response3 = rt3.exchange("https://kapi.kakao.com/v2/api/talk/memo/default/send",
				HttpMethod.POST, kakaoMassegeRequest3, String.class);
		System.out.println(response3.getBody());
		
		String resultCode = "";
		JSONObject jsonData = new JSONObject(response3.getBody());
		resultCode = jsonData.get("result_code").toString();
		System.out.println("*******resultCode **********: " + resultCode);
		
		return "redirect:/";
	}
}
