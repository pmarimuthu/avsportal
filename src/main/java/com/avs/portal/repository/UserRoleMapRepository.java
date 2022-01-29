package com.avs.portal.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.avs.portal.entity.User;
import com.avs.portal.entity.UserRoleMap;
import com.avs.portal.enums.RoleEnum;

public interface UserRoleMapRepository extends CrudRepository<UserRoleMap, UUID> {

	@Override
	List<UserRoleMap> findAll();
	
	UserRoleMap findByUser(User user);
	
	List<UserRoleMap> findByRole(RoleEnum role);
	
}
