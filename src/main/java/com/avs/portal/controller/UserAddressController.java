package com.avs.portal.controller;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avs.portal.bean.UserAddressBean;
import com.avs.portal.bean.UserBean;
import com.avs.portal.service.UserAddressService;
import com.avs.portal.service.UserService;

@RestController
@RequestMapping(path = "/api/user-address")
public class UserAddressController {

	@Autowired
	private UserAddressService userAddressService;
	
	@Autowired
	private UserService userService;

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

	@PostMapping("/update/{userId}")
	public List<UserAddressBean> addOrEditUser(@PathVariable(name = "userId") String userId, @RequestBody UserAddressBean userAddressBean) {
		if(userId == null || userAddressBean == null || userAddressBean.getAddressType() == null)
			return Collections.emptyList();
		
		UserBean userBean = null;
		try {
			userBean = userService.getUser(new UserBean().setId(UUID.fromString(userId)));
		} catch (Exception e) {
			return Collections.emptyList();
		}
		
		return userAddressService.addOrEditUserAddress(userBean, userAddressBean);
	}

	@DeleteMapping("/delete/{userId}")
	public List<UserAddressBean> deleteUserAddress(@PathVariable(name = "userId") String userId, @RequestBody UserAddressBean userAddressBean) {
		if(userId == null || userAddressBean == null)
			return Collections.emptyList();
		
		UserBean userBean = null;
		try {
			userBean = userService.getUser(new UserBean().setId(UUID.fromString(userId)));
		} catch (Exception e) {
			return Collections.emptyList();
		}
		
		return userAddressService.deleteUserAddress(userBean, userAddressBean);
	}

}
