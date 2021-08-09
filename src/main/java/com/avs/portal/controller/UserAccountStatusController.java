package com.avs.portal.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avs.portal.bean.UserAccountStatusBean;
import com.avs.portal.entity.User;
import com.avs.portal.entity.UserAccountStatus;
import com.avs.portal.repository.UserAccountStatusRepository;
import com.avs.portal.repository.UserRepository;

@RestController
@RequestMapping(path = "/api/user-accountstatus")
public class UserAccountStatusController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserAccountStatusRepository userAccountStatusRepository;

	@GetMapping("/health")
	public String sayHello() {
		return "UserAccountStatusController is Alive!!";
	}

	@PostMapping("/list")
	public List<UserAccountStatusBean> getAllUsersAccountStatus() {
		return userAccountStatusRepository.findAll().stream().map(UserAccountStatus :: toBean).collect(Collectors.toList());
	}

	@PostMapping("/update")
	public UserAccountStatusBean update(@RequestBody UserAccountStatusBean userAccountStatusBean) {
		if(userAccountStatusBean == null || userAccountStatusBean.getId() == null)
			return null;

		UserAccountStatus userAccountStatus = userAccountStatusRepository.findById(userAccountStatusBean.getId()).orElse(null);
		if(userAccountStatus == null || userAccountStatus.getUser() == null || userAccountStatus.getUser().getId() == null)
			return null;

		User user = userRepository.findById(userAccountStatus.getUser().getId()).orElse(null);
		if(user == null) 
			return null;

		userAccountStatus = user.getUserAccountStatus();

		userAccountStatus.setIsActive(userAccountStatusBean.getIsActive());
		userAccountStatus.setIsBlocked(userAccountStatusBean.getIsBlocked());
		userAccountStatus.setIsDeleted(userAccountStatusBean.getIsDeleted());
		userAccountStatus.setIsLocked(userAccountStatusBean.getIsLocked());
		userAccountStatus.setIsVerified(userAccountStatusBean.getIsVerified());

		userAccountStatus.setUser(user);
		user.setUserAccountStatus(userAccountStatus);

		userAccountStatus.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));

		user = userRepository.save(user); 
		
		return user.getUserAccountStatus().toBean();	
	}

}
