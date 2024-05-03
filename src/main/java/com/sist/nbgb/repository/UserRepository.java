package com.sist.nbgb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sist.nbgb.entity.User;
import com.sist.nbgb.enums.Provider;

@Repository
public interface UserRepository extends JpaRepository<User, String>
{
	Boolean existsByUserId(String userId);
	Boolean existsByUserEmailAndUserProvider(String userEmail, Provider provider);

	User findUserIdByUserNameAndUserEmailAndUserProvider(String userName, String userEmail, Provider provider);
	User findUserEmailByUserIdAndUserNameAndUserEmailAndUserProvider(String userId, String userName, String userEmail, Provider provider);
	
	Optional<User> findByUserId(String userId);

	User findFirstByUserId(String userId);
	
	Optional<User> findById(String userId);
}

