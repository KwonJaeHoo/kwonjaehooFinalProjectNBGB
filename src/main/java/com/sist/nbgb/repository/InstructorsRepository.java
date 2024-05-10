package com.sist.nbgb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	
	List<Instructors> findAllByInstructorId(String instructorId);
	
	//아이디로 찾기
	Instructors findFirstByInstructorId(String instructorId);
	
	//페이징(관리자용)
	Page<Instructors> findAllByOrderByInstructorRegdateDesc(Pageable pageable);
	
	//검색용(관리자용)
	Page<Instructors> findByInstructorIdContainingOrInstructorEmailContainingOrInstructorNicknameContainingOrInstructorPhoneContaining(String instructorId, String instructorEmail, String instructorNickname, String instructorPhone, Pageable pageable);
}
