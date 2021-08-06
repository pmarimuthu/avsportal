package com.avs.portal.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.avs.portal.entity.UserPreferences;

public interface UserPreferencesRepository extends CrudRepository<UserPreferences, UUID> {

}
