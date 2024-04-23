package com.sist.nbgb.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sist.nbgb.dto.OnlineReviewDTO;
import com.sist.nbgb.entity.Review;
import com.sist.nbgb.entity.ReviewId;
import com.sist.nbgb.enums.Status;

public interface OnlineReviewRepository extends JpaRepository<Review, ReviewId> {
	//후기 목록
	List<Review> findByClassIdAndClassIdenAndReviewStatusOrderByReviewRegdateDesc(Long classId, String classIden, Status reviewStatus);	
	
	//후기 개수
	Long countByClassIdAndClassIdenAndReviewStatus(Long classId, String classIden, Status reviewStatus);
 
	//별점 평균
	@Query(value="select round(avg(r.review_rating), 0) from nbgb_review r "
			+ "where r.class_id = :classId and r.class_iden = 'ON' "
			+ "and r.review_status = 'Y'", nativeQuery = true)
	Integer starAvg(@Param("classId") Long classId);

	//후기 목록 페이징
	Page<Review> findAllByClassIdAndClassIdenAndReviewStatus(Pageable pageable, Long classId, String classIden, Status reviewStatus);
}
