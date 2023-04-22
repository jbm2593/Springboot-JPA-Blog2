package com.cos.blog2.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import com.cos.blog2.dto.ReplySaveRequestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor; 

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//클래스를 테이블화 시킨다.
//ORM -> Java(다른언어) Object -> 테이블로 매핑해주는 기술
@Entity // User 클래스가 자동으로 MySQL에 테이블이 생성이 된다.
public class Reply {

	@Id  //Primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) //프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
	private int id; // 시퀀스, auto_increment
	
	@Lob //대용량 데이터
	private String content; 
	
	@ManyToOne
	@JoinColumn(name="boardId")
	private Board board;
	
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;
	
	@CreationTimestamp
	private Timestamp createDate;
}
