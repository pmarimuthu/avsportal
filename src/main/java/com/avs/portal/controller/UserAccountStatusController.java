package com.avs.portal.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avs.portal.bean.UserAccountStatusBean;
import com.avs.portal.entity.User;
import com.avs.portal.entity.UserAccountStatus;
import com.avs.portal.repository.UserRepository;

@RestController
@RequestMapping(path = "/api/useraccountstatus")
public class UserAccountStatusController {

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/health")
	public String sayHello() {
		return "UserAccountStatusController is Alive!!";
	}
	
	@PostMapping("/update")
	public UserAccountStatusBean update(@RequestBody UserAccountStatusBean bean) {
		if(bean == null || bean.getId() == null)
			return null;
		
		User userEntity = userRepository.findById(bean.getId()).orElse(null);

		if(userEntity == null)
			return null;
		
		UserAccountStatus userAccountStatus = userEntity.getUserAccountStatus();
		userAccountStatus.setIsActive(bean.getIsActive());
		userAccountStatus.setIsBlocked(bean.getIsBlocked());
		userAccountStatus.setIsDeleted(bean.getIsDeleted());
		userAccountStatus.setIsLocked(bean.getIsLocked());
		userAccountStatus.setIsVerified(bean.getIsVerified());
		
		userAccountStatus.setUser(userEntity);
		userEntity.setUserAccountStatus(userAccountStatus);
		
		userAccountStatus.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		userEntity = userRepository.save(userEntity);
		userAccountStatus = userEntity.getUserAccountStatus();
		
		return userAccountStatus.toBean();	
	}
	
}
