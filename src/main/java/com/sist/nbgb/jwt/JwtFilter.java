//package com.sist.nbgb.jwt;
//
//import lombok.RequiredArgsConstructor;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//
//@RequiredArgsConstructor
//public class JwtFilter extends OncePerRequestFilter
//{
//	private final JwtTokenProvider jwtTokenProvider;
//	
//	@Override
//	protected void doFilterInternal (HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws IOException, ServletException 
//	{
//        //1. Request Header 에서 JWT Token 추출
//        String token = jwtTokenProvider.resolveToken(httpServletRequest);
//
//        //2. validateToken 메서드로 토큰 유효성 검사
//        if (token != null && jwtTokenProvider.validateToken(token)) 
//        {
//            Authentication authentication = jwtTokenProvider.getAuthentication(token);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//        filterChain.doFilter(httpServletRequest, httpServletResponse);
//	}
//}