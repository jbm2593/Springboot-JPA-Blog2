package com.cos.blog2.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog2.dto.ResponseDto;

@ControllerAdvice // 모든 exception이 발생하면 GlobalExceptionHandler 클래스로 들어옴
@RestController
public class GlobalExceptionHandler {

		//IllegalArgumentException  에러가 발생하면 그 Exception에 대한 에러는 이 함수에 전달을 해준다 스프링이.
		@ExceptionHandler(value = Exception.class)
		public ResponseDto<String> handleArgumentException(Exception e) {
			//에러 발생시, 상태값과 에러메시지 리턴
			return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());			
		}
		
//		//모든 Exception에 대한 것을 처리하고 싶으면 가장 부모인 Exception 클래스를 불러온다.
//		@ExceptionHandler(value = Exception.class)
//		public String handleArgumentException(Exception e) {
//			return "<h1>" + e.getMessage() + "</h1>";
//		}
				
}
