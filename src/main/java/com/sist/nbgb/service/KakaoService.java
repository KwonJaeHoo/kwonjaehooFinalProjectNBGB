package com.sist.nbgb.service;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.sist.nbgb.dto.KakaoPaymentCancelDto;
import com.sist.nbgb.kakao.KakaoPayCancel;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KakaoService 
{
	private static final String cid = "TC0ONETIME";
											//aba0de9a41c32c20845d6050f0fd01ee
	private static final String admin_Key = "b5da1907f4cf9df4cafd9ebb58dfcf1e";
	
	public KakaoPayCancel kakaoPayCancel(KakaoPaymentCancelDto kakaoPaymentCancelDto)
	{
		KakaoPayCancel kakaoPayCancel = null;
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders httpHeaders = new HttpHeaders();
		MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
		
		httpHeaders.add("Authorization", "KakaoAK " + admin_Key);
		httpHeaders.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=utf-8");
		
		params.add("cid", cid);
		params.add("tid", kakaoPaymentCancelDto.getTid());
		params.add("cancel_amount", kakaoPaymentCancelDto.getTotalAmount());
		params.add("cancel_tax_free_amount", kakaoPaymentCancelDto.getTaxFreeAmount());
		
		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(params, httpHeaders);
		
		try
		{
			kakaoPayCancel = restTemplate.postForObject(new URI("https://kapi.kakao.com/v1/payment/cancel"), request, KakaoPayCancel.class);
		} 
		catch (URISyntaxException e) 
		{
			new URISyntaxException("https://kapi.kakao.com/v1/payment/cancel", "kakaoPayCancel URISyntaxException " + e);
		}

		return kakaoPayCancel;
	}
}