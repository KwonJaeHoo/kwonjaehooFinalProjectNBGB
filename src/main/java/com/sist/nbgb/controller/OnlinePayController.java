package com.sist.nbgb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class OnlinePayController {
	//카카오페이 결제 내역 확인 팝업
	@GetMapping("/online/pay")
	public String pay(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
//		long promSeq = HttpUtil.get(request, "promSeq", (long)0);
//		PromBoard promBoard = null;
//		
//		logger.debug("promseq ?????????????????" + promSeq);
//		if(promSeq > 0)
//		{
//			promBoard = boardService.promBoardView(promSeq);
//			
//		}
//		
//		model.addAttribute("promBoard", promBoard);
		return "onlineClass/pay/pay";
	}
	
	//카카오페이 결제 승인 시 response
	@GetMapping("/online/payResult")
	public String payResult(ModelMap model, HttpServletRequest reqeust, HttpServletResponse response) {
//		KakaoPayApprove kakaoPayApprove = null;
//		
//		String tId = HttpUtil.get(reqeust, "tId", "");
//		String orderId = HttpUtil.get(reqeust, "orderId", "");
//		String userId = CookieUtil.getHexValue(reqeust, AUTH_COOKIE_USER);
//		String pgToken = HttpUtil.get(reqeust, "pgToken", "");
//		
//		KakaoPayOrder kakaoPayOrder = new KakaoPayOrder();
//		
//		kakaoPayOrder.setPartnerOrderId(orderId);
//		kakaoPayOrder.setPartnerUserId(userId);
//		kakaoPayOrder.settId(tId);
//		kakaoPayOrder.setPgToken(pgToken);
//	
//		kakaoPayApprove = kakaoPayService.kakaoPayApprove(kakaoPayOrder);
//		
//		model.addAttribute("kakaoPayApprove", kakaoPayApprove);
		
		return "onlineClass/pay/payResult";
	}
}
