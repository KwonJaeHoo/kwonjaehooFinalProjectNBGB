package com.sist.nbgb.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sist.nbgb.dto.OfflineApproveResponse;
import com.sist.nbgb.dto.OfflinePayBeforeDto;
import com.sist.nbgb.dto.OfflinePayDto;
import com.sist.nbgb.dto.OfflinePayReadyDto;
import com.sist.nbgb.dto.OfflinePaymentApproveDto;
import com.sist.nbgb.dto.OfflineReadyResponse;
import com.sist.nbgb.dto.OnlinePayBeforeDto;
import com.sist.nbgb.dto.OnlinePayDto;
import com.sist.nbgb.dto.OnlinePayReadyDto;
import com.sist.nbgb.dto.OnlinePaymentApproveDto;
import com.sist.nbgb.dto.OnlinePopDTO;
import com.sist.nbgb.entity.OfflinePaymentApprove;
import com.sist.nbgb.entity.OnlinePaymentApprove;
import com.sist.nbgb.entity.User;
import com.sist.nbgb.response.UUIDUtil;
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
	public ResponseEntity<OfflineReadyResponse> payReady(@RequestPart(value="onlinePayBeforeDto") OnlinePayBeforeDto payDto, Model model)
	{		
		System.out.println("controller : " + payDto.getOnlineClassId());
		System.out.println("controller : " + payDto.getUserId());
		System.out.println("controller : " + payDto.getOnlineClassTitle());
		System.out.println("controller : " + payDto.getTotalAmount());
		System.out.println("controller : " + payDto.getUsedPoint());
		
		String orderId = UUIDUtil.uniqueValue();
		
		OnlinePaymentApproveDto approveDto = OnlinePaymentApproveDto.builder()
				.cid(cid)
				.partnerOrderId(orderId)
				.partnerUserId(payDto.getUserId())
				.itemCode(payDto.getOnlineClassId())
				.itemName(payDto.getOnlineClassTitle())
				.point(Long.valueOf(payDto.getUsedPoint())) //여기 수정함 user->used
				.taxFreeAmount(Long.valueOf(payDto.getTotalAmount()))
				.approvedAt(LocalDateTime.now())
				.totalAmount(Long.valueOf(payDto.getTotalAmount()))
				.build();
		
		OfflineReadyResponse readyResponse = null;
		
		readyResponse = onlineService.payReady(approveDto);

		if(readyResponse != null)
		{
			readyResponse.setOrderId(approveDto.getPartnerOrderId());
			
			System.out.println("readyResponse : " + readyResponse.getTid());
			
			readyResponse.setCode(0);
		}
		
        return ResponseEntity.ok(readyResponse);
	}
	
	//결제 팝업창
	@PostMapping("/online/payPopUp")
	public String payPopUp(Model model, OnlinePayReadyDto payDto)
	{
		System.out.println("payPopUp : " + payDto.getPcUrl());
		System.out.println("payPopUp : " + payDto.getOrderId());
		System.out.println("payPopUp : " + payDto.getTid());
		System.out.println("payPopUp : " + payDto.getPcUrl());
		System.out.println("payPopUp : " + payDto.getUsedPoint());
		
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		
		model.addAttribute("payDto", payDto);
		model.addAttribute("userId", userId);
		
		return "onlineClass/pay/payPopUp";
	}
		
	//결제 완료
	@PostMapping("/online/payResult")
	public String payResult(Model model, OnlinePayDto payDto) {
		System.out.println("payDto : " + payDto.getOrderId());
		System.out.println("pgtoken : " + payDto.getPgToken());
		
		OfflineApproveResponse payApprove = onlineService.ApproveResponse(payDto, payDto.getPgToken());
		
		System.out.println("payApprove : " + payApprove.getPartner_user_id());
		System.out.println("payApprove : " + payApprove.getPartner_order_id());
		System.out.println("payApprove : " + payApprove.getCid());
		
		
		onlineService.payUpload(payApprove, payDto.getUsedPoint());
		
		Optional<OnlinePaymentApprove> pay = onlineService.findAllByPartnerOrderId(payApprove.getPartner_order_id());
		Optional<User> user = onlineService.findByUserId(payApprove.getPartner_user_id());
		
		Long payPoint = Long.valueOf(payDto.getUsedPoint());//pay.get().getPoint();
		Long userPoint = user.get().getUserPoint();
		Long getPoint = (long)(Math.floor(payApprove.getAmount().getTotal()*0.01));
		Long updatePoint = userPoint - payPoint + getPoint;
		log.info("payApprove.getAmount().getTotal():"+payApprove.getAmount().getTotal());
		log.info("payPonit:" + payPoint + ", userPoint:" + userPoint + ", getPoint:" + getPoint + ", updatePoint:" + updatePoint);
		
		onlineService.updatePoint(payApprove.getPartner_user_id(), updatePoint);
		
		model.addAttribute("payApprove", payApprove);
		model.addAttribute("user", user.get());
		model.addAttribute("pay", pay.get());
		model.addAttribute("updatePoint", updatePoint);
		model.addAttribute("getPoint", getPoint);
		
		return "onlineClass/pay/payResult";
	}
	
	@GetMapping(value="/online/paySuccess")
	public String paySuccess(ModelMap model, String pg_token)
	{		
		System.out.println("pgToken : " + pg_token);
		model.addAttribute("pgToken", pg_token);
		
		return "onlineClass/pay/paySuccess";
	}
}
