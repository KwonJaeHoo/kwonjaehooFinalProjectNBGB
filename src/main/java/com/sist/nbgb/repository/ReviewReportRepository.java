package com.sist.nbgb.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sist.nbgb.entity.ReviewReport;
import com.sist.nbgb.entity.ReviewReportId;

public interface ReviewReportRepository extends JpaRepository<ReviewReport, ReviewReportId> {	
	Page<ReviewReport> findAll(Pageable pageable);
}
