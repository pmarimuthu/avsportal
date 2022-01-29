package com.avs.portal.repository;

import java.util.Set;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.avs.portal.entity.UserVerification;

public interface UserVerificationRepository extends CrudRepository<UserVerification, UUID> {

	@Override
	Set<UserVerification> findAll();
}
