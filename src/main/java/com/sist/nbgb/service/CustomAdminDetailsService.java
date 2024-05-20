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

import com.sist.nbgb.entity.Admin;
import com.sist.nbgb.repository.AdminRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomAdminDetailsService implements UserDetailsService
{
	private final AdminRepository adminRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		return adminRepository.findByAdminId(username)
				.map(this::createAdminDetails)
				.orElse(null);
	}
	
	//DB 에 User 값이 존재한다면 UserDetails 객체로 만들어서 리턴
    private UserDetails createAdminDetails(Admin admin) 
    {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(admin.getAuthority().toString());
        return new User(String.valueOf(admin.getAdminId()), admin.getAdminPassword(), Collections.singleton(grantedAuthority));
    }
}
