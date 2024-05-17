package com.sist.nbgb.dto;

import java.time.LocalDateTime;

import com.sist.nbgb.entity.Review;
import com.sist.nbgb.entity.ReviewReport;
import com.sist.nbgb.entity.ReviewReportId;
import com.sist.nbgb.entity.User;
import com.sist.nbgb.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ReviewReportDTO {
	private Review reviewId;
	private User userId;
	private String reportReason;
	private LocalDateTime reportDate;
	private Status reportStatus;

	public ReviewReportDTO(ReviewReport report) {
		this.reviewId = report.getReviewId();
		this.userId = report.getUserId();
		this.reportReason = report.getReportReason();
		this.reportDate = report.getReportDate();
		this.reportStatus = report.getReportStatus();
	}
	
	public ReviewReport toEntity() {
		ReviewReportId reviewReportId = new ReviewReportId();
        reviewReportId.setReviewId(reviewId.getReviewId());
        reviewReportId.setUserId(userId.getUserId());
        
		return ReviewReport.builder()
				.id(reviewReportId)
				.reviewId(reviewId)
				.userId(userId)
				.reportDate(reportDate)
				.reportReason(reportReason)
				.reportStatus(reportStatus)
				.build();
	}

	
	
}
