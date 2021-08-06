package com.avs.portal.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.avs.portal.entity.TempPassword;

public interface TempPasswordRepository extends CrudRepository<TempPassword, UUID> {

}
