package com.avs.portal.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.avs.portal.entity.UserAccountStatus;

public interface UserAccountStatusRepository extends CrudRepository<UserAccountStatus, UUID> {

	List<UserAccountStatus> findAll();
	
}
