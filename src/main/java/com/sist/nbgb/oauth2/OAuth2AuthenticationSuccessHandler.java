package com.sist.nbgb.oauth2;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

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
        
       

        final String email = oAuth2User.getName();
        final Provider provider = oAuth2User.getAttribute("provider");
        final String name = oAuth2User.getAttribute("name");
        final String authorities = oAuth2User.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

//        System.out.println("OAuth2AuthenticationSuccessHandler 1 " + oAuth2User);
//        System.out.println("OAuth2AuthenticationSuccessHandler 2 " + email);
//        System.out.println("OAuth2AuthenticationSuccessHandler 3 " + provider);
//        System.out.println("OAuth2AuthenticationSuccessHandler 4 " + name);
//        System.out.println("OAuth2AuthenticationSuccessHandler 5 " + authorities);
        

    }
}