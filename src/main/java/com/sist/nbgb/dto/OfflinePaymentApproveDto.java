package com.sist.nbgb.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Id;

import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;

import com.sist.nbgb.entity.OfflinePaymentApprove;
import com.sist.nbgb.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class OfflinePaymentApproveDto 
{
	//가맹점 코드(고정값)
	private String cid;
	
	//결제 고유번호(20자,카카오 제공)
	private String tid;
	
	//가맹점 주문번호
	private String partnerOrderId;
	
	//가맹점 회원 아이디
	private String partnerUserId;
	
	//상품코드(오프라인 강의 글번호)
	private String itemCode;
	
	//상품명(오프라인 강의 제목)
	private String itemName;
	
	//예약 날짜(년월일)
	private String bookingDate;
	
	//예약 시간
	private String bookingTime;

	//사용 포인트
	private Long point;

	//상품 총액
	private Long totalAmount;
	
	//상품 비과세 금액(무조건 0)
	private Long taxFreeAmount;
	
	//결제날짜
	private LocalDateTime approvedAt;
	
	private String status;
	
	public OfflinePaymentApproveDto(OfflinePaymentApprove approve)
	{
		this.cid = approve.getCid();
		this.tid = approve.getTid();
		this.partnerOrderId = approve.getPartnerOrderId();
		this.partnerUserId = approve.getPartnerUserId();
		this.itemCode = approve.getItemCode();
		this.itemName = approve.getItemName();
		this.bookingDate = approve.getBookingDate();
		this.bookingTime = approve.getBookingTime();
		this.point = approve.getPoint();
		this.totalAmount = approve.getTotalAmount();
		this.taxFreeAmount = approve.getTaxFreeAmount();
		this.approvedAt = approve.getApprovedAt();
		this.status = approve.getStatus();
	}
	
	public static OfflinePaymentApproveDto toDto(OfflinePaymentApprove approve)
	{
		return OfflinePaymentApproveDto.builder()
				.cid(approve.getCid())
				.tid(approve.getTid())
				.partnerOrderId(approve.getPartnerOrderId())
				.partnerUserId(approve.getPartnerUserId())
				.itemCode(approve.getItemCode())
				.itemName(approve.getItemName())
				.bookingDate(approve.getBookingDate())
				.bookingTime(approve.getBookingTime())
				.point(approve.getPoint())
				.taxFreeAmount(approve.getTaxFreeAmount())
				.approvedAt(approve.getApprovedAt())
				.status(approve.getStatus())
				.build();
	}
}
