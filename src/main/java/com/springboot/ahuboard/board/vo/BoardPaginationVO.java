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
		//this.end = this.begin + size -1;
		this.end = Math.min(this.begin + size -1, this.total);
		
	}
	
	//처음 (first), 이전(previous), 다음(next), 마지막(last)dp eogks wjdqh
	public boolean hasFirstBlock() {
		return current > 1;
		
	}
	
	public boolean hasPreviousBlock() {
		return begin > 1;
	}
	
	public boolean hasNextBlock() {
		return end < total;
	}
	
	public boolean hasLastBlock() {
		return current < total;
	}
	
	
	// JPA는 페이지 0부터 시작  하므로 아래 메서드들을 모두 -1 해준다
	
	public int getFirst() {
		return 1 - 1;
	}
	
	public int getLast() {
		return total - 1;
	}
	
	public int getPrevious() {
		return begin -1 -1;
	}
	
	public int getNext() {
		return end + 1 - 1;
	}
	

}
