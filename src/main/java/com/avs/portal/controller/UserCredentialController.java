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

import com.avs.portal.bean.UserBean;
import com.avs.portal.bean.UserCredentialBean;
import com.avs.portal.service.UserCredentialService;

@RestController
@RequestMapping(path = "/api/user-credential")
public class UserCredentialController {

	@Autowired
	private UserCredentialService userCredentialService;
	
	@GetMapping("/health")
	public String sayHello() {
		return "UserCredential is Alive!!";
	}
	
	@PostMapping("/list")
	public List<UserCredentialBean> listAllUsersInformation() {
		return userCredentialService.getAllUsersCredentials();
	}
	
	@PostMapping("/get")
	public UserCredentialBean getUserCredential(@RequestBody UserBean userBean) {
		return userCredentialService.getUserCredential(userBean);
	}
	
	@PostMapping("/create")
	public UserCredentialBean createUserCredential(@RequestBody UserCredentialBean userCredentialBean) {
		return userCredentialService.createUserCredential(userCredentialBean);
	}

	@PutMapping("/update")
	public UserCredentialBean updateUserCredential(@RequestBody UserCredentialBean userCredentialBean) {
		return userCredentialService.updateUserCredential(userCredentialBean);
	}
	
	@DeleteMapping("/delete")
	public UserBean deleteUserCredential(@RequestBody UserCredentialBean userCredentialBean) {
		return userCredentialService.deleteUserCredential(userCredentialBean);
	}
	
}
