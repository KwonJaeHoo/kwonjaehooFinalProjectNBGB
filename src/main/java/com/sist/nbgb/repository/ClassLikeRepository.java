package com.sist.nbgb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sist.nbgb.entity.ClassId;
import com.sist.nbgb.entity.ClassLike;

public interface ClassLikeRepository extends JpaRepository<ClassLike, ClassId>{
	Long countByClassId_classId(long classId);
}
