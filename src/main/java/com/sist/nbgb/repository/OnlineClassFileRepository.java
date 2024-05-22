package com.sist.nbgb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sist.nbgb.entity.OnlineClass;
import com.sist.nbgb.entity.OnlineClassFile;
import com.sist.nbgb.entity.OnlineClassFileId;


public interface OnlineClassFileRepository extends JpaRepository<OnlineClassFile, OnlineClassFileId>{
	//강의 자료 조회
	OnlineClassFile findByOnlineClassFileIdOnlineClassIdAndOnlineClassFileIdOnlineFileId(@Param("onlineClassId") Long onlineClassId, @Param("onlineFileId") Long onlineFileId);
	OnlineClassFile findByOnlineClassFileId(OnlineClassFileId onlineClassFileId);
	List<OnlineClassFile> findByOnlineClassId(OnlineClass onlineClassId);
	
	//강의 자료 개수 조회
	Long countByOnlineClassFileIdOnlineClassId(@Param("onlineClassId") Long onlineClassId);
	
	//강의 자료 리스트 전체 조회
	List<OnlineClassFile> findAllByOnlineClassFileIdOnlineClassIdOrderByOnlineFileName(@Param("onlineClassId") Long onlineClassId);
	
	Long countByOnlineClassFileId_onlineClassId(Long onlineClassId);
	
	//강의 첨부파일 삭제
	@Modifying
	@Query("delete from OnlineClassFile o where o.onlineClassFileId.onlineClassId =:onlineClassId and o.onlineClassFileId.onlineFileId =:onlineFileId")
	int deleteFile(@Param("onlineClassId") Long onlineClassId, @Param("onlineFileId") Long onlineFileId);
	
	//강의 classId로 삭제
	int deleteByOnlineClassFileId_onlineClassId(Long onlineClassId);
}
