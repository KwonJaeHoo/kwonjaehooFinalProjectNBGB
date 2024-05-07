package com.sist.nbgb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sist.nbgb.entity.User;
import com.sist.nbgb.entity.UserFile;
import com.sist.nbgb.entity.UserFileId;

public interface UserFileRepository extends JpaRepository<UserFile, UserFileId>
{
	List<UserFile> findAllByUserId(User user);
}
