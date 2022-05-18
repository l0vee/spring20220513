package com.choong.spr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.choong.spr.domain.BoardDto;
import com.choong.spr.domain.PageInfoDto;
import com.choong.spr.domain.ReplyDto;
import com.choong.spr.service.BoardService;
import com.choong.spr.service.ReplyService;

@Controller
@RequestMapping("myproject")
public class BoardController {
	
	@Autowired
	private BoardService service;
	
	@Autowired
	private ReplyService replyService;

	// /myproject/board/list
//	@GetMapping("board/list")
//	public void listBoard(Model model) {
//		List<BoardDto> list = service.listBoard();
//		
////		pageInfo info = 
//		
//		model.addAttribute("boardList", list);
////		model.addAttribute("pagenum", info);
//		
//	}
	
	@GetMapping("board/list")
	public void listBoard(@RequestParam(name = "page", defaultValue = "1")int page, Model model) {
		int rowPerPage = 5;
		
		List<BoardDto> list = service.listBoard();
	
		List<BoardDto> list1 = service.listBoardPage(page,rowPerPage);
		int totalRecords = service.countBoards();
		
		int end = (totalRecords - 1) / rowPerPage + 1;
		
		PageInfoDto pageInfo = new PageInfoDto();
		
		pageInfo.setCurrent(page);
		pageInfo.setEnd(end);
		
		model.addAttribute("boardList", list);
		model.addAttribute("boards", list1);
		model.addAttribute("pageInfo", pageInfo);
	
	}
	
	
	
	
	@GetMapping("board/{id}") // 책 366쪽
	public String getBoard(@PathVariable("id") int id, Model model) {
		System.out.println(id);
		
		// 서비스일 시켜서 id에 해당하는 게시물 select
		BoardDto dto = service.getBoard(id);
		
		List<ReplyDto> replyList = replyService.listReplyByBoardId(id);
		
		// 모델에 넣고
		model.addAttribute("board", dto);
		model.addAttribute("replyList", replyList);
		
		// /WEB-INF/views/myproject/board/get.jsp로 포워드
		return "/myproject/board/get";
	}
	
	@PostMapping("board/modify")
	public String modifyBoard(BoardDto board, RedirectAttributes rttr) {
		boolean success = service.updateBoard(board);
		
		if (success) {
			rttr.addFlashAttribute("message","게시물이 성공적으로 수정되었습니다!");
			
		} else {
			rttr.addFlashAttribute("message","게시물 수정에 실패하였습니다.");
		}
		
		return "redirect:/myproject/board/" + board.getId();
	}
	
	@PostMapping("board/remove")
	public String removeBoard(int id) {
		boolean success = service.removeBoardById(id);
		
		if (success) {
			
		} else {
			
		}
		
		return "redirect:/myproject/board/list";
	}
	
	@GetMapping("board/write")
	public void writeBoard() {
		
	}
	
	@PostMapping("board/write")
	public String writeBoardProcess(BoardDto board, RedirectAttributes rttr ) {
		boolean success = service.addBoard(board);
		
		if (success) {
			rttr.addFlashAttribute("message","게시물이 성공적으로 등록되었습니다!");
			
		} else {
			rttr.addFlashAttribute("message","게시물 등록에 실패하였습니다.");
		}
		
		return "redirect:/myproject/board/" + board.getId();
	}
	
//	@PostMapping("board/list")
//	public String listBoard(@RequestParam(name = "page", defaultValue = "1")int page, Model model) {
//		int rowPerPage = 5;
//		
//		List<BoardDto> list = service.listBoardPage(page, rowPerPage);
//		
//		int totalRecords = service.countBoards();
//		int end = (totalRecords - 1) / rowPerPage + 1;
//		
//		PageInfoDto pageInfo = new PageInfoDto();
//		
//		pageInfo.setCurrent(page);
//		pageInfo.setEnd(end);
//		
//		model.addAttribute("boards", list);
//		model.addAttribute("pageInfo", pageInfo);
//	
//		return "/myproject/board/list";
	
	
}











