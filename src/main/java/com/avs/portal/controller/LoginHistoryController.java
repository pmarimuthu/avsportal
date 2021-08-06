package com.avs.portal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/login/history")
public class LoginHistoryController {

	@GetMapping("/health")
	public String sayHello() {
		return "LoginHistoryController is Alive!!";
	}

}
