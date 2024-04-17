package com.sist.nbgb.entity;

import java.time.LocalDate;

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
@Table(name = "NBGB_ADMIN")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class Admin 
{
	@Id
	@Column(length = 20)
	@Comment("관리자 아이디")
	private String adminId;
	
	@Column(length = 150)
	@Comment("관리자 비밀번호")
	private String adminPassword;
	
	@Column(length = 50)
	@Comment("관리자 이름")
	private String adminName;
	
	@Column
	@Comment("관리자 상태")
	@Enumerated(EnumType.STRING)
	private Status adminStatus;
	
	@Column(length = 20)
	@Comment("권한")
	@Enumerated(EnumType.STRING)
	private Role Authority;
	
	@CreatedDate
	@Comment("관리자 등록일")
	private LocalDate adminRegdate;
}