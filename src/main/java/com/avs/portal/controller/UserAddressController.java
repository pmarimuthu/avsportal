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

import com.avs.portal.bean.UserAddressBean;
import com.avs.portal.bean.UserBean;
import com.avs.portal.service.UserAddressService;

@RestController
@RequestMapping(path = "/api/user-address")
public class UserAddressController {

	@Autowired
	private UserAddressService userAddressService;
	
	@GetMapping("/health")
	public String sayHello() {
		return "UserController is Alive!!";
	}

	@PostMapping("/list")
	public List<UserAddressBean> getAllUserAddresses() {
		return  userAddressService.getAllUsersAddresses();
	}

	@PostMapping("/get")
	public List<UserAddressBean> getUserAddresses(@RequestBody UserBean userBean) {
		return userAddressService.getUserAddresses(userBean);
	}
	
	@PostMapping("/create/{userId}")
	public List<UserAddressBean> createUserAddress(@PathVariable(name = "userId") String userId, @RequestBody UserAddressBean userAddressBean) {
		return userAddressService.createUserAddress(new UserBean().setId(UUID.fromString(userId)), userAddressBean);
	}
	
	@PatchMapping("/patch/{userId}")
	public UserBean patchUserAddress(@PathVariable(name = "userId") String userId, @RequestBody UserAddressBean userAddressBean) {
		return userAddressService.updateUserAddress(new UserBean().setId(UUID.fromString(userId)), userAddressBean);
	}

	@PatchMapping("/attach/{userId}")
	public List<UserAddressBean> attachUserWithUserAddress(@PathVariable(name = "userId") String userId, @RequestBody UserAddressBean userAddressBean) {
		return userAddressService.attachUserWithUserAddress(new UserBean().setId(UUID.fromString(userId)), userAddressBean);
	}	

	@PatchMapping("/detach/{userId}")
	public List<UserAddressBean> detachUserFromUserAddress(@PathVariable(name = "userId") String userId, @RequestBody UserAddressBean userAddressBean) {
		return userAddressService.detachUserFromUserAddress(new UserBean().setId(UUID.fromString(userId)), userAddressBean);
	}
	
	@PatchMapping("/update/{userId}")
	public UserBean updateUserAddress(@PathVariable(name = "userId") String userId, @RequestBody UserAddressBean userAddressBean) {
		return userAddressService.updateUserAddress(new UserBean().setId(UUID.fromString(userId)), userAddressBean);
	}
	
	@DeleteMapping("/remove/{userId}")
	public UserBean removeUserAddress(@PathVariable(name = "userId") String userId, @RequestBody UserAddressBean userAddressBean) {
		return userAddressService.removeUserAddress(new UserBean().setId(UUID.fromString(userId)), userAddressBean);
	}

}
