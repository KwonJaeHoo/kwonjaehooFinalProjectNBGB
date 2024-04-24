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
public class UserIdCheckDto 
{
	private String userId;
	
	public static UserIdCheckDto of(User user)
	{
		return UserIdCheckDto.builder()
				.userId(user.getUserId())
				.build();
	}
}