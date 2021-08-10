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
	public List<UserAddressBean> listAllUserAddresses() {
		List<UserAddressBean> list = userAddressService.getAllUsersAddresses();
		return list;
	}

	@PostMapping("/get")
	public List<UserAddressBean> getUserAddress(@RequestBody UserBean user) {
		try {
			return userAddressService.getUserAddresses(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}

	@PostMapping("/update")
	public List<UserAddressBean> addOrEditUser(@RequestBody UserAddressBean userAddress) {
		return userAddressService.addOrEditUserAddress(userAddress);
	}

	@DeleteMapping("/delete")
	public List<UserAddressBean> deleteUserAddress(@RequestBody UserAddressBean userAddress) {
		return userAddressService.deleteUserAddress(userAddress);
	}

}
