package com.sist.nbgb.oauth2;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.sist.nbgb.entity.User;
import com.sist.nbgb.enums.Provider;
import com.sist.nbgb.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler 
{
	private final UserRepository userRepository;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException 
    {
        //로그인 성공한 사용자
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        
        final String userId = URLEncoder.encode(oAuth2User.getAttribute("id"), StandardCharsets.UTF_8.toString());
        final String userEmail = URLEncoder.encode(oAuth2User.getAttribute("email"), StandardCharsets.UTF_8.toString());
        final String userName = URLEncoder.encode(oAuth2User.getAttribute("name"), StandardCharsets.UTF_8.toString());

        User user = userRepository.findFirstByUserId(userId);
        
    	HttpSession session = request.getSession(false);
    	session.invalidate();
        
        if(user.getUserNickname() == null)
        {
            // 사용자를 추가 정보 입력 페이지로 리다이렉트
          String targetUrl = UriComponentsBuilder.fromUriString("/signup/oauth2")
          		.queryParam("userId", userId)
          		.queryParam("userName", userName)
          		.queryParam("userEmail", userEmail)
          		.build().toUriString();
          getRedirectStrategy().sendRedirect(request, response, targetUrl);
          
        }
        else
        {
        	String targetUrl = UriComponentsBuilder.fromUriString("/login/oauth2")
        			.queryParam("userId", userId)
              		.build().toUriString();
      
              getRedirectStrategy().sendRedirect(request, response, targetUrl);
        }
    }
}