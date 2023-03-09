package com.springboot.ahuboard.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity @Table(name ="board")
public class Board {
	
	@Id @GeneratedValue(strategy =GenerationType.AUTO)
	private Long no;
	
	@Column(length = 60)
	private String writer;
	
	@Column(length = 300)
	private String title;
	
	@Column @Lob
	private String content;
	
	@Column
	private String password;
	
	@Column
	private int readcount;
	
	@CreationTimestamp
	private Date writeTime;
	
	@UpdateTimestamp
	private Date editTime;

	//계층형 게시판을 위한 상태값
	@Column
	private Long grp;

	@Column
	private long seq, dep; // 여기서 wrapper? 형 클래스를 안써서 초기값을 null이 아닌 0으로 받은듯 하다
	
	//댓글 개수 확인용 컬럼
	//-조인을 해도 셀 수 있지만 성능상의 이점을 가지기 위해 별도의 컬럼을 설정
	@Column
	private long replyCount;
	
	
	

}
