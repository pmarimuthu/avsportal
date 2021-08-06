package com.avs.portal.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.avs.portal.entity.UserCredential;

public interface UserCredentialRepository extends CrudRepository<UserCredential, UUID> {

	List<UserCredential> findAll();
	
	List<UserCredential> findByUserIdAndPassword(UUID userId, String password);
}
