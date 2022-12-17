package com.cos.blog2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.cos.blog2.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;
	
		// 컨트롤러에서 세션을 어떻게 찾는지?
	
		// 게시글 리스트 처리
		@GetMapping({"", "/"})
		public String index(Model model, @PageableDefault(size=6, sort="id", direction = Sort.Direction.DESC) Pageable pageable,
												@RequestParam(value = "searchKeyword", required = false)String searchKeyword) {
			if(searchKeyword == null) {
				model.addAttribute("boards", boardService.글목록(pageable));
			}
			else {
				model.addAttribute("boards", boardService.글검색기능(searchKeyword, pageable));
				model.addAttribute("searchKeyword", searchKeyword);
			}
			
			//   WEB-INF/views//WEB-INF/views/index.jsp
				return "index2"; // viewResolver 작동!!
		}
		
		//게시글 상세보기
		@GetMapping("/board/{id}")
		public String findById(@PathVariable int id, Model model) {
			model.addAttribute("board", boardService.글상세보기(id));
			return "board/detail";
		}
		
		//게시글 수정
		@GetMapping("/board/{id}/updateForm")
		public String updateForm(@PathVariable int id, Model model) {
			model.addAttribute("board", boardService.글상세보기(id));
			return "board/updateForm";
		}
		
		// USER 권한이 필요
		//게시글 작성
		@GetMapping("/board/saveForm")
		public String saveForm() {
			return "board/saveForm";
			
		}
}
