package com.sist.nbgb.dto;

import java.time.LocalDateTime;

import com.sist.nbgb.entity.Review;
import com.sist.nbgb.entity.ReviewReport;
import com.sist.nbgb.entity.User;
import com.sist.nbgb.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ReviewReportRequest {
	private Review reviewId;
	private User userId;
	private String reportReason;
	private LocalDateTime reportDate;
	private Status reportStatus;
	
	public ReviewReport toEntity() {
		return ReviewReport.builder()
				.reviewId(reviewId)
				.userId(userId)
				.reportDate(reportDate)
				.reportReason(reportReason)
				.reportStatus(reportStatus)
				.build();
	}
}
