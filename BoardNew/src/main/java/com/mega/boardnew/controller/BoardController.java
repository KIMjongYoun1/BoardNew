package com.mega.boardnew.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.mega.boardnew.bean.BoardDAO;
import com.mega.boardnew.bean.BoardVO;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/board/*")
@Slf4j
public class BoardController {
	
	@Autowired
	private BoardDAO dao;
	
	@GetMapping("list")
	public void list(Model model) {
		log.info("------------------------------");
		log.info("[BoardController] list called");
		log.info("------------------------------");
		dao.getList().forEach(board -> log.info(board.toString()));
		model.addAttribute("list", dao.getList());
	}
	
	@GetMapping("write")
	public void write() {
		
	}
	
	@PostMapping("write")
	@ResponseBody
	// 글을쓰고, 리스트 화면으로 이동하십시오.
	public RedirectView writer(BoardVO board, RedirectAttributes rttr) {
		//String	// String title, String writer, String content 이렇게바꿀수있음
		if(board != null) {
		log.info("Register : " + board);
		dao.register(board);
		log.info("Register : " + board);
		rttr.addFlashAttribute("msg", board.getBno() + "번 글이 등록 되었습니까?");
		}
		//return "redirect:/board/list";
		//RedirectView("list");
		return new RedirectView("/board/list");

		
//	    return "<script>" +
//        "window.open('/board/list', '_blank');" + // 새 창에서 리스트 열기
//        "</script>";
	}
	
	
}
