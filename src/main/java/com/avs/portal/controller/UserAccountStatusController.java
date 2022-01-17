package com.avs.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	@PostMapping("/get")
	public UserAccountStatusBean getUserAccountStatus(@RequestBody UserAccountStatusBean userAccountStatusBean) {
		return userAccountStatusService.getUserAccountStatus(userAccountStatusBean);
	}

	@PostMapping("/create")
	public UserBean createAccountStatus(@RequestBody UserAccountStatusBean userAccountStatusBean) {
		return userAccountStatusService.createAccountStatus(userAccountStatusBean);
	}

	@PutMapping("/edit")
	public UserAccountStatusBean updateAccountStatus(@RequestBody UserAccountStatusBean userAccountStatusBean) {
		return userAccountStatusService.updateAccountStatus(userAccountStatusBean);
	}

	@DeleteMapping("/delete")
	public UserBean deleteAccountStatus(@RequestBody UserAccountStatusBean userAccountStatusBean) {
		return userAccountStatusService.deleteAccountStatus(userAccountStatusBean);
	}

}
