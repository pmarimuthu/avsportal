package com.avs.portal.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avs.portal.bean.LoginHistoryBean;
import com.avs.portal.entity.LoginHistory;
import com.avs.portal.entity.User;
import com.avs.portal.repository.LoginHistoryRepository;
import com.avs.portal.repository.UserRepository;

@Service
public class LoginHistoryService {

	@Autowired
	private LoginHistoryRepository loginHistoryRepository;
	
	@Autowired
	private UserRepository userRepository;

	public List<LoginHistoryBean> getAllUsersLoginHistories() {
		return loginHistoryRepository.findAll().stream().map(LoginHistory :: toBean).collect(Collectors.toList());
	}

	public List<LoginHistoryBean> getUserLoginHistories(UUID userId) {
		if(userId == null)
			return null;

		User user = userRepository.findById(userId).orElse(null);
		if(user == null)
			return null;
		
		return loginHistoryRepository.findByUser(user).stream().map(LoginHistory :: toBean).collect(Collectors.toList());
	}

}
