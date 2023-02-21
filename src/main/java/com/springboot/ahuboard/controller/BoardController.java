package com.springboot.ahuboard.controller;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.springboot.ahuboard.board.vo.BoardPaginationVO;
import com.springboot.ahuboard.board.vo.BoardVO;
import com.springboot.ahuboard.entity.Board;
import com.springboot.ahuboard.repository.BoardRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BoardController {
	
	
	@Autowired
	private BoardRepository boardRepository;
	
	@RequestMapping("/")
	public String list(Model model,
						@ModelAttribute BoardVO boardVO,
						@PageableDefault(page = 0, size=10)
						@SortDefault(sort="no", direction = Sort.Direction.ASC)
						Pageable pageable) {
		
		Page<Board> data = boardRepository.findAll(boardVO.specification(), pageable);
		
		model.addAttribute("data", data);
		model.addAttribute("pagination",new BoardPaginationVO(data));
		
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
	
	//비밀번호 로직이 추가되었으므로 단순하게 번호를 받는 것이 아닌 FlashMap을 수신하는 코드로 변경
	@GetMapping("/edit")
	public String edit(HttpServletRequest request, Model model) {
		//FlashMap 수신 코드
		Map<String, ?> map = RequestContextUtils.getInputFlashMap(request);
		if(map == null) throw new RuntimeException("권한 없음");
		Long no = (Long) map.get("no");
		
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
		//비밀번호 변경 추가
		origin.setPassword(board.getPassword());
		
		Board result = boardRepository.save(origin);
//		model.addAttribute("no",result.getNo());
		attr.addAttribute("no", result.getNo());
		attr.addAttribute("title", result.getTitle());
		attr.addAttribute("writer", result.getWriter());
//		attr.addFlashAttribute("no", result.getNo());
		
//		return "redirect:detail?no="+result.getNo();
		return "redirect:detail";
	}
	
	//비밀번호 로직이 추가되었으므로 단순하게 번호를 받는 것이 아닌 FlashMap을 수신하는 코드로 변경
	@GetMapping("/delete")
	public String delete(HttpServletRequest request, RedirectAttributes attr) {
		Map<String, ?> map = RequestContextUtils.getInputFlashMap(request);
		if(map == null) throw new RuntimeException("권한 없음");
		
		Long no = (Long) map.get("no");
		boardRepository.deleteById(no);
		return "redirect:/";
	}
	
	//비밀번호 검사 매핑 추가
	//- /passwrd/모드/번호
	//- 모드는 반드시 edit 또는 delete 중 하나가 오도록 정규식 검사 처리
	@GetMapping("/password/{mode:edit|delete}/{no}")
	public String password(@PathVariable String mode,@PathVariable long no, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("no", no);
		return "password";
	}
	
	@PostMapping("/password/{mode:edit|delete}/{no}")
	public String password(@PathVariable String mode, @PathVariable long no, @RequestParam String password,
							RedirectAttributes attr) {
		//글번호가 없으면 오류 처리
		Board board = boardRepository.findById(no).orElseThrow();
		
		if(board.getPassword().equals(password)) { //비밀번호 일치 - 다음 단계로 이동
			//이동은 하지만 번호를 알 수가 없으며, 이 페이지를 통과했다는 보증이 필요하므로 FlashAttribute를 사용
			//FlashAttribute는 요청 후 소멸하므로 현재 상태에서 유용하게 사용 할 수 있는 값
			//-> 하지만 edit와 delete의 로직을 변경해야함
			attr.addFlashAttribute("no",no);
			return "redirect:/" + mode;
			
			
		}
		else {// 비밀번호 불일치 - 이전 단계로 복귀 (+error 표식)
			return "redirect:/password/"+mode+"/"+no+"?error";
		}
	}
	
	

}
