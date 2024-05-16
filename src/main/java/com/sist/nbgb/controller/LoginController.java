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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sist.nbgb.dto.AdminDto;
import com.sist.nbgb.dto.LoginDto;
import com.sist.nbgb.service.AdminService;
import com.sist.nbgb.service.CustomAdminDetailsService;
import com.sist.nbgb.service.CustomInstructorsDetailsService;
import com.sist.nbgb.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginController 
{
	private final CustomUserDetailsService customUserDetailsService;
	private final CustomInstructorsDetailsService customInstructorsDetailsService;
	private final CustomAdminDetailsService customAdminDetailsService;
	private final PasswordEncoder passwordEncoder;
	
	private final AdminService adminService;
	
	@GetMapping("/login")
	public String login(HttpSession httpSession)
	{		
		return "login/login";
	}
	
	@ResponseBody
	@PostMapping("/login/user")
	public ResponseEntity<Object> loginUser(@RequestBody @Valid LoginDto loginDto, HttpServletRequest httpServletRequest)
	{
		UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginDto.getId());
		
		if(userDetails == null)
		{
            return ResponseEntity.ok(401);
		}
		else
		{
			if(passwordEncoder.matches(loginDto.getPassword(), userDetails.getPassword())) 
			{
				if(userDetails.getAuthorities().toString().equals("[ROLE_USER]"))
				{
					Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());		
					SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
					securityContext.setAuthentication(authentication);
					HttpSession session = httpServletRequest.getSession(true);
					session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);
					
					//SPRING_SECURITY_CONTEXT
					return ResponseEntity.ok(200);
				}
				else if(userDetails.getAuthorities().toString().equals("[ROLE_STOP]"))
				{
					return ResponseEntity.ok(403);
				}	
	        }
			
			return ResponseEntity.ok(401);
		}
	}
	
	@ResponseBody
	@PostMapping("/login/instructor")
	public ResponseEntity<Object> loginInstructors(@RequestBody @Valid LoginDto loginDto, HttpServletRequest httpServletRequest)
	{
		UserDetails userDetails = customInstructorsDetailsService.loadUserByUsername(loginDto.getId());

		if(userDetails == null)
		{
            return  ResponseEntity.ok(401);
		}
		else
		{
			if(passwordEncoder.matches(loginDto.getPassword(), userDetails.getPassword())) 
			{
				Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());		
				SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
				securityContext.setAuthentication(authentication);
				HttpSession session = httpServletRequest.getSession(true);
				session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);
				
				return ResponseEntity.ok(200);
	        }
			else
			{
				return  ResponseEntity.ok(401);
			}
		}	
	}
	
	
	@GetMapping("/login/admin")
	public String adminLoginPage(HttpSession httpSession)
	{
		return "admin/adminLogin";
	}
	
	
	//admin
	@ResponseBody
	@PostMapping("/login/admin")
	public ResponseEntity<Object> loginAdmin(@RequestBody @Valid LoginDto loginDto, HttpServletRequest httpServletRequest)
	{
		UserDetails userDetails = customAdminDetailsService.loadUserByUsername(loginDto.getId());

		if(userDetails == null)
		{
            return ResponseEntity.ok(401);	
		}
		else
		{
			if(passwordEncoder.matches(loginDto.getPassword(), userDetails.getPassword())) 
			{
				Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());		
				SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
				securityContext.setAuthentication(authentication);
				HttpSession session = httpServletRequest.getSession(true);
				session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);

				return ResponseEntity.ok(200);
	        }
			else
			{
				return ResponseEntity.ok(401);
			}
		}	
	}
	
//	@ResponseBody
//	@PostMapping("/admin/signup")
//	public ResponseEntity<AdminDto> signupAdmin(@RequestBody AdminDto adminDto)
//	{
//		return ResponseEntity.ok(adminService.adminSignup(adminDto));
//	}
	
}