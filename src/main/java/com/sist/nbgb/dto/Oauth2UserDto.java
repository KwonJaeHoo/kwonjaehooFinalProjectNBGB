package com.sist.nbgb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OAuth2UserDto 
{
	private String userId;
	
	private String userNickname;
	
	private String userPhone;
	
	private String userBirth;
	
	private char userGender;
	
}