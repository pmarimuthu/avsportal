package com.avs.portal.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.avs.portal.bean.LoginBean;
import com.avs.portal.bean.UserBean;
import com.avs.portal.enums.UserAgentEnum;
import com.avs.portal.service.AuthService;

@RestController
@RequestMapping(path = "/api/auth")
public class AuthController {

	@Autowired
	private AuthService authService;
	
	@GetMapping("/health")
	public String sayHello() {
		return "AuthController is Alive!!";
	}
	
	@PostMapping("/login")
	public UserBean attemptLogin(@RequestBody LoginBean loginBean) {
		return authService.attemptLogin(loginBean, getIPAddress(), getUserAgent());
	}

	private String getIPAddress() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
		        .getRequest();
		
		String remoteAddress = "Unknown!!";
		
        if (request != null) {
        	remoteAddress = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddress == null || "".equals(remoteAddress)) {
            	remoteAddress = request.getRemoteAddr();
            }
        }

        return remoteAddress;
	}
	
	private UserAgentEnum getUserAgent() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
		        .getRequest();
		
		String userAgent = "Unknown!!";
		
        if (request != null) {
        	userAgent = request.getHeader("User-Agent");
            if (userAgent == null || "".equals(userAgent)) {
            	userAgent = request.getRemoteAddr();
            }
        }

        return UserAgentEnum.OTHER;
	}
	
}
