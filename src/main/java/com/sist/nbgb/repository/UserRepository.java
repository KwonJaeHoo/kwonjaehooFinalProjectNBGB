package com.sist.nbgb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sist.nbgb.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>
{
	Boolean existsByUserId(String userId);
	Optional<User> findByUserId(String userId);
}
