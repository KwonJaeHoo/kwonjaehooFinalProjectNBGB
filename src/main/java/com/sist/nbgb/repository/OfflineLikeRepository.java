package com.sist.nbgb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sist.nbgb.entity.ClassId;
import com.sist.nbgb.entity.ClassLike;

public interface OfflineLikeRepository extends JpaRepository<ClassLike, ClassId>
{
	Long countByClassId_classIdAndClassId_classIdenAndClassId_userId(Long classId, String classIden, String userId);
	
	Long countByClassId_classIdAndClassId_classIden(Long classId, String classIden);
	
	//찜 삭제
	int deleteByClassId_classIdAndClassId_classIdenAndClassId_userId(Long classId, String classIden, String userId);
}
