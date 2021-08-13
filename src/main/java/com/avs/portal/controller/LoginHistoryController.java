package com.avs.portal.controller;

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
import com.avs.portal.repository.UserRepository;

@RestController
@RequestMapping(path = "/api/login-history")
public class LoginHistoryController {

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/health")
	public String sayHello() {
		return "LoginHistoryController is Alive!!";
	}
	
	@PostMapping("/list")
	public List<LoginHistoryBean> getUserLoginHistories(@RequestBody UserBean userBean) {
		if(userBean == null || userBean.getId() == null )
			return null;
		
		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null)
			return null;
		
		return user.getLoginHistories()
				.stream().map(LoginHistory::toBean).collect(Collectors.toList());		
	}

}
