package com.sist.nbgb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sist.nbgb.entity.User;

public interface UserRepository extends JpaRepository<User, String>
{
	Boolean existsByUserId(String userId);
}
