package com.avs.portal.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avs.portal.bean.LoginHistoryBean;
import com.avs.portal.bean.UserBean;
import com.avs.portal.service.LoginHistoryService;

@RestController
@RequestMapping(path = "/api/login-history")
public class LoginHistoryController {

	@Autowired
	private LoginHistoryService loginHistoryService;
	
	@GetMapping("/health")
	public String sayHello() {
		return "LoginHistoryController is Alive!!";
	}
	
	@GetMapping("/list")
	public List<LoginHistoryBean> getAllUsersLoginHistories() {
		return loginHistoryService.getAllUsersLoginHistories();
	}
	
	@PostMapping("/get/{userId}")
	public List<LoginHistoryBean> getUserLoginHistories(@PathVariable(name = "userId") String userId) {
		return loginHistoryService.getUserLoginHistories(new UserBean().setId(UUID.fromString(userId)));
	}

}
