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
import com.avs.portal.service.UserService;

@RestController
@RequestMapping(path = "/api/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/health")
	public String sayHello() {
		return "UserController is Alive!!";
	}
	
	@PostMapping("/list")
	public List<UserBean> listUsers() {
		return userService.getUsers();
	}
	
	@PostMapping("/get")
	public UserBean getUser(@RequestBody UserBean user) {
		try {
			return userService.getUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@PostMapping("/add")
	public UserBean addUser(@RequestBody UserBean user) {
			return userService.addUser(user);
	}
	
	@PutMapping("/edit")
	public UserBean editUser(@RequestBody UserBean user) throws Exception {
		return userService.editUser(user);
	}
	
	@DeleteMapping("/delete")
	public UserBean deleteUser(@RequestBody UserBean user) throws Exception {
		return userService.deleteUser(user);
	}
	
	
}
