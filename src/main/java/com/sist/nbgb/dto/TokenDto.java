package com.sist.nbgb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class TokenDto 
{
    private String grantType;
    private String accessToken;
    private Long accessTokenExpirationTime;
    private String refreshToken;
}