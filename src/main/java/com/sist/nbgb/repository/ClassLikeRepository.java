package com.sist.nbgb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sist.nbgb.entity.ClassId;
import com.sist.nbgb.entity.ClassLike;

public interface ClassLikeRepository extends JpaRepository<ClassLike, ClassId>{
	ClassLike findByClassId_classIdAndClassId_classIdenAndClassId_userId(Long classId, String classIden, String userId);
	
	//좋아요 개수
	Long countByClassId_classId(long classId);
	
	//내가 좋아요 했는지 확인
	Long countByClassId_classIdAndClassId_classIdenAndClassId_userId(Long classId, String classIden, String userId);
}
