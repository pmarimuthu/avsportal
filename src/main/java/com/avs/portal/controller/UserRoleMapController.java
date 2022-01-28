package com.avs.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avs.portal.bean.UserBean;
import com.avs.portal.bean.UserRoleMapBean;
import com.avs.portal.service.UserRoleMapService;

@RestController
@RequestMapping(path = "/api/user-role-map")
public class UserRoleMapController {

	@Autowired
	private UserRoleMapService userRoleMapService;
	
	@GetMapping("/health")
	public String sayHello() {
		return "UserRoleMapController is Alive!!";
	}

	@PostMapping("/list")
	public List<UserRoleMapBean> listUserRoleMaps() {
		return userRoleMapService.listUserRoleMaps();
	}

	@PostMapping("/get")
	public UserRoleMapBean getUserRoleMap(@RequestBody UserBean userBean) {
		return userRoleMapService.getUserRoleMap(userBean);
	}

	@PostMapping("/create")
	public UserRoleMapBean createUserRoleMap(@RequestBody UserRoleMapBean userRoleMapBean) {
		return userRoleMapService.createUserRoleMap(userRoleMapBean);
	}

	@PatchMapping("/update")
	public UserBean updateUserRoleMapBean(@RequestBody UserRoleMapBean userRoleMapBean) {
		return userRoleMapService.updateUserRoleMap(userRoleMapBean);
	}

	@DeleteMapping("/delete")
	public UserBean deleteUserRoleMap(@RequestBody UserBean userBean) {
		return userRoleMapService.deleteUserRoleMap(userBean);
	}
	
}
