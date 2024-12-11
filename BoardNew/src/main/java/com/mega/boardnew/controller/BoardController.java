package com.mega.boardnew.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.mega.boardnew.bean.AttachFileVO;
import com.mega.boardnew.bean.BoardDAO;
import com.mega.boardnew.bean.BoardVO;
import com.mega.boardnew.utill.Color;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/board/*")
@Slf4j
public class BoardController {

	@Autowired
	private BoardDAO dao;

	@GetMapping("list")
	public void list(String type, String keyword, Model model) {
		log.info("------------------------------");
		log.info("[BoardController] list called : " + keyword);
		log.info("------------------------------");
		if (keyword == null) {
			dao.getList().forEach(board -> log.info(board.toString()));
			model.addAttribute("list", dao.getList());
		}
		else {
			model.addAttribute("list", dao.getListWithKey(type, keyword));
		}
	}

	@GetMapping("write")
	public void write() {

	}

	@PostMapping("write")
	@ResponseBody
	// 글을쓰고, 리스트 화면으로 이동하십시오.
	public RedirectView writer(BoardVO board, RedirectAttributes rttr) {
		// String // String title, String writer, String content 이렇게바꿀수있음
		if (board != null) {
			log.info("Register : " + board);
			dao.register(board);
			log.info("Register : " + board);
			rttr.addFlashAttribute("msg", board.getBno() + "번 글이 등록 되었습니까?");
		}
		// return "redirect:/board/list";
		// RedirectView("list");
		return new RedirectView("/board/list");

//	    return "<script>" +
//        "window.open('/board/list', '_blank');" + // 새 창에서 리스트 열기
//        "</script>";
	}

	// localhost:10000/board/get?bno=N 을 호출해쓸때 내용을 보여주는 페이지
	// 리스트로 돌아가기 링크 클릭시 리스트로 이동
	// 1. 해당 주소 클릭시 실행할 메서드 생성
	// 2. 메서드 호출시 정상적이 호출이 되는지 확인위해 메서드 호출 메세지 출력 기능 추가
	// 3. 매퍼로부터 bno에 해당하는 BoardVO 넣기
	// 4.model에 얻어온 BoardVO 넣기
	// 5. 페이지로 이동하기

	@GetMapping("get")
	public void get(Long bno, Model model) {
		log.info("-----------------");
		log.info(Color.BOLD + Color.GREEN + "get called" + Color.END);
		log.info("-----------------");
		BoardVO board = dao.get(bno);
		// model.addAttribute("board", board);
		if (board != null) {
			model.addAttribute("board", dao.get(bno));
		}
	}

	// localhost:10000/board/revome?bno=N
	// 해당 글의 삭제 처리 수행 후 리스트로 돌아감.

	@PostMapping("remove")
	public RedirectView remove(@RequestParam("bno") Long bno, RedirectAttributes rttr) {

		log.info("remove : " + bno);
		boolean result = dao.remove(bno);
		log.info("remove : " + bno);

		rttr.addFlashAttribute("msg", bno + "번 글이 삭제 되었습니다.");
		return new RedirectView("/board/list");

	}
//		@GetMapping("remove")
//		public RedirectView remove2(Long bno, RedirectAttributes rttr) {
//			
//			rttr.addFlashAttribute("msg", "성공? 실");
//			return new RedirectView("list");
//		}

	@GetMapping("modify")
	public void modify(Long bno, Model model) {
		model.addAttribute("board", dao.get(bno));
	}

//		@PostMapping("modify")
//		public RedirectView modify(@RequestParam("bno") Long bno , @RequestParam("title") String title,
//				@RequestParam("content") String content, @RequestParam("writer") String writer,
//				RedirectAttributes rttr) {
//			
//			boolean result = dao.modify(bno, title, content, writer);
//		    rttr.addFlashAttribute("msg", bno + "번 글 수정에 성공했습니다.");
//			
//			return new RedirectView("/board/list");
//		}
		@PostMapping("modify")
		public RedirectView modify(BoardVO board, RedirectAttributes rttr) {
		   
		    boolean result = dao.modify(board);

		    if (result) {
		        rttr.addFlashAttribute("msg", "업데이트 성공");
		    } else {
		        rttr.addFlashAttribute("msg", "업데이트 실패");
		    }

		    return new RedirectView("/board/list");
		}
		
		// 게시글의 첨부파일
		@GetMapping(value="/getAttachList", produces=MediaType.APPLICATION_JSON_VALUE)
		@ResponseBody
		public List<AttachFileVO> getAttachList(Long bno){
			log.info("getAttachList----------------------");
			return dao.getAttachList(bno);
		}
		
}
