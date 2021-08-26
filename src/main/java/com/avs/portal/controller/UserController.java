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

	@PostMapping("/get/{userId}")
	public UserBean getUser(@PathVariable(name = "userId") String userId) {
		return userService.getUser(new UserBean().setId(UUID.fromString(userId)));
	}

	@PostMapping("/create")
	public UserBean createUser(@RequestBody UserBean user) {
		return userService.createUser(user);
	}

	@PutMapping("/edit")
	public UserBean editUser(@RequestBody UserBean user) {
		return userService.editUser(user);
	}

	@DeleteMapping("/delete/{userId}")
	public UserBean deleteUser(@PathVariable(name = "userId") String userId) {
		return userService.deleteUser(new UserBean().setId(UUID.fromString(userId)));
	}

}
