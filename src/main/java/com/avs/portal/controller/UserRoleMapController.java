package com.avs.portal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/user-role-map")
public class UserRoleMapController {

	@GetMapping("/health")
	public String sayHello() {
		return "UserRoleMapController is Alive!!";
	}
	
}
