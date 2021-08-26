package com.avs.portal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/user-referrer-map")
public class UserReferrerMapController {

	@GetMapping("/health")
	public String sayHello() {
		return "UserReferrerMapController is Alive!!";
	}

}
