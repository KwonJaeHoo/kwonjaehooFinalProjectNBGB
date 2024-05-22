package com.sist.nbgb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.sist.nbgb.oauth2.OAuth2AuthenticationSuccessHandler;
import com.sist.nbgb.oauth2.OAuth2Service;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig 
{
	private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
	private final OAuth2Service oAuth2Service;

	@Bean
    public MappingJackson2JsonView jsonView() 
    {
        return new MappingJackson2JsonView();
    }
	
    @Bean
    public PasswordEncoder passwordEncoder() 
    {
        return new BCryptPasswordEncoder();
    }

    public WebSecurityCustomizer webSecurityCustomizer()
    {
    	return (web) -> web.ignoring().antMatchers("/css/**", "/images/**", "/js/**", "/favicon.ico");
    }
    
    @Bean
	public SecurityFilterChain FilterChain(HttpSecurity httpSecurity) throws Exception
	{
    	httpSecurity
				.exceptionHandling()
					.accessDeniedPage("/403")
					
				.and()
				.csrf().disable() // 사이트 위변조 요청 방지
				.headers().frameOptions().sameOrigin()
				.and()
				.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
				.and()
				.formLogin()
					.loginPage("/login")// 사용자 정의 로그인 페이지 (아니면 시큐리티 자체 로그인창 뜸)
				.and()
				.logout()
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) 	// 로그아웃 URL
					.logoutSuccessUrl("/") 											// 로그아웃 성공시 리다이렉트 URL
					.invalidateHttpSession(true)
					.deleteCookies("JSESSIONID")
				.and()
				.authorizeHttpRequests()
					.antMatchers("/", "/signup/**", "/login/**", "/onlineClass", "/offlineClass").permitAll()
					.antMatchers("/user/**", "/reference/referenceWrite").hasAnyAuthority("ROLE_USER")
					.antMatchers("/instructor/**").hasAnyAuthority("ROLE_INSTRUCTOR")					
					.antMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN")
				.and()
				.oauth2Login()
				.loginPage("/login")
                .successHandler(oAuth2AuthenticationSuccessHandler) // 성공 핸들러 설정
                .userInfoEndpoint()
                .userService(oAuth2Service);
				
			return httpSecurity.build();
	}
}