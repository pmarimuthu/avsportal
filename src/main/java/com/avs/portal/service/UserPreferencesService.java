package com.avs.portal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avs.portal.repository.UserPreferencesRepository;

@Service
public class UserPreferencesService {

	@Autowired
	private UserPreferencesRepository userPreferencesRepository;
	
	@Autowired
	private UserService userService;
	
}
