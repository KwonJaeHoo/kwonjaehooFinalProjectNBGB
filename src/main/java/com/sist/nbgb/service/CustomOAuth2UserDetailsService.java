package com.sist.nbgb.service;

import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sist.nbgb.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserDetailsService implements UserDetailsService 
{
	private final UserRepository userRepository;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException 
	{
		return userRepository.findByUserId(userId)
				.map(this::createUserDetails)
				.orElse(null);
		}
	
	//DB 에 User 값이 존재한다면 UserDetails 객체로 만들어서 리턴
    private UserDetails createUserDetails(com.sist.nbgb.entity.User user)
    {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getAuthority().toString());
        return new User(String.valueOf(user.getUserId()), "", Collections.singleton(grantedAuthority));
    }
}
