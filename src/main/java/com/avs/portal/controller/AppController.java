package com.avs.portal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avs.portal.util.Logger;

@RestController
@RequestMapping(path = "/api/app")
public class AppController {

	@GetMapping(path = "/health")
	public String checkHealth(@RequestHeader(value = "User-Agent") String userAgent) {
		Logger.info("User Agent: " + userAgent);
		return "Healthy!! App Up & Running.";
	}
	
}
