package com.sist.nbgb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sist.nbgb.entity.Instructors;

@Repository
public interface InstructorsRepository extends JpaRepository<Instructors, String>
{
	Boolean existsByInstructorId(String instructorId);
	Optional<Instructors> findByInstructorId(String instructorId);
}
