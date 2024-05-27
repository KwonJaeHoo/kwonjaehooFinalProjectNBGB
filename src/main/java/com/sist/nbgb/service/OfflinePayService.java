package com.sist.nbgb.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.sist.nbgb.dto.OfflineApproveResponse;
import com.sist.nbgb.dto.OfflinePayDto;
import com.sist.nbgb.dto.OfflinePaymentApproveDto;
import com.sist.nbgb.dto.OfflineReadyResponse;
import com.sist.nbgb.entity.OfflinePaymentApprove;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OfflinePayService 
{
	static final String cid = "TC0ONETIME";
	static final String admin_Key = "b5da1907f4cf9df4cafd9ebb58dfcf1e";
	private OfflineReadyResponse payReady;
	
	private final OfflineService offlineService;
	
	public OfflineReadyResponse payReady(OfflinePaymentApproveDto offPayDto)
	{
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		
		HttpHeaders httpHeaders = new HttpHeaders();
		
		String auth = "KakaoAK " + admin_Key;
		
		System.out.println("service : " + offPayDto.getTotalAmount());
		
		httpHeaders.set("Authorization", auth);
		httpHeaders.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		parameters.add("cid", cid);
        parameters.add("partner_order_id", offPayDto.getPartnerOrderId());
        parameters.add("partner_user_id", offPayDto.getPartnerUserId());
        parameters.add("item_name", offPayDto.getItemName());
        parameters.add("quantity", "1");
        parameters.add("total_amount", String.valueOf(offPayDto.getTotalAmount()));
        parameters.add("vat_amount", "0");
        parameters.add("tax_free_amount", "0");
        parameters.add("approval_url", "http://localhost:8008/offlineClass/reserve/pay/paysuccess"); // 성공 시 redirect url
        parameters.add("cancel_url", "http://localhost:8008/offlineClass/reserve/pay/paycancel"); // 취소 시 redirect url
        parameters.add("fail_url", "http://localhost:8008/offlineClass/reserve/pay/payfail");
        
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, httpHeaders);
        
        RestTemplate restTemplate = new RestTemplate();
        
        try 
		{
        	payReady = restTemplate.postForObject(
	        		new URI("https://kapi.kakao.com/v1/payment/ready"),
	        		requestEntity, 
	        		OfflineReadyResponse.class);
	        
	        payReady.setTid(payReady.getTid());
		}
		catch(URISyntaxException e) 
		{
			System.out.println(e);
		}
        
        return payReady;
	}
	
	 public OfflineApproveResponse ApproveResponse(OfflinePayDto payDto, String pgToken) 
	 {
		 OfflineApproveResponse payApprove = null;
		 
		 RestTemplate restTemplate = new RestTemplate();
		 
		 HttpHeaders httpHeaders = new HttpHeaders();
			
		 String auth = "KakaoAK " + admin_Key;
		
		 httpHeaders.set("Authorization", auth);
		 httpHeaders.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		 
		 MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		 
		 System.out.println("service : " + payDto.getOrderId());
		 
		 Optional<OfflinePaymentApprove> approveDto = offlineService.findbyPartnerOrderId(payDto.getOrderId());
			
		 parameters.add("cid", cid);
		 parameters.add("tid", payDto.getTid());
         parameters.add("partner_order_id", payDto.getOrderId());
         parameters.add("partner_user_id", approveDto.get().getPartnerUserId());
         parameters.add("pg_token", pgToken);
        
         HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, httpHeaders);
        
         payApprove = restTemplate.postForObject(
         		"https://kapi.kakao.com/v1/payment/approve",
         		requestEntity, 
         		OfflineApproveResponse.class);
         
         return payApprove;
	 }
}
