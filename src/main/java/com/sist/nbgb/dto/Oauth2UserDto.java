package com.sist.nbgb.dto;

import com.sist.nbgb.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Oauth2UserDto 
{
	private String userId;
	
	private String userNickname;
	
	private String userPhone;
	
	private String userBirth;
	
	private char userGender;
	
	public Oauth2UserDto oauth2UserDto(User user)
	{
		return Oauth2UserDto
				.builder()
				.userNickname(user.getUserNickname())
				.userPhone(user.getUserPhone())
				.userBirth(user.getUserBirth())
				.userGender(user.getUserGender())
				.build();
	}
}
