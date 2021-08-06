package com.avs.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avs.portal.bean.UserBean;
import com.avs.portal.bean.UserCredentialBean;
import com.avs.portal.service.UserCredentialService;
import com.avs.portal.util.CommonUtil;

@RestController
@RequestMapping(path = "/api/usercredential")
public class UserCredentialController {

	@Autowired
	private UserCredentialService userCredentialService;	
	
	@GetMapping("/auth")
	public UserBean onAuthenticate(UserBean userBean, UserCredentialBean userCredentialBean) throws Exception {
		UserBean authenticatedUser = userCredentialService.onAuthenticateUser(userBean, userCredentialBean);
		if(authenticatedUser != null)
			onPostAuthentication(authenticatedUser, Boolean.TRUE);
		System.out.println(CommonUtil.toString(authenticatedUser.getCreatedOn()));
		
		return userBean;
	}

	private void onPostAuthentication(UserBean authenticatedUser, Boolean authenticationFlag) {
		if(authenticationFlag) { // AUTH SUCCESS
			
		}
		else { // AUTH FAILED
			
		}
	}
	
}
