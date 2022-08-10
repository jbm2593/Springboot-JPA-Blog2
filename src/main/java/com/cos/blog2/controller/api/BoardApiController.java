package com.cos.blog2.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog2.config.auth.PrincipalDetail;
import com.cos.blog2.dto.ResponseDto;
import com.cos.blog2.model.Board;
import com.cos.blog2.service.BoardService;

@RestController
public class BoardApiController {

	@Autowired
	private BoardService boardService;
	
	@PostMapping("/api/board")
	public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal) {
			boardService.글쓰기(board, principal.getUser());
			 return new ResponseDto<Integer>(HttpStatus.OK.value(),1); //자바오브젝트를 JSON으로 변환해서 리턴(Jackson)
	}
}
