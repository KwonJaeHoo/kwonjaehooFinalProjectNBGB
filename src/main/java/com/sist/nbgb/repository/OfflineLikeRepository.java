package com.sist.nbgb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sist.nbgb.entity.ClassId;
import com.sist.nbgb.entity.ClassLike;

public interface OfflineLikeRepository extends JpaRepository<ClassLike, ClassId>
{
	@Query("SELECT COUNT(classId) FROM ClassLike WHERE classId = :offlineClassId AND userId = :userId AND classIden = 'OFF'")
	int findDuplicationLike(@Param("offlineClassId") Long offlineClassId, @Param("userId") String userId);
}
