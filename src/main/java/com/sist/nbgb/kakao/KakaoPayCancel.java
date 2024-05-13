package com.sist.nbgb.kakao;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KakaoPayCancel
{
	private String cid;
	
	private String tid;

	private String partner_order_id;
	
	private String partner_user_id;
	
	private String payment_method_type;

	private Amount amount;
	
	private ApprovedCancelAmount approved_cancel_amount;
	
	private CanceledAmount canceled_amount;
	
	private CancelAvailableAmount cancel_available_amount;
	
	private String item_name;
	
	private String item_code;
	
	private int quantity;
	
	private Date created_at;
	
	private Date approved_at;
	
	private Date canceled_at;
	
	private String status;
	
	private String payload;	
}