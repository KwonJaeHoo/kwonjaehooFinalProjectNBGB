package com.sist.nbgb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sist.nbgb.entity.Reference;
import com.sist.nbgb.entity.ReferenceAnswer;

public interface ReferenceAnswerRepository extends JpaRepository<ReferenceAnswer, Reference>
{
	//ReferenceAnswer findByReference_referenceId(Reference reference);
}
