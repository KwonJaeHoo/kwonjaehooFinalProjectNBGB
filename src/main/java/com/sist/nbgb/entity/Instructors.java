package com.sist.nbgb.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;

import com.sist.nbgb.enums.Role;
import com.sist.nbgb.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "NBGB_INSTRUCTORS")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class Instructors 
{
	@Id
	@Column(length = 20)
	@Comment("강사 아이디")
	private String instructorId;

	@Column(length = 150)
	@Comment("강사 비밀번호")
	private String instructorPassword;
	
	@Column(length = 50)
	@Comment("강사 이름")
	private String instructorName;
	
	@Column(length = 50)
	@Comment("강사 예명(상호명)")
	private String instructorNickname;
	
	@Column(length = 50)
	@Comment("강사 이메일")
	private String instructorEmail;
	
	@Column(length = 15)
	@Comment("강사 연락처")
	private String instructorPhone;
	
	@Column(length = 20)
	@Comment("강사 은행")
	private String instructorBank;
	
	@Column(length = 16)
	@Comment("강사 계좌번호")
	private String instructorAccount;
	
	@Column(length = 20)
	@Comment("권한")
	@Enumerated(EnumType.STRING)
	private Role Authority;
	
	@Column
	@Comment("강사 상태")
	@Enumerated(EnumType.STRING)
	private Status instructorStatus;
	
	@CreatedDate
	@Comment("강사 가입일")
	private LocalDateTime instructorRegdate;
	
	@Column(length = 30)
	@Comment("강사 주 카테고리")
	private String instructorCategory;
		
}