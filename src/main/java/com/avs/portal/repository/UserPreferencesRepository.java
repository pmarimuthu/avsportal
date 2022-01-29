package com.avs.portal.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.avs.portal.entity.User;
import com.avs.portal.entity.UserPreferences;
import com.avs.portal.enums.VisibilityEnum;

public interface UserPreferencesRepository extends CrudRepository<UserPreferences, UUID> {

	@Override
	List<UserPreferences> findAll();
	
	UserPreferences findByUser(User user);
	
	List<UserPreferences> findByVisibility(VisibilityEnum visibility);
	
	List<UserPreferences> findByAdvertisement(Boolean advertisement);
}
