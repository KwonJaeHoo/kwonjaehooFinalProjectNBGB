package com.sist.nbgb.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sist.nbgb.dto.OfflineApproveResponse;
import com.sist.nbgb.dto.OfflinePayBeforeDto;
import com.sist.nbgb.dto.OfflinePayDto;
import com.sist.nbgb.dto.OfflinePayReadyDto;
import com.sist.nbgb.dto.OfflinePaymentApproveDto;
import com.sist.nbgb.dto.OfflinePopDto;
import com.sist.nbgb.dto.OfflineReadyResponse;
import com.sist.nbgb.entity.OfflineClass;
import com.sist.nbgb.entity.OfflinePaymentApprove;
import com.sist.nbgb.entity.User;
import com.sist.nbgb.service.OfflinePayService;
import com.sist.nbgb.service.OfflineService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class OfflinePayController 
{
	private final OfflineService offlineService;
	private final OfflinePayService payService;
	
	//예약하기 결제창
	@PostMapping("/offlineClass/reserve/find")
	public ResponseEntity<Integer> findDate(ModelMap model, @RequestParam(value="classId") Long classId, @RequestParam(value="userId") String userId, @RequestParam(value="bookingDate") String bookingDate, @RequestParam(value="resTime") String resTime)
	{		
		System.out.println("controller : " + userId);
		System.out.println("controller : " + bookingDate);
		System.out.println("controller : " + resTime);
		
		OfflineClass offclass = offlineService.findByView(classId);
		Long peopleCount = offlineService.countPeople(String.valueOf(classId), bookingDate, resTime);
		
		Long limit = offclass.getOfflineClassLimitPeople();
		
		System.out.println("peopleCount : " + peopleCount);
		System.out.println("limit : " + limit);
		
		if(peopleCount < limit)
		{
			return ResponseEntity.ok(0);
		}
		else
		{
			return ResponseEntity.ok(1);
		}
	}
	
	@PostMapping("/offlineClass/reserve/pay")
	public String offlineClassReservePay(ModelMap model, OfflinePopDto offlinePopDto)
	{		
		model.addAttribute("offlinePopDto", offlinePopDto);
		
		return "/offline/pay/pay";
	}
	
	//결제 요청
	@PostMapping("/offlineClass/reserve/pay/payReady")
	@ResponseBody
	public ResponseEntity<OfflineReadyResponse> payReady(@RequestPart(value="offlinePayBeforeDto") OfflinePayBeforeDto payDto, Model model)
	{		
		System.out.println("controller : " + payDto.getOfflineClassId());
		System.out.println("controller : " + payDto.getUserId());
		System.out.println("controller : " + payDto.getOfflineClassTitle());
		System.out.println("controller : " + payDto.getTotalAmount());
		
		OfflinePaymentApproveDto approveDto = offlineService.payUpload(payDto);
		
		OfflineReadyResponse readyResponse = payService.payReady(approveDto);
		
		if(readyResponse != null)
		{
			readyResponse.setOrderId(approveDto.getPartnerOrderId());
			
			System.out.println("readyResponse : " + readyResponse.getTid());
			
			readyResponse.setCode(0);
		}
		else
		{
			offlineService.deletePay(approveDto.getPartnerOrderId());
			
			readyResponse.setCode(1);
		}
		
        return ResponseEntity.ok(readyResponse);
	}
	
	//결제 팝업창
	@PostMapping("/offlineClass/reserve/pay/payPopUp")
	public String payPopUp(Model model, OfflinePayReadyDto payDto)
	{
		System.out.println("payPopUp : " + payDto.getPcUrl());
		System.out.println("payPopUp : " + payDto.getOrderId());
		System.out.println("payPopUp : " + payDto.getTid());
		System.out.println("payPopUp : " + payDto.getPcUrl());
		
		model.addAttribute("payDto", payDto);
		
		return "/offline/pay/payPopUp";
	}
	
	//결제 완료
	@PostMapping("/offlineClass/reserve/pay/payResult")
	public String payResult(Model model, OfflinePayDto payDto)
	{
		System.out.println("payDto : " + payDto.getOrderId());
		System.out.println("pgtoken : " + payDto.getPgToken());
		
		OfflineApproveResponse payApprove = payService.ApproveResponse(payDto, payDto.getPgToken());
		
		System.out.println("payApprove : " + payApprove.getPartner_user_id());
		System.out.println("payApprove : " + payApprove.getPartner_order_id());
		System.out.println("payApprove : " + payApprove.getCid());
		
		offlineService.updatePay(payApprove);
		
		
		Optional<OfflinePaymentApprove> pay = offlineService.findbyPartnerOrderId(payApprove.getPartner_order_id());
		Optional<User> user = offlineService.findByUserId(payApprove.getPartner_user_id());
		
		Long payPoint = pay.get().getPoint();
		Long userPoint = user.get().getUserPoint();
		Long amount  = Long.valueOf(pay.get().getTotalAmount());
		Long plusPoint = amount / 100;
		Long updatePoint = (userPoint - payPoint) + plusPoint;
		
		offlineService.updatePoint(payApprove.getPartner_user_id(), updatePoint);
		
		model.addAttribute("payApprove", payApprove);
		model.addAttribute("user", user.get());
		model.addAttribute("pay", pay.get());
		model.addAttribute("updatePoint", updatePoint);
		
		return "/offline/pay/result";
	}
	
	@GetMapping(value="/offlineClass/reserve/pay/paysuccess")
	public String paySuccess(ModelMap model, String pg_token)
	{		
		System.out.println("pgToken : " + pg_token);
		model.addAttribute("pgToken", pg_token);
		
		return "/offline/pay/success";
	}
	
	/**
     * 결제 진행 중 취소
     */
    @GetMapping("/offlineClass/reserve/pay/paycancel")
    public String cancel() {

        return "/offline/pay/cancel";
    }

    /**
     * 결제 실패
     */
    @GetMapping("/offlineClass/reserve/pay/payfail")
    public String fail() {

        return"/offline/pay/fail";
    }
}
