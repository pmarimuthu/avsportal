package com.avs.portal.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.avs.portal.entity.UserProfile;

public interface UserProfileRepository extends CrudRepository<UserProfile, UUID> {

}
