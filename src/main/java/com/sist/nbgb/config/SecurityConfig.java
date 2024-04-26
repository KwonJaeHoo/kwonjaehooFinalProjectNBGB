package com.sist.nbgb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.sist.nbgb.repository.InstructorsRepository;
import com.sist.nbgb.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig 
{
	private final UserRepository userRepository;
	private final InstructorsRepository instructorsRepository;
	private final UserDetailsService userDetailsService;
	
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
    @Order(1)
	public SecurityFilterChain userFilterChain(HttpSecurity httpSecurity) throws Exception
	{
    	return httpSecurity
				
				.formLogin()
				.loginPage("/login") // 사용자 정의 로그인 페이지 (아니면 시큐리티 자체 로그인창 뜸)
				.usernameParameter("userId")         // 로그인 id값 설정 (default username) 
		        .passwordParameter("userPassword")         // 로그인 pw값 설정 (default password)

				.loginProcessingUrl("/login/user") // 폼 태그에 사용되는 URL
				.defaultSuccessUrl("/") // 로그인 성공시 리다이렉트 URL
				
				.and()
				.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // 로그아웃 URL
				.logoutSuccessUrl("/") // 로그아웃 성공시 리다이렉트 URL
				.invalidateHttpSession(true).and().authorizeHttpRequests()
				
				

				.antMatchers("/", "/signup/**", "/login/**", "/onlineClass", "/offlineClass").permitAll()
				.antMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN").antMatchers("/instructor/**").hasAnyAuthority("ROLE_INSTRUCTOR")
				.antMatchers("/user/**").hasAnyAuthority("ROLE_USER")
				.and()
				.csrf().disable() // 사이트 위변조 요청 방지
				.build();
	}
    
    @Bean
    @Order(2)
	public SecurityFilterChain instructorFilterChain(HttpSecurity httpSecurity) throws Exception
	{
    	return httpSecurity
				
				.formLogin()
				.loginPage("/login") // 사용자 정의 로그인 페이지 (아니면 시큐리티 자체 로그인창 뜸)
				.usernameParameter("instructorId")         // 로그인 id값 설정 (default username) 
		        .passwordParameter("instructorPassword")         // 로그인 pw값 설정 (default password)

				.loginProcessingUrl("/login/instructor") // 폼 태그에 사용되는 URL
				.defaultSuccessUrl("/") // 로그인 성공시 리다이렉트 URL
				
				.and()
				.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // 로그아웃 URL
				.logoutSuccessUrl("/") // 로그아웃 성공시 리다이렉트 URL
				.invalidateHttpSession(true).and().authorizeHttpRequests()
				
				

				.antMatchers("/", "/signup/**", "/login/**", "/onlineClass", "/offlineClass").permitAll()
				.antMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN").antMatchers("/instructor/**").hasAnyAuthority("ROLE_INSTRUCTOR")
				.antMatchers("/user/**").hasAnyAuthority("ROLE_USER")
				.and()
				.csrf().disable() // 사이트 위변조 요청 방지
				.build();
	}
    
//	@Bean
//    DaoAuthenticationProvider ownerDaoAuthenticationProvider() {
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setUserDetailsService(new OwnerDetailsService(ownerRepository));
//        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
//        return daoAuthenticationProvider;
//    }
//    @Bean
//    DaoAuthenticationProvider customerDaoAuthenticationProvider() {
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setUserDetailsService(new CustomerDetailsService(customerRepository));
//        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
//        return daoAuthenticationProvider;
//    }
    
//    @Bean
//    @Order(3)
//	public SecurityFilterChain adminChain(HttpSecurity httpSecurity) throws Exception
//	{
//    	return httpSecurity
//				
//				.formLogin()
//				.loginPage("/login") // 사용자 정의 로그인 페이지 (아니면 시큐리티 자체 로그인창 뜸)
//				.usernameParameter("userId")         // 로그인 id값 설정 (default username) 
//		        .passwordParameter("userPassword")         // 로그인 pw값 설정 (default password)
//
//				.loginProcessingUrl("/login") // 폼 태그에 사용되는 URL
//				.defaultSuccessUrl("/") // 로그인 성공시 리다이렉트 URL
//				
//				.and()
//				.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // 로그아웃 URL
//				.logoutSuccessUrl("/") // 로그아웃 성공시 리다이렉트 URL
//				.invalidateHttpSession(true).and().authorizeHttpRequests()
//				
//				
//
//				.antMatchers("/", "/signup/**", "/login/**", "/onlineClass", "/offlineClass").permitAll()
//				.antMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN").antMatchers("/instructor/**").hasAnyAuthority("ROLE_INSTRUCTOR")
//				.antMatchers("/user/**").hasAnyAuthority("ROLE_USER")
//				.and()
//				.csrf().disable() // 사이트 위변조 요청 방지
//				.build();
//	}
}
