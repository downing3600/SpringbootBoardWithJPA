package com.springboot.ahuboard.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
@Table(name="member") @Builder @Entity
public class Member {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private long no;
	
	@Column
	private String name;
	
	@Column
	private String phone;
	
	@Column
	private String sex;
	
	@CreationTimestamp
	private Timestamp joinTime;
	
	@UpdateTimestamp
	private Timestamp updateTime;
	
	

}
