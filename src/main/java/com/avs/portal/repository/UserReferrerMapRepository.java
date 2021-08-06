package com.avs.portal.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.avs.portal.entity.UserReferrerMap;

public interface UserReferrerMapRepository extends CrudRepository<UserReferrerMap, UUID> {

}
