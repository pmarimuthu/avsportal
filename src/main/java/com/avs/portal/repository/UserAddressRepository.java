package com.avs.portal.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.avs.portal.entity.UserAddress;

public interface UserAddressRepository extends CrudRepository<UserAddress, UUID> {

}
