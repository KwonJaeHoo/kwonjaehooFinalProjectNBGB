package com.sist.nbgb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.sist.nbgb.entity.Reference;
import java.util.List;

@Transactional
@Repository
public interface ReferenceRepository extends JpaRepository<Reference, Long> {
    List<Reference> findAllByOrderByRefRegdateDesc();
}