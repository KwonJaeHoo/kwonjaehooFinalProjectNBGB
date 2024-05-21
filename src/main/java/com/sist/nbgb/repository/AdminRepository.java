package com.sist.nbgb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sist.nbgb.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, String>
{
	Optional<Admin> findByAdminId(String adminId);
	Admin findAdminNameByAdminId(String adminId);
}
