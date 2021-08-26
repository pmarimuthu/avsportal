package com.avs.portal.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.avs.portal.entity.UserFamilyMap;

public interface UserFamilyMapRepository extends CrudRepository<UserFamilyMap, UUID> {

	List<UserFamilyMap> findAll();
}
