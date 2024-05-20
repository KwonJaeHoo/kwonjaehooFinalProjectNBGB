package com.sist.nbgb.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sist.nbgb.entity.ReviewReport;
import com.sist.nbgb.entity.ReviewReportId;
import com.sist.nbgb.entity.Review;
import com.sist.nbgb.entity.ReviewReport;
import com.sist.nbgb.entity.ReviewReportId;
import com.sist.nbgb.entity.User;
import com.sist.nbgb.enums.Status;

public interface ReviewReportRepository extends JpaRepository<ReviewReport, ReviewReportId> {	
	boolean existsById_reviewIdAndId_userId(Long reviewId, String userId);
	
	Page<ReviewReport> findAll(Pageable pageable);
	
	@Query("select r from ReviewReport r where r.reportStatus = 'J'")
	Page<ReviewReport> findNotRecived(Pageable pageable);
	
	ReviewReport findByReviewId_reviewIdAndUserId_userId(@Param(value="reviewId")Long reviewId, @Param(value="userId")String userId);
	
	@Modifying
	@Query("update ReviewReport r set r.reportStatus = :reportStatus where r.reviewId = :reviewId and r.userId = :userId")
	int updateReportStatus(@Param(value="reviewId")Review reviewId, @Param(value="userId")User userId, @Param(value="reportStatus")Status reportStatus);
	
	@Modifying
	@Query("update ReviewReport r set r.reportStatus = :reportStatus where r.reportStatus = 'J' and r.reviewId = :reviewId")
	int updateAllReportStatus(@Param(value="reviewId")Review reviewId, @Param(value="reportStatus")Status reportStatus);

}
