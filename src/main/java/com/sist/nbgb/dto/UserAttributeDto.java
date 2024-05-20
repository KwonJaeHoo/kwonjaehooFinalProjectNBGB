package com.sist.nbgb.dto;

import com.sist.nbgb.entity.User;
import com.sist.nbgb.enums.Provider;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAttributeDto 
{
	private String userNickname;
	
	private Provider userProvider;
	
	public static UserAttributeDto userAttribute(User user)
	{
		return UserAttributeDto
				.builder()
				.userNickname(user.getUserNickname())
				.userProvider(user.getUserProvider())
				.build();
	}
}
