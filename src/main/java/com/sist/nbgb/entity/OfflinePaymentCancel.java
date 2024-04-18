package com.sist.nbgb.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name="NBGB_OFFLINE_PAYMENT_CANCEL")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class OfflinePaymentCancel 
{
	@Column(length = 32)
	@Comment("가맹점 코드(고정값)")
	private String cid;
	
	@Column(length = 32)
	@Comment("결제 고유번호(20자,카카오 제공)")
	private String tid;
	
	@Id
	@Column(length = 32)
	@Comment("가맹점 주문번호")
	private String partnerOrderId;
	
	@Column(length = 32)
	@Comment("가맹점 회원 아이디")
	private String partnerUserId;
	
	@Column(length = 32)
	@Comment("상품코드(오프라인 강의 글번호)")
	private String itemCode;
	
	@Column(length = 32)
	@Comment("상품명(오프라인 강의 제목)")
	private String itemName;
	
	@Column(columnDefinition = "CHAR(8)")
	@Comment("예약 날짜(년월일)")
	private String bookingDate;
	
	@Column(columnDefinition = "CHAR(2)")
	@Comment("예약 시간")
	private String bookingTime;
	
	@Column(columnDefinition = "NUMBER(15)")
	@Comment("사용 포인트")
	private Long point;
	
	@Column(columnDefinition = "NUMBER(15)")
	@Comment("취소 상품 총액")
	private Long cancelTotalAmount;
	
	@Column(columnDefinition = "NUMBER(15)")
	@Comment("취소 상품 비과세 금액(무조건 0)")
	private Long cancelTaxFreeAmount;
	
	@CreatedDate
	@Comment("취소날짜")
	private LocalDateTime canceledAt;
	
}