package com.sist.nbgb.oauth2;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.sist.nbgb.entity.User;
import com.sist.nbgb.enums.Provider;
import com.sist.nbgb.enums.Role;
import com.sist.nbgb.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuth2Service extends DefaultOAuth2UserService
{
	private final UserRepository userRepository;
	
	@Override
	@Transactional
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException 
	{
		OAuth2User oAuth2User = super.loadUser(userRequest);
		//Oauth2Service(kakao, google, naver에서 가져온 정보)

		// OAuth 서비스 이름(ex. kakao, naver, google) (provider)
		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		
        //pk, id
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
     
        //provider 정보 기반 객체 생성
        OAuth2Attribute oAuth2Attribute = OAuth2Attribute.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        final String userId = oAuth2Attribute.getId();
        final String userName = oAuth2Attribute.getName();
		final String userEmail = oAuth2Attribute.getEmail();
        final Provider provider = oAuth2Attribute.getProvider();
        
        
        if(userRepository.existsByUserEmailAndUserProvider(userEmail, provider))
        {
        	Optional<User> user = userRepository.findByUserId(userId);
        	user.ifPresent(value -> value.setUserName(userName));
        }
        else
        {
            User user = User.builder()
            		.userId(userId)
            		.userName(userName)
            		.userEmail(userEmail)
                    .userProvider(provider)
                    .Authority(Role.ROLE_USER)
                    .build();

            userRepository.save(user);
        }
        Map<String, Object> memberAttribute = oAuth2Attribute.convertToMap();
        
        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")), memberAttribute, "id");
	}
}