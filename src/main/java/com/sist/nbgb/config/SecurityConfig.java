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
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.sist.nbgb.jwt.JwtAccessDeniedHandler;
import com.sist.nbgb.jwt.JwtAuthenticationEntryPoint;
//import com.sist.nbgb.jwt.JwtTokenProvider;

//import com.sist.nbgb.jwt.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig 
{
//	private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
	
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
    	return  web -> web.ignoring().antMatchers("/css/**", "/images/**", "/js/**", "/favicon.ico");
    }
    
    @Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception
	{
    	httpSecurity
	    	.httpBasic().disable()
		    .cors()
		    .and()
		    // 사이트 위변조 요청 방지
		    .csrf().disable()
		    //세션 사용x
	    	.headers().frameOptions().sameOrigin().and()
		    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    	
    	// 인가(접근권한) 설정
    	httpSecurity
			.authorizeHttpRequests()
				.antMatchers("/token/**").permitAll()
				.antMatchers("/admin/**").hasRole("ROLE_ADMIN")
				.antMatchers("/user/**").hasRole("ROLE_USER")
				.antMatchers("/instructor/**").hasRole("ROLE_INSTRUCTOR")
				.antMatchers("/**").permitAll()

				;
//				.and()
//				.apply(new JwtSecurityConfig(jwtTokenProvider));
    	
	    return httpSecurity.build();
	}
}
