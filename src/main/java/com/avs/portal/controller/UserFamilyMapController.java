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
import com.avs.portal.bean.UserFamilyMapBean;
import com.avs.portal.service.UserFamilyMapService;

@RestController
@RequestMapping(path = "/api/user-family-map")
public class UserFamilyMapController {

	@Autowired
	private UserFamilyMapService userFamilyMapService;

	@GetMapping("/health")
	public String sayHello() {
		return "UserFamilyMapController is Alive!!";
	}

	@PostMapping("/list")
	public List<UserFamilyMapBean> listUserFamilyMaps() {
		return userFamilyMapService.listUserFamilyMaps();
	}

	@PostMapping("/get/{userId}")
	public UserFamilyMapBean getUserFamilyMap(@PathVariable(name = "userId") UUID userId) {
		return userFamilyMapService.getUserFamilyMap(new UserBean().setId(userId));
	}

	@PostMapping("/create/{userId}")
	public UserFamilyMapBean createUserFamilyMap(@PathVariable(name = "userId") UUID userId, @RequestBody UserFamilyMapBean userFamilyMapBean) {
		return userFamilyMapService.createUserFamilyMap(new UserBean().setId(userId), userFamilyMapBean);
	}

	@PutMapping("/edit/{userId}")
	public UserFamilyMapBean editUserFamilyMapBean(@PathVariable(name = "userId") UUID userId, @RequestBody UserFamilyMapBean userFamilyMapBean) {
		return userFamilyMapService.editUserFamilyMap(new UserBean().setId(userId), userFamilyMapBean);
	}

	@DeleteMapping("/delete/{userId}")
	public UserBean deleteUserFamilyMap(@PathVariable(name = "userId") UUID userId, @RequestBody UserFamilyMapBean userFamilyMapBean) {
		return userFamilyMapService.deleteUserFamilyMap(new UserBean().setId(userId), userFamilyMapBean);
	}

}
