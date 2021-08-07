package com.avs.portal.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avs.portal.bean.TempPasswordBean;
import com.avs.portal.bean.UserBean;
import com.avs.portal.entity.TempPassword;
import com.avs.portal.entity.User;
import com.avs.portal.repository.UserRepository;
import com.avs.portal.util.CommonUtil;

@RestController
@RequestMapping(path = "/api/temp")
public class TempPasswordController {

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/health")
	public String sayHello() {
		return "TempPasswordController is Alive!!";
	}
	
	@PostMapping("/create")
	public TempPasswordBean createTempPassword(@RequestBody UserBean user) {
		if(user == null || user.getId() == null)
			return null;
		
		User userEntity = userRepository.findById(user.getId()).orElse(null);

		if(userEntity == null)
			return null;
		
		TempPassword tempPassword = userEntity.getTempPassword();
		tempPassword.setIsUsed(Boolean.FALSE);
		tempPassword.setGeneratedPassword(CommonUtil.generateTempPassword());
		tempPassword.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
		tempPassword.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		tempPassword.setUser(userEntity);
		userEntity.setTempPassword(tempPassword);
		
		userEntity = userRepository.save(userEntity);
		tempPassword = userEntity.getTempPassword();
		
		return tempPassword.toBean();	
	}
	
}
