package com.avs.portal.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.avs.portal.entity.UserRoleMap;

public interface UserRoleMapRepository extends CrudRepository<UserRoleMap, UUID> {

}
