package com.sist.nbgb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sist.nbgb.dto.LoginDto;
import com.sist.nbgb.service.CustomInstructorsDetailsService;
import com.sist.nbgb.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginController 
{
	private final CustomUserDetailsService customUserDetailsService;
	private final CustomInstructorsDetailsService customInstructorsDetailsService;
	
	@GetMapping("/login")
	public String login()
	{
		return "login/login";
	}
	
	@ResponseBody
	@PostMapping("/login/user")
	public ResponseEntity<String> loginUser(@RequestBody @Valid LoginDto loginDto, HttpServletRequest httpServletRequest)
	{
		UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginDto.getId());
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
		
		SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
		securityContext.setAuthentication(authentication);
		HttpSession session = httpServletRequest.getSession(true);
		session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);

		return ResponseEntity.ok("200");
	}
	
	@ResponseBody
	@PostMapping("/login/instructor")
	public ResponseEntity<String> loginInstructors(@RequestBody @Valid LoginDto loginDto, HttpServletRequest httpServletRequest)
	{
		UserDetails userDetails = customInstructorsDetailsService.loadUserByUsername(loginDto.getId());
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
		
		SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
		securityContext.setAuthentication(authentication);
		HttpSession session = httpServletRequest.getSession(true);
		session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);
		
		return ResponseEntity.ok("");
	}
}