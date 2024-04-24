package com.sist.nbgb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sist.nbgb.entity.ReviewFile;
import com.sist.nbgb.entity.ReviewFileId;

public interface ReviewFileRepository extends JpaRepository<ReviewFile, ReviewFileId> {
	List<ReviewFile> findAll();
}
