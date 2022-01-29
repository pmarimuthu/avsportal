package com.avs.portal.repository;

import java.util.Collection;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.avs.portal.entity.UserFamilyMap;

public interface UserFamilyMapRepository extends CrudRepository<UserFamilyMap, UUID> {

	@Override
	Collection<UserFamilyMap> findAll();

	Collection<UserFamilyMap> findDistinctByFamilyHeadIdNotNull();
}
