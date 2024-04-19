package com.sist.nbgb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sist.nbgb.entity.OnlineClass;

public interface OnlineClassRepository extends JpaRepository<OnlineClass, Long> {
	//조회수 증가
	@Modifying
	@Query("update OnlineClass o set o.onlineClassViews = o.onlineClassViews + 1 where o.onlineClassId = :onlineClassId")
	int updateViews(@Param("onlineClassId") Long onlineClassId);
}
