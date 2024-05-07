package com.sist.nbgb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sist.nbgb.entity.User;

public interface OfflineUserRepository  extends JpaRepository<User, String>
{
	Optional<User> findByUserId(String userId);
	
	@Modifying
	@Query("UPDATE User "
			+ "SET userPoint = :point "
			+ "WHERE userId = :userId")
	int updatePoint(@Param("userId") String userId, @Param("point") Long point);
}
