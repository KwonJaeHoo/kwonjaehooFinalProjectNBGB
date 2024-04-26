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

import com.sist.nbgb.entity.Instructors;
import com.sist.nbgb.repository.InstructorsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomInstructorsDetailsService implements UserDetailsService 
{
	private final InstructorsRepository instructorsRepository;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String instructorId) throws UsernameNotFoundException 
	{
		return instructorsRepository.findByInstructorId(instructorId)
				.map(this::createInstructorsDetails)
				.orElseThrow(() -> new UsernameNotFoundException(instructorId + "-> 데이터베이스에서 찾을 수 없습니다."));
	}
	
	//DB 에 User 값이 존재한다면 UserDetails 객체로 만들어서 리턴
    private UserDetails createInstructorsDetails(Instructors instructors) 
    {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(instructors.getAuthority().toString());

        return new User(String.valueOf(instructors.getInstructorId()), instructors.getInstructorPassword(), Collections.singleton(grantedAuthority));
    }
}
