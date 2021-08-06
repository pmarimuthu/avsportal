package com.avs.portal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avs.portal.repository.UserAccountStatusRepository;

@Service
public class UserAccountStatusService {

	@Autowired
	private UserAccountStatusRepository userAccountStatusRepository;
	
	@Autowired
	private UserService userService;
	
}
