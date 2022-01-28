package com.avs.portal.controller;

import java.util.Set;
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
import com.avs.portal.bean.UserVerificationBean;
import com.avs.portal.service.UserVerificationService;

@RestController
@RequestMapping(path = "/api/user-verification")
public class UserVerificationController {

	@Autowired
	private UserVerificationService userVerificationService;

	@GetMapping("/health")
	public String sayHello() {
		return "UserVerificationController is Alive!!";
	}

	@PostMapping("/list")
	public Set<UserVerificationBean> listAllUserVerifications() {
		Set<UserVerificationBean> list = userVerificationService.getAllUsersVerifications();
		return list;
	}

	@PostMapping("/get/{userId}")
	public Set<UserVerificationBean> getUserVerification(@PathVariable(name = "userId") String userId) {
		return userVerificationService.getUserVerifications(new UserBean().setId(UUID.fromString(userId)));
	}

	@PatchMapping("/update/{userId}")
	public UserBean updateUserVerification(@PathVariable(name = "userId") String userId, @RequestBody UserVerificationBean userVerificationBean) {
		return userVerificationService.updateUserVerification(new UserBean().setId(UUID.fromString(userId)), userVerificationBean);
	}

	@DeleteMapping("/delete/{userId}")
	public Set<UserVerificationBean> deleteUserVerification(@PathVariable(name = "userId") String userId, @RequestBody UserVerificationBean userVerificationBean) {
		return userVerificationService.deleteUserVerification(new UserBean().setId(UUID.fromString(userId)), userVerificationBean);
	}

}
