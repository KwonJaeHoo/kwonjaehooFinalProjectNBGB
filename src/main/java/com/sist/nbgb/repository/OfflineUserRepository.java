package com.sist.nbgb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sist.nbgb.entity.User;

public interface OfflineUserRepository  extends JpaRepository<User, String>
{
	Optional<User> findByUserId(String userId);
}
