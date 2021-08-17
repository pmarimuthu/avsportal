package com.avs.portal.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.avs.portal.entity.UserAddress;
import com.avs.portal.enums.AddressTypeEnum;

public interface UserAddressRepository extends CrudRepository<UserAddress, UUID> {

	List<UserAddress> findAll();
	
	List<UserAddress> findByAddressType(AddressTypeEnum addressType);
	
	List<UserAddress> findByCity(String city);
	
	List<UserAddress> findByState(String state);

}
