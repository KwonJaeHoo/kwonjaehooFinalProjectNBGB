package com.sist.nbgb.dto;

import javax.validation.constraints.NotNull;

import com.sist.nbgb.entity.Instructors;
import com.sist.nbgb.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDto 
{
	@NotNull
	private String id;
	
	@NotNull
	private String password;
}
