package com.avs.portal.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.avs.portal.entity.User;
import com.avs.portal.entity.UserAddress;
import com.avs.portal.enums.AddressTypeEnum;

public interface UserAddressRepository extends CrudRepository<UserAddress, UUID> {

	List<UserAddress> findAll();
	
	List<UserAddress> findByUser(User user);
	
	List<UserAddress> findByAddressType(AddressTypeEnum addressType);
	
	UserAddress findByUserAndAddressType(User user, AddressTypeEnum addressType);
	
	List<UserAddress> findByCity(String city);
	
	List<UserAddress> findByState(String state);
}
