package com.avs.portal.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.avs.portal.entity.User;

public interface UserRepository extends CrudRepository<User, UUID> {

	List<User> findAll();
		
	List<User> findByPhone(Long phone);
	
	List<User> findByEmail(String email);
	
	List<User> findByIdOrEmailAndPhone(UUID id, String email, Long phone);
}
