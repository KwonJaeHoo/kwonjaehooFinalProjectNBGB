package com.sist.nbgb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.sist.nbgb.entity.Reference;
import java.util.List;

@Transactional
@Repository
public interface ReferenceRepository extends JpaRepository<Reference, Long> {
    List<Reference> findAllByOrderByRefRegdateDesc();
    
    Page<Reference> findAllByOrderByRefRegdateDesc(Pageable pageable);
    
    Page<Reference> findByRefTitleContainingOrRefContentContainingOrUserId_UserIdContaining(String title, String content, String userId, Pageable pageable);
}