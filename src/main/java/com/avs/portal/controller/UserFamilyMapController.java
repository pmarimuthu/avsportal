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

	@PostMapping("/get")
	public UserFamilyMapBean getUserFamilyMap(@RequestBody UserBean userBean) {
		return userFamilyMapService.getUserFamilyMap(userBean);
	}

	@PostMapping("/create")
	public UserFamilyMapBean createUserFamilyMap(@RequestBody UserFamilyMapBean userFamilyMapBean) {
		return userFamilyMapService.createUserFamilyMap(userFamilyMapBean);
	}

	@PatchMapping("/update")
	public UserBean updateUserFamilyMapBean(@RequestBody UserFamilyMapBean userFamilyMapBean) {
		return userFamilyMapService.updateUserFamilyMap(userFamilyMapBean);
	}

	@DeleteMapping("/delete")
	public UserBean deleteUserFamilyMap(@RequestBody UserBean userBean) {
		return userFamilyMapService.deleteUserFamilyMap(userBean);
	}
	
	@PostMapping("/distinct-family-heads-info")
	public List<String[]> getDistinctFamilyHeadsInfo() {
		return userFamilyMapService.listDistinctFamilyHeadsInfo();
	}
	
	@PostMapping("/distinct-parent-family-heads-info")
	public List<String[]> getDistinctParentFamilyHeadsInfo() {
		return userFamilyMapService.listDistinctParentFamilyHeadsInfo();
	}

}
