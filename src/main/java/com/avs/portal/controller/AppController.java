package com.avs.portal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/app")
public class AppController {

	@GetMapping(path = "/health")
	public String checkHealth(@RequestHeader(value = "User-Agent") String userAgent) {
		return "Healthy!! App Up & Running.";
	}
	
}
