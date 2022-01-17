package com.avs.portal.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avs.portal.bean.UserBean;
import com.avs.portal.bean.UserReferralBean;
import com.avs.portal.service.UserReferralService;

@RestController
@RequestMapping(path = "/api/user-referral")
public class UserReferralController {

	@Autowired
	private UserReferralService userReferralService;

	@GetMapping("/health")
	public String sayHello() {
		return "UserReferralController is Alive!!";
	}

	@PostMapping("/list")
	public List<UserReferralBean> listUserReferrals() {
		return userReferralService.getUserReferrals();
	}

	@PostMapping("/get/{userId}")
	public List<UserReferralBean> getUserReferrals(@PathVariable(name = "userId") String userId) {
		return userReferralService.getUserReferrals(new UserBean().setId(UUID.fromString(userId)));
	}

	@PostMapping("/create/{userId}")
	public UserReferralBean createUserReferral(@PathVariable(name = "userId") String userId) {
		return userReferralService.createUserReferral(new UserBean().setId(UUID.fromString(userId)));
	}

	@PutMapping("/edit")
	public UserReferralBean editUserReferral(@RequestBody UserReferralBean userReferralBean) {
		return userReferralService.editUserReferral(userReferralBean);
	}

	@DeleteMapping("/delete/{userId}")
	public UserReferralBean deleteUserReferral(@PathVariable(name = "userId") String userId, @RequestBody UserReferralBean userReferralBean) {
		return userReferralService.deleteUserReferral(new UserBean().setId(UUID.fromString(userId)), userReferralBean);
	}
	
	// --------- Business APIs --------- 

	@PostMapping("/activate/{referralCode}")
	public UserReferralBean activateReferee(@PathVariable(name = "referralCode") String referralCode) {
		return userReferralService.activateReferee(new UserReferralBean().setReferralCode(referralCode));
	}
	
	@PostMapping("/join/{referralCode}")
	public UserBean joinUser(@PathVariable(name = "referralCode") String referralCode, @RequestBody UserBean userBean) {
		UserBean theUserBean = userReferralService.joinUser(new UserReferralBean().setReferralCode(referralCode), userBean);
		return theUserBean;
	}

}
