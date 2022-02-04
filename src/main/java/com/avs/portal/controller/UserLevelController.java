package com.avs.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avs.portal.bean.UserBean;
import com.avs.portal.repository.UserRepository;
import com.avs.portal.service.UserService;

@RestController
@RequestMapping(path = "/api/user-level")
public class UserLevelController {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@GetMapping("/health")
	public String sayHello() {
		return "UserLevel is Alive!!";
	}
	
	@PostMapping("/{level}")
	public String getLevel(@PathVariable("level") int level, @RequestBody UserBean userBean) {
		System.err.println("GetLevel for the user: " + userBean.getId());
		if(userBean ==  null || userBean.getId() == null)
			return null;
		
		
		
		return "LEVEL " + level;
	}
}
