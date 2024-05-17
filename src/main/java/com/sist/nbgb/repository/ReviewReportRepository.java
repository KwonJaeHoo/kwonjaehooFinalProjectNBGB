package com.sist.nbgb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sist.nbgb.entity.ReviewReport;
import com.sist.nbgb.entity.ReviewReportId;

public interface ReviewReportRepository extends JpaRepository<ReviewReport, ReviewReportId> {
	boolean existsById_reviewIdAndId_userId(Long reviewId, String userId);
}
