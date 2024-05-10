package com.sist.nbgb.controller;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sist.nbgb.dto.OfflinePayBeforeDto;
import com.sist.nbgb.dto.OfflinePaymentApproveDto;
import com.sist.nbgb.dto.OfflineReadyResponse;
import com.sist.nbgb.dto.OnlinePaymentApproveDto;
import com.sist.nbgb.dto.OnlinePopDTO;
import com.sist.nbgb.service.OfflinePayService;
import com.sist.nbgb.service.OnlineClassService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class OnlinePayController {
	static final String cid = "TC0ONETIME";
	static final String admin_Key = "b5da1907f4cf9df4cafd9ebb58dfcf1e";
	
	private final OnlineClassService onlineService;
	
	//카카오페이 결제 내역 확인 팝업
	@PostMapping("/online/pay")
	public String pay(ModelMap model, OnlinePopDTO dto) {
		model.addAttribute("dto", dto);
		return "onlineClass/pay/pay";
	}
	
	//결제 요청
	@PostMapping("/online/payReady")
	@ResponseBody
	public ResponseEntity<OfflineReadyResponse> payReady(@RequestPart(value="onlinePayBeforeDto") OnlinePopDTO payDto, Model model)
	{		
		System.out.println("controller : " + payDto.getOnlineClassId());
		System.out.println("controller : " + payDto.getUserId());
		System.out.println("controller : " + payDto.getOnlineClassTitle());
		System.out.println("controller : " + payDto.getTotalAmount());
		
		OnlinePaymentApproveDto approveDto = OnlinePaymentApproveDto.builder()
				.cid(cid)
				//.partnerOrderId
				.partnerUserId(payDto.getUserId())
				.itemCode(payDto.getOnlineClassId())
				.itemName(payDto.getOnlineClassTitle())
				.point(Long.valueOf(payDto.getUserPoint()))
				.taxFreeAmount(Long.valueOf(payDto.getTotalAmount()))
				.approvedAt(LocalDateTime.now())
				.build();
		
		OfflineReadyResponse readyResponse = onlineService.payReady(approveDto);
		
		if(readyResponse != null)
		{
			readyResponse.setOrderId(approveDto.getPartnerOrderId());
			
			System.out.println("readyResponse : " + readyResponse.getTid());
			
			readyResponse.setCode(0);
		}
		else
		{
			//onlineService.deletePay(approveDto.getPartnerOrderId());
			
			readyResponse.setCode(1);
		}
		
        return ResponseEntity.ok(readyResponse);
	}
		
	//카카오페이 결제 승인 시 response
	@GetMapping("/online/payResult")
	public String payResult(ModelMap model, HttpServletRequest reqeust, HttpServletResponse response) {
		
		return "onlineClass/pay/payResult";
	}
}
