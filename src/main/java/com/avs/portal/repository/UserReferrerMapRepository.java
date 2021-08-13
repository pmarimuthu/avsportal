package com.avs.portal.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.avs.portal.entity.User;
import com.avs.portal.entity.UserReferrerMap;

public interface UserReferrerMapRepository extends CrudRepository<UserReferrerMap, UUID> {

	List<UserReferrerMap> findAll();
	
	UserReferrerMap findByUser(User user);
	
	List<UserReferrerMap> findByReferredByUserId(UUID userId);
}
