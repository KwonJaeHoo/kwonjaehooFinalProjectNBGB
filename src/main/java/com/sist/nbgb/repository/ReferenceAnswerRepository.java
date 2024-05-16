package com.sist.nbgb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sist.nbgb.entity.ReferenceAnswer;

public interface ReferenceAnswerRepository extends JpaRepository<ReferenceAnswer, Long>
{
	ReferenceAnswer findByRefId(Long reference);
}
