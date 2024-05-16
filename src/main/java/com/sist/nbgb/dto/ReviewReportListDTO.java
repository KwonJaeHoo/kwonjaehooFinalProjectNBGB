package com.sist.nbgb.dto;

import java.time.LocalDateTime;

import com.sist.nbgb.entity.Review;
import com.sist.nbgb.entity.ReviewReport;
import com.sist.nbgb.entity.User;
import com.sist.nbgb.enums.Status;

import lombok.Getter;

@Getter
public class ReviewReportListDTO {
	private Review reviewId;
	private User userId;
	private LocalDateTime reportDate;
	private String reportReason;
	private Status reportStatus;
	
	public ReviewReportListDTO(ReviewReport reviewReport) {
		this.reviewId = reviewReport.getReviewId();
		this.userId = reviewReport.getUserId();
		this.reportDate = reviewReport.getReportDate();
		this.reportReason = reviewReport.getReportReason();
		this.reportStatus = reviewReport.getReportStatus();
	}
}
