package com.springboot.ahuboard.board.vo;

import org.springframework.data.domain.Page;

import com.springboot.ahuboard.entity.Board;

import lombok.Getter;
import lombok.ToString;

//하단 pagination울 위한 vo
@Getter @ToString
public class BoardPaginationVO {
	
	private int current; //현재 페이지 번호
	private int begin;//시작 페이지 번호
	private int end;//종료 페이지 번호
	private int total;//전체 페이지 개수
	private int size =10;//보여줄 페이지 번호 개수
	
	
	public BoardPaginationVO(Page<Board> page) {
		this.total = page.getTotalPages();
		this.current = page.getNumber() + 1; //jpa는 페이지가 0부터 시작
		this.begin = (current-1) / size * size + 1; 
		this.end = this.begin + size -1;
		
	}
	

}
