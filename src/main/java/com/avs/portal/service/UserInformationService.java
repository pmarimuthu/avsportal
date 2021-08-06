package com.avs.portal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avs.portal.repository.UserInformationRepository;

@Service
public class UserInformationService {

	@Autowired
	private UserInformationRepository userInformationRepository;
	
	@Autowired
	private UserService userService;
	
}
