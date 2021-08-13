package com.avs.portal.repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.avs.portal.entity.User;
import com.avs.portal.entity.UserInformation;

public interface UserInformationRepository extends CrudRepository<UserInformation, UUID> {

	List<UserInformation> findAll();
	
	UserInformation findByUser(User user);
	
	List<UserInformation> findByFirstname(String firstname);
	
	List<UserInformation> findByLastname(String lastname);
	
	List<UserInformation> findByFirstnameOrLastname(String firstname, String lastname);
	
	List<UserInformation> findByDateOfBirth(Date dateOfBirth);
	
	List<UserInformation> findByProfession(String profession);
}
