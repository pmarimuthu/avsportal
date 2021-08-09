package com.avs.portal.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.avs.portal.entity.UserCredential;

public interface UserCredentialRepository extends CrudRepository<UserCredential, UUID> {
	
}
