package com.cos.blog2.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog2.config.auth.PrincipalDetail;
import com.cos.blog2.dto.ResponseDto;
import com.cos.blog2.model.Board;
import com.cos.blog2.service.BoardService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
@Api(tags = {"게시글과 관련된 API"})
@RestController
public class BoardApiController {

	@Autowired
	private BoardService boardService;
	
	@ApiOperation(value = "게시글 작성 API", notes = "게시글 작성 역할을 합니다.")
	@PostMapping("/api/board")
	public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal) {
			boardService.글쓰기(board, principal.getUser());
			return new ResponseDto<Integer>(HttpStatus.OK.value(),1); //자바오브젝트를 JSON으로 변환해서 리턴(잭슨 라이브러리)
	}
	
	@ApiOperation(value = "게시글 삭제 API", notes = "게시글 삭제 역할을 합니다.")
	@DeleteMapping("/api/board/{id}")
	public ResponseDto<Integer> deleteById(@PathVariable int id){
		boardService.boardDelete(id);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
	
	@ApiOperation(value = "게시글 수정 API", notes = "게시글 수정 역할을 합니다.")
	@PutMapping("/api/board/{id}")
	public ResponseDto<Integer> update(@PathVariable int id, @RequestBody Board board){
		boardService.boardEdit(id,board);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1); 
	}
}
