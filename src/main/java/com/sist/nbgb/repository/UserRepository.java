package com.sist.nbgb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sist.nbgb.entity.User;
import com.sist.nbgb.enums.Provider;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, String>
{
	Boolean existsByUserId(String userId);
	Boolean existsByUserEmailAndUserProvider(String userEmail, Provider provider);

	User findUserIdByUserNameAndUserEmailAndUserProvider(String userName, String userEmail, Provider provider);
	User findUserEmailByUserIdAndUserNameAndUserEmailAndUserProvider(String userId, String userName, String userEmail, Provider provider);
	
	Optional<User> findByUserId(String userId);

	User findFirstByUserId(String userId);
	List<User> findAllByUserId(String userId);
	
	//페이징(관리자용)
	Page<User> findAllByOrderByUserRegdateDesc(Pageable pageable);
	
	//검색용(관리자용)
	Page<User> findByUserIdContainingOrUserEmailContainingOrUserNameContainingOrUserPhoneContaining(String userId, String userEmail, String userName, String userPhone, Pageable pageable);
	
	@Modifying
	@Query("UPDATE User "
			+ "SET userPoint = userPoint + :point "
			+ "WHERE userId = :userId")
	int payPoint(@Param("userId") String userId, @Param("point") Long point);
	
	@Modifying
	@Query("UPDATE User "
			+ "SET userPoint = userPoint - :point "
			+ "WHERE userId = :userId")
	int returnPoint(@Param("userId") String userId, @Param("point") Long point);
}