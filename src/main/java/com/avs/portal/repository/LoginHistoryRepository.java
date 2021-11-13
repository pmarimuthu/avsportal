package com.avs.portal.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.avs.portal.entity.LoginHistory;
import com.avs.portal.entity.User;

public interface LoginHistoryRepository extends CrudRepository<LoginHistory, UUID> {

	List<LoginHistory> findAll();
	
	List<LoginHistory> findByUser(User user);
	
	List<LoginHistory> findFirst10ByUserOrderByUpdatedOnDesc(User user);
	
}
