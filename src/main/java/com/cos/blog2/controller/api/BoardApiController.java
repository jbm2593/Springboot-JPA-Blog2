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
import com.cos.blog2.dto.ReplySaveRequestDto;
import com.cos.blog2.dto.ResponseDto;
import com.cos.blog2.model.Board;
import com.cos.blog2.service.BoardService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@Api(tags = {"게시글과 관련된 API"})
@RestController
public class BoardApiController {

	@Autowired
	private BoardService boardService;
	
	@ApiOperation(value = "게시글 작성 API", notes = "게시글 작성 역할을 합니다.")
	@PostMapping("/api/board")
	public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal) {
			boardService.boardWrite(board, principal.getUser());
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
	
//	@ApiOperation(value = "댓글 작성 API", notes = "댓글 작성 역할을 합니다.")
//	@PostMapping("/api/board/{boardId}/reply")
//	public ResponseDto<Integer> replySave(@PathVariable int boardId, @RequestBody Reply reply, @AuthenticationPrincipal PrincipalDetail principal) {
//			boardService.replyWrite(principal.getUser(), boardId, reply);
//			return new ResponseDto<Integer>(HttpStatus.OK.value(),1); //자바오브젝트를 JSON으로 변환해서 리턴(잭슨 라이브러리)
//	}
	
	//데이터 받을 때 컨트롤러에서 dto 만들어서 받는게 좋다.
	//dto 사용하지 않은 이유는!! 토이프로젝트이기 때문에 규모가 작아서 안만들었음.
	//아래는 dto 사용해서 받음.
	@ApiOperation(value = "댓글 작성 API", notes = "댓글 작성 역할을 합니다.")
	@PostMapping("/api/board/{boardId}/reply")
	public ResponseDto<Integer> replySave(@RequestBody ReplySaveRequestDto replySaveRequestDto) {
			boardService.replyWrite(replySaveRequestDto);
			return new ResponseDto<Integer>(HttpStatus.OK.value(),1); 
	}
	
	@DeleteMapping("api/board/{boardId}/reply/{replyId}")
	public ResponseDto<Integer> replySave(@PathVariable int replyId) {
		boardService.replyDelete(replyId);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1); 
	}
}
