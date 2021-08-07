package com.avs.portal.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avs.portal.bean.LoginHistoryBean;
import com.avs.portal.bean.UserBean;
import com.avs.portal.entity.LoginHistory;
import com.avs.portal.entity.User;
import com.avs.portal.repository.LoginHistoryRepository;
import com.avs.portal.repository.UserRepository;

@RestController
@RequestMapping(path = "/api/loginhistory")
public class LoginHistoryController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private LoginHistoryRepository loginHistoryRepository;
	
	@GetMapping("/health")
	public String sayHello() {
		return "LoginHistoryController is Alive!!";
	}
	
	@PostMapping("/get")
	public List<LoginHistoryBean> getUserHistories(@RequestBody UserBean bean) {
		if(bean == null || bean.getId() == null )
			return null;
		
		User user = userRepository.findById(bean.getId()).orElse(null);
		if(user == null)
			return null;
		
		return user.getLoginHistories()
				.stream().map(LoginHistory::toBean).collect(Collectors.toList());		
	}
	
	@PostMapping("/add")
	public List<LoginHistoryBean> addUserHistory(@RequestBody LoginHistoryBean bean) {
		if(bean == null || bean.getUserId() == null)
			return null;
		
		User user = userRepository.findById(bean.getUserId()).orElse(null);
		if(user == null)
			return null;
		
		LoginHistory loginHistory = new LoginHistory()
				.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()))
				.setDeviceType(bean.getDeviceType())
				.setIpAddress(bean.getIpAddress())
				.setUserAgent(bean.getUserAgent())
				.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		List<LoginHistory> loginHistories = user.getLoginHistories();
		loginHistories.add(loginHistory);
		loginHistory.setUser(user);
		
		loginHistory = loginHistoryRepository.save(loginHistory);
		
		List<LoginHistoryBean> beans = loginHistory.getUser()
			.getLoginHistories()
			.stream().map(LoginHistory::toBean).collect(Collectors.toList());
		
		System.out.println(beans.toString());
		
		return beans.stream()
			.sorted(Comparator.comparing(LoginHistoryBean :: getUpdatedOn).reversed())
			.collect(Collectors.toList());
	}
}
