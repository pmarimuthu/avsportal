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
import com.avs.portal.bean.UserProfileBean;
import com.avs.portal.repository.UserProfileRepository;
import com.avs.portal.repository.UserRepository;
import com.avs.portal.service.UserProfileService;

@SuppressWarnings("unused")
@RestController
@RequestMapping(path = "/api/user-profile")
public class UserProfileController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserProfileService userProfileService;

	@Autowired
	private UserProfileRepository userProfileRepository;

	@GetMapping("/health")
	public String sayHello() {
		return "UserProfileController is Alive!!";
	}

	@PostMapping("/list")
	public List<UserProfileBean> getAllUserProfiles() {
		return userProfileService.getAllUserProfiles();
	}

	@PostMapping("/get")
	public UserProfileBean getUserProfile(@RequestBody UserBean userBean) {
		return userProfileService.getUserProfile(userBean);
	}

	@PostMapping("/create/{userId}")
	public UserProfileBean createUserProfile(@PathVariable(name = "userId") String userId, @RequestBody UserProfileBean userProfileBean) {
		return userProfileService.createUserProfile(new UserBean().setId(UUID.fromString(userId)), userProfileBean);
	}
	
	@PatchMapping("/update")
	public UserBean updateUserProfile(@RequestBody UserProfileBean userProfileBean) {
		return userProfileService.updateUserProfile(userProfileBean);
	}	

	@DeleteMapping("/delete/{userId}")
	public UserBean deleteUserProfile(@PathVariable(name = "userId") String userId, @RequestBody UserProfileBean userProfileBean) {
		return userProfileService.deleteTempPassword(new UserBean().setId(UUID.fromString(userId)), userProfileBean);
	}

}
