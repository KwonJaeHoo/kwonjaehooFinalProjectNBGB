package com.sist.nbgb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sist.nbgb.entity.Instructors;

@Repository
public interface InstructorsRepository extends JpaRepository<Instructors, String>
{
	Boolean existsByInstructorId(String instructorId);
	Boolean existsByInstructorEmail(String instructorEmail);
	
	Instructors findInstructorIdByInstructorNameAndInstructorEmail(String instructorName, String instructorEmail);
	Instructors findInstructorEmailByInstructorIdAndInstructorNameAndInstructorEmail(String instructorId, String instructorName, String instructorEmail);
	
	Optional<Instructors> findByInstructorId(String instructorId);
	
	//아이디로 찾기
	Instructors findFirstByInstructorId(String instructorId);
}
