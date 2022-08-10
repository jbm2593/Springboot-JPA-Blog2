package com.cos.blog2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cos.blog2.config.auth.PrincipalDetail;
import com.cos.blog2.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	
	
		// 컨트롤러에서 세션을 어떻게 찾는지?
		@GetMapping({"", "/"})
		public String index(Model model) { 
			model.addAttribute("boards", boardService.글목록());
			//   WEB-INF/views//WEB-INF/views/index.jsp
				return "index"; // viewResolver 작동!!
		}
		
		// USER 권한이 필요
		@GetMapping("/board/saveForm")
		public String saveForm() {
			return "board/saveForm";
			
		}
}
