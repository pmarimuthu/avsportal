package com.avs.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avs.portal.bean.UserBean;
import com.avs.portal.bean.UserInformationBean;
import com.avs.portal.service.UserInformationService;

@RestController
@RequestMapping(path = "/api/user-information")
public class UserInformationController {

	@Autowired
	private UserInformationService userInformationService;
	
	@GetMapping("/health")
	public String sayHello() {
		return "UserController is Alive!!";
	}
	
	@PostMapping("/list")
	public List<UserInformationBean> listAllUsersInformation() {
		return userInformationService.getAllUsersInformation();
	}
	
	@PostMapping("/get")
	public UserInformationBean getUserInformation(@RequestBody UserBean user) {
		return userInformationService.getUserInformation(user);
	}
	
	@PostMapping("/add")
	public UserInformationBean createUserInformation(@RequestBody UserInformationBean userInformationBean) {
		return userInformationService.createUserInformation(userInformationBean);
	}

	@PostMapping("/update")
	public UserBean updateUserInformation(@RequestBody UserInformationBean userInformationBean) {
		return userInformationService.updateUserInformation(userInformationBean);
	}
	
	@DeleteMapping("/delete")
	public UserBean deleteUserInformation(@RequestBody UserBean userBean) {
		return userInformationService.deleteUserInformation(userBean);
	}
	
	
}
