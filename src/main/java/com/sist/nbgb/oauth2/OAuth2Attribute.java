package com.sist.nbgb.oauth2;

import java.util.HashMap;
import java.util.Map;

import com.sist.nbgb.enums.Provider;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Builder
@Getter
public class OAuth2Attribute
{
    private Map<String, Object> attributes;
    private String attributeKey;
    private String id;
    private String email;
    private String name;
    private Provider provider;

    static OAuth2Attribute of(String provider, String attributeKey, Map<String, Object> attributes) 
    {
        switch (provider) 
        {
            case "google":
                return ofGoogle(attributeKey, attributes);
            case "kakao":
                return ofKakao("id", attributes);
            case "naver":
                return ofNaver("id", attributes);
            default:
                throw new RuntimeException();
        }
    }

    private static OAuth2Attribute ofGoogle(String attributeKey, Map<String, Object> attributes) 
    {
        return OAuth2Attribute.builder()
        		.id((String) attributes.get("sub"))
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .provider(Provider.GOOGLE)
                .attributes(attributes)
                .attributeKey(attributeKey)
                .build();
    }

    private static OAuth2Attribute ofKakao(String attributeKey, Map<String, Object> attributes) 
    {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

        return OAuth2Attribute.builder()
        		.id(String.valueOf(attributes.get("id")))
                .name((String) kakaoProfile.get("nickname"))
                .email((String) kakaoAccount.get("email"))
                .provider(Provider.KAKAO)
                .attributes(kakaoAccount)
                .attributeKey(attributeKey)
                .build();
    }

    private static OAuth2Attribute ofNaver(String attributeKey, Map<String, Object> attributes) 
    {
    	Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuth2Attribute.builder()
        		.id((String) response.get("id"))
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .provider(Provider.NAVER)
                .attributes(response)
                .attributeKey(attributeKey)
                .build();
    }

    Map<String, Object> convertToMap() 
    {
    	Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("key", attributeKey);
        map.put("email", email);
        map.put("name", name);
        map.put("provider", provider);

        return map;
    }
}