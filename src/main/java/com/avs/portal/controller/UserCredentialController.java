package com.avs.portal.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
		return "UserCredentialController is Alive!!";
	}
	
	@PostMapping("/list")
	public List<UserCredentialBean> listAllUsersCredentials() {
		return userCredentialService.getAllUsersCredentials();
	}
	
	@PostMapping("/get/{userId}")
	public UserCredentialBean getUserCredential(@PathVariable(name = "userId") String userId) {
		return userCredentialService.getUserCredential(new UserBean().setId(UUID.fromString(userId)));
	}
	
	@PostMapping("/create/{userId}")
	public UserCredentialBean createUserCredential(@PathVariable(name = "userId") String userId, @RequestBody UserCredentialBean userCredentialBean) {
		return userCredentialService.createUserCredential(new UserBean().setId(UUID.fromString(userId)), userCredentialBean);
	}

	@PatchMapping("/update")
	public UserBean updateUserCredential(@RequestBody UserCredentialBean userCredentialBean) {
		return userCredentialService.updateUserCredential(userCredentialBean);
	}
	
	@DeleteMapping("/delete/{userId}")
	public UserBean deleteUserCredential(@PathVariable(name = "userId") String userId) {
		return userCredentialService.deleteUserCredential(new UserBean().setId(UUID.fromString(userId)));
	}
	
}
