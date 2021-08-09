package com.avs.portal.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.avs.portal.entity.UserVerification;

public interface UserVerificationRepository extends CrudRepository<UserVerification, UUID> {

	List<UserVerification> findAll();
}
