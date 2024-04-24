package com.sist.nbgb.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SecurityUtil 
{
	private SecurityUtil()
	{
		
	}
	
	public static String getCurrentUserId()
	{
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if(authentication == null || authentication.getName() == null)
		{
			throw new RuntimeException("인증정보가 없습니다.");
		}
		
		return authentication.getName();
	}
	
}
