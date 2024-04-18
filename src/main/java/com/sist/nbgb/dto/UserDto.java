package com.sist.nbgb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sist.nbgb.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto 
{
	private String userId;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String userPassword;

	private String userName;
	
	private String userNickname;
	
	private String userEmail;
	
	private String userPhone;
	
	private String userBirth;
	
	private char userGender;
	
	public static UserDto from (User user)
	{
		if(user == null)
		{
			return null;
		}
		return UserDto
				.builder()
				.userId(user.getUserId())
				.userName(user.getUserName())
				.userNickname(user.getUserNickname())
				.userEmail(user.getUserEmail())
				.userPhone(user.getUserPhone())
				.userBirth(user.getUserBirth())
				.userGender(user.getUserGender())
				.build();
	}
}