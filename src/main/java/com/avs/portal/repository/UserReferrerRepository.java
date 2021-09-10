package com.avs.portal.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.avs.portal.entity.User;
import com.avs.portal.entity.UserReferrer;

public interface UserReferrerRepository extends CrudRepository<UserReferrer, UUID> {

	List<UserReferrer> findAll();
	
	UserReferrer findByUser(User user);
	
	List<UserReferrer> findByReferredByUserId(UUID userId);
}
