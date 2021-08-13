package com.avs.portal.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.avs.portal.entity.User;
import com.avs.portal.entity.UserRelationToMeMap;
import com.avs.portal.enums.RelationToMeEnum;

public interface UserRelationToMeMapRepository extends CrudRepository<UserRelationToMeMap, UUID> {

	List<UserRelationToMeMap> findAll();
	
	UserRelationToMeMap findByUser(User user);
	
	List<UserRelationToMeMap> findByRelativeUserId(UUID relativeUserId);
	
	List<UserRelationToMeMap> findByRelationToMe(RelationToMeEnum relationToMe);
	
	
}
