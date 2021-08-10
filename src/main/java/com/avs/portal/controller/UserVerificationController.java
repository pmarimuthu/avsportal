package com.avs.portal.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
	public List<UserVerificationBean> listAllUserVerifications() {
		List<UserVerificationBean> list = userVerificationService.getAllUsersVerifications();
		return list;
	}

	@PostMapping("/get")
	public List<UserVerificationBean> getUserVerification(@RequestBody UserBean user) {
		try {
			return userVerificationService.getUserVerifications(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}

	@PostMapping("/update")
	public List<UserVerificationBean> addOrEditUser(@RequestBody UserVerificationBean userVerification) {
		return userVerificationService.addOrEditUserVerification(userVerification);
	}

	@DeleteMapping("/delete")
	public List<UserVerificationBean> deleteUserVerification(@RequestBody UserVerificationBean userVerification) {
		return userVerificationService.deleteUserVerification(userVerification);
	}

}
