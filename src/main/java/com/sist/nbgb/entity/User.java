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

import com.sist.nbgb.enums.Provider;
import com.sist.nbgb.enums.Role;
import com.sist.nbgb.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "NBGB_USER")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class User 
{
	@Id
	@Column(length = 20)
	@Comment("회원 아이디")
	private String userId;
	
	@Column(length = 150)
	@Comment("회원 비밀번호")
	private String userPassword;

	@Column(length = 50)
	@Comment("회원 이름")
	private String userName;
	
	@Column(length = 30)
	@Comment("회원 닉네임")	
	private String userNickname;
	
	@Column(length = 50)
	@Comment("회원 이메일")
	private String userEmail;
	
	@Column(length = 15)
	@Comment("회원 연락처")
	private String userPhone;
	
	@Column(columnDefinition = "CHAR(8)")
	@Comment("회원 생년월일")
	private String userBirth;
	
	@Column
	@Comment("회원 성별")
	private char userGender;
	
	@Column(columnDefinition = "NUMBER(7)")
	@Comment("회원 포인트")
	private Long userPoint;

	@Column(length = 8)
	@Comment("회원 식별자")
	@Enumerated(EnumType.STRING)
	private Provider userProvider;
	
	@Column(length = 20)
	@Comment("권한")
	@Enumerated(EnumType.STRING)
	private Role Authority;
	
	@CreatedDate
	@Comment("회원 가입일")
	private LocalDateTime userRegdate;
}