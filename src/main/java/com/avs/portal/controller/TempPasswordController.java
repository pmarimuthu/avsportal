package com.avs.portal.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avs.portal.bean.TempPasswordBean;
import com.avs.portal.bean.UserBean;
import com.avs.portal.entity.TempPassword;
import com.avs.portal.entity.User;
import com.avs.portal.repository.TempPasswordRepository;
import com.avs.portal.repository.UserRepository;
import com.avs.portal.util.CommonUtil;

@RestController
@RequestMapping(path = "/api/temp-password")
public class TempPasswordController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TempPasswordRepository tempPasswordRepository;
	
	@GetMapping("/health")
	public String sayHello() {
		return "TempPasswordController is Alive!!";
	}
	
	@PostMapping("/update")
	public TempPasswordBean createOrUpdateTempPassword(@RequestBody UserBean userBean) {
		if(userBean == null || userBean.getId() == null)
			return null;
		
		User user = userRepository.findById(userBean.getId()).orElse(null);

		if(user == null)
			return null;
		
		TempPassword tempPassword = user.getTempPassword();
		if(tempPassword == null) {
			tempPassword = new TempPassword();
			tempPassword.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
		}
		
		tempPassword.setIsUsed(Boolean.FALSE);
		tempPassword.setGeneratedPassword(CommonUtil.generateTempPassword());
		tempPassword.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		tempPassword.setUser(user);
		user = userRepository.save(user); // save PARENT only	
		
		return tempPassword.toBean();	
	}
	
	@DeleteMapping("/delete")
	public UserBean deleteTempPassword(@RequestBody UserBean userBean) {
		if(userBean == null || userBean.getId() == null)
			return null;
		
		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null)
			return null;
		
		TempPassword tempPassword = user.getTempPassword();
		if(tempPassword != null)
			tempPassword.setUser(null);
		
		user.setTempPassword(null);
		user = userRepository.save(user);
		return user.toBean();
	}
	
}
