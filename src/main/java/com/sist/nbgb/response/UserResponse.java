package com.sist.nbgb.response;

import java.time.LocalDateTime;

import com.sist.nbgb.entity.User;
import com.sist.nbgb.enums.Provider;
import com.sist.nbgb.enums.Role;
import com.sist.nbgb.enums.Status;

import lombok.Getter;

@Getter
public class UserResponse
{
	private String userId;
	
	private String userPassword;

	private String userName;
	
	private String userNickname;
	
	private String userEmail;
	
	private String userPhone;
	
	private String userBirth;
	
	private char userGender;
	
	private Long userPoint;

	private Provider userProvider;
	
	private Role Authority;
	
	private Status userStatus;
	
	private LocalDateTime userRegdate;
	
	public UserResponse(User user)
	{
		this.userId = user.getUserId();
		
		this.userPassword = user.getUserPassword();

		this.userName = user.getUserName();
		
		this.userNickname = user.getUserName();
		
		this.userEmail = user.getUserEmail();
		
		this.userPhone = user.getUserPhone();
		
		this.userBirth = user.getUserBirth();
		
		this.userGender  = user.getUserGender();
		
		this.userPoint = user.getUserPoint();

		this.userProvider = user.getUserProvider();
		
		this.Authority = user.getAuthority();
		
		this.userStatus = user.getUserStatus();
		
		this.userRegdate = user.getUserRegdate();
	}
}
