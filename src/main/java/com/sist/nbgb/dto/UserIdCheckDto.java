package com.sist.nbgb.dto;

import com.sist.nbgb.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserIdCheckDto 
{
	private String userId;
	
	public UserIdCheckDto(User user)
	{
		this.userId = user.getUserId();
	}
}
