package com.sist.nbgb.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto 
{
	@NotNull
	@Size(min = 4, max = 20)
	private String id;

	@NotNull
	@Size(min = 8, max = 32)
	private String password;
}