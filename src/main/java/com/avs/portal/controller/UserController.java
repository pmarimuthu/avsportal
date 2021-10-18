package com.avs.portal.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
		try {
			return userService.getUser(new UserBean().setId(UUID.fromString(userId)));
		} catch (Exception e) {
			throw new ResponseStatusException(200, "Unable to find User: " + userId, e);
		}
	}

	@PostMapping("/create")
	public UserBean createUser(@RequestBody UserBean userBean) {
		try {
			return userService.createUser(userBean);
		} catch (Exception e) {
			userBean.setHasError(true);
			userBean.getCustomErrorMessages().add("Email/Phone already exists.");
		}
		
		return userBean;
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
