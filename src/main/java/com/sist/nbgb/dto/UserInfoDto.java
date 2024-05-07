package com.sist.nbgb.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sist.nbgb.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class UserInfoDto 
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
	
	private Long userPoint;
	
	private LocalDateTime userRegdate; 

	public static UserInfoDto infoUser(List<User> user)
	{
		return UserInfoDto.builder()
				.userId(user.get(0).getUserId())
				.userPassword(user.get(0).getUserPassword())
				.userName(user.get(0).getUserName())
				.userNickname(user.get(0).getUserNickname())
				.userEmail(user.get(0).getUserEmail())
				.userPhone(user.get(0).getUserPhone())
				.userBirth(user.get(0).getUserBirth())
				.userGender(user.get(0).getUserGender())
				.userPoint(user.get(0).getUserPoint())
				.userRegdate(user.get(0).getUserRegdate())
				.build();
	}
}
