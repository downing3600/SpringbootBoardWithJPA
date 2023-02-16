package com.springboot.ahuboard.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.ahuboard.entity.Board;
import com.springboot.ahuboard.repository.BoardRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BoardController {
	
	
	@Autowired
	private BoardRepository boardRepository;
	
	@GetMapping("/")
	public String list(Model model) {
		model.addAttribute("list", boardRepository.findAll());
//		model.addAttribute("list", boardRepository.findAllByOrderNoDesc());
		
//		for(Board board : boardRepository.findAll()) {
//			log.info(String.valueOf(board.getNo()));
//			log.info(String.valueOf(board.getWriter()));
//			log.info(String.valueOf(board.getTitle()));
//		}
		
		
		return "list";
	}
	
	@GetMapping("/write")
	public String write() {
		return "write";
	}
	
	@PostMapping("/write")
	public String wrtie(@ModelAttribute Board board) {
		Board result = boardRepository.save(board);
		String resultStr = String.valueOf(result);
		
		log.info("리설트 스트링 값 확인" + resultStr);
//		
//		if(board != null) {
//			log.info(String.valueOf(board.getNo()));
//			log.info(String.valueOf(board.getTitle()));
//			log.info(String.valueOf(board.getContent()));
//			log.info(String.valueOf(board.getWriter()));
//		}
		
//		return "redirect:/list";
		return "redirect:/detail?no="+result.getNo();
		
	}
	
	@GetMapping("/detail")
	public String detail(@RequestParam long no, Model model) {
		Board board = boardRepository.findById(no).orElseThrow();
		
		//조회수 증가
		board.setReadcount(board.getReadcount()+1);
		Board result = boardRepository.save(board);
		
		model.addAttribute("board", board);
		
		return "detail";
	}
	
	@GetMapping("/edit")
	public String edit(@RequestParam long no, Model model) {
		Board board = boardRepository.findById(no).orElseThrow();
		model.addAttribute("board", board);
		
		return "edit";
	}
	
//	@PatchMapping("/edit")
	@PostMapping("/edit")
	public String edit(@ModelAttribute Board board, RedirectAttributes attr, Model model) {
		Board origin = boardRepository.findById(board.getNo()).orElseThrow();
		origin.setTitle(board.getTitle());
		origin.setWriter(board.getWriter());
		origin.setContent(board.getContent());
		
		Board result = boardRepository.save(origin);
//		model.addAttribute("no",result.getNo());
		attr.addAttribute("no", result.getNo());
		attr.addAttribute("title", result.getTitle());
		attr.addAttribute("writer", result.getWriter());
//		attr.addFlashAttribute("no", result.getNo());
		
//		return "redirect:detail?no="+result.getNo();
		return "redirect:detail";
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam long no, RedirectAttributes attr) {
		boardRepository.deleteById(no);
		return "redirect:/";
	}
	
	

}
