package com.avs.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avs.portal.bean.UserBean;
import com.avs.portal.bean.UserPreferencesBean;
import com.avs.portal.service.UserPreferencesService;

@RestController
@RequestMapping(path = "/api/user-preferences")
public class UserPreferencesController {

	@Autowired
	private UserPreferencesService userPreferencesService;
	
	@GetMapping("/health")
	public String sayHello() {
		return "UserPreferences is Alive!!";
	}
	
	@PostMapping("/list")
	public List<UserPreferencesBean> listAllUsersuserPreferencesService() {
		return userPreferencesService.getAllUsersPreferences();
	}
	
	@PostMapping("/get")
	public UserPreferencesBean getUserPreferences(@RequestBody UserBean userBean) {
		return userPreferencesService.getUserPreferences(userBean);
	}
	
	@PostMapping("/create")
	public UserPreferencesBean createUserPreferences(@RequestBody UserPreferencesBean userPreferencesBean) {
		return userPreferencesService.createUserPreferences(userPreferencesBean);
	}

	@PutMapping("/update")
	public UserPreferencesBean updateUserPreferences(@RequestBody UserPreferencesBean userPreferencesBean) {
		return userPreferencesService.updateUserPreferences(userPreferencesBean);
	}
	
	@DeleteMapping("/delete")
	public UserBean deleteUserPreferences(@RequestBody UserBean userBean) {
		return userPreferencesService.deleteUserPreferences(userBean);
	}
	
}
