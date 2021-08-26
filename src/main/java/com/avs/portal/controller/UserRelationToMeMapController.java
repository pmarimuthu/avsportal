package com.avs.portal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/user-relation-tome-map")
public class UserRelationToMeMapController {

	@GetMapping("/health")
	public String sayHello() {
		return "UserRelationToMeMapController is Alive!!";
	}
	
}
