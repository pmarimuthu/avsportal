package com.avs.portal.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avs.portal.bean.UserAccountStatusBean;
import com.avs.portal.bean.UserBean;
import com.avs.portal.service.UserAccountStatusService;

@RestController
@RequestMapping(path = "/api/user-account-status")
public class UserAccountStatusController {

	@Autowired
	private UserAccountStatusService userAccountStatusService;

	@GetMapping("/health")
	public String sayHello() {
		return "UserAccountStatusController is Alive!!";
	}

	@PostMapping("/list")
	public List<UserAccountStatusBean> getAllUsersAccountStatus() {
		return userAccountStatusService.getAllUsersAccountStatus();
	}
	
	@PostMapping("/get/{userId")
	public UserAccountStatusBean getUserAccountStatus(@PathVariable(name = "userId") String userId) {
		return userAccountStatusService.getUserAccountStatus(new UserBean().setId(UUID.fromString(userId)));
	}

	@PutMapping("/create/{userId}")
	public UserBean createAccountStatus(@PathVariable(name = "userId") String userId, @RequestBody UserAccountStatusBean userAccountStatusBean) {
		return userAccountStatusService.createAccountStatus(new UserBean().setId(UUID.fromString(userId)), userAccountStatusBean);
	}

	@PutMapping("/edit/{userId}")
	public UserAccountStatusBean updateAccountStatus(@PathVariable(name = "userId") String userId, @RequestBody UserAccountStatusBean userAccountStatusBean) {
		return userAccountStatusService.updateAccountStatus(new UserBean().setId(UUID.fromString(userId)), userAccountStatusBean);
	}

	@PutMapping("/delete/{userId}")
	public UserBean deleteAccountStatus(@PathVariable(name = "userId") String userId, @RequestBody UserAccountStatusBean userAccountStatusBean) {
		return userAccountStatusService.deleteAccountStatus(new UserBean().setId(UUID.fromString(userId)), userAccountStatusBean);
	}

}
