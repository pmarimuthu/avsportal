package com.avs.portal.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.avs.portal.entity.TempPassword;
import com.avs.portal.entity.User;

public interface TempPasswordRepository extends CrudRepository<TempPassword, UUID> {

	List<TempPassword> findAll();
	
	TempPassword findByUser(User user);
}
