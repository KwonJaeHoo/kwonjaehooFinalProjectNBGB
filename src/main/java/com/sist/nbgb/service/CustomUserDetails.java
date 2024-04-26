package com.sist.nbgb.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sist.nbgb.entity.User;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails
{
	private final User user;
	
	//권한 반환
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() 
	{
	    List<GrantedAuthority> authorities = new ArrayList<>();
	    authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
	    return authorities;
	}

	//id 반환
	@Override
	public String getUsername() 
	{
		return user.getUserId();
	}
	
	//password 반환
	@Override
	public String getPassword() 
	{
		return user.getUserPassword();
	}
	
	//계정만료여부
	@Override
	public boolean isAccountNonExpired() 
	{
		//true 만료 x
		return true;
	}

	//계정 잠금여부
	@Override
	public boolean isAccountNonLocked() 
	{
		//true 잠금 x
		return true;
	}
	
	//패스워드 만료여부
	@Override
	public boolean isCredentialsNonExpired() 
	{
		//true 만료 x
		return true;
	}

	//계정 사용가능여부
	@Override
	public boolean isEnabled() 
	{
		//사용가능
		return true;
	}
}
