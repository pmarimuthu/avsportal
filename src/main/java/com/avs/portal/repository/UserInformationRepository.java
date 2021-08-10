package com.avs.portal.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.avs.portal.entity.UserInformation;

public interface UserInformationRepository extends CrudRepository<UserInformation, UUID> {

	List<UserInformation> findAll();
}
