package com.avs.portal.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avs.portal.bean.UserBean;
import com.avs.portal.bean.UserVerificationBean;
import com.avs.portal.entity.User;
import com.avs.portal.entity.UserVerification;
import com.avs.portal.repository.UserRepository;
import com.avs.portal.repository.UserVerificationRepository;

@RestController
@RequestMapping(path = "/api/user-verification")
public class UserVerificationController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserVerificationRepository userVerificationRepository;

	@GetMapping("/health")
	public String sayHello() {
		return "UserVerificationController is Alive!!";
	}

	@PostMapping("/list")
	public List<UserVerificationBean> listAllUserVerifications() {
		return userVerificationRepository.findAll().stream().map(UserVerification::toBean).collect(Collectors.toList());
	}

	@PostMapping("/get")
	public UserVerificationBean getUserVerification(@RequestBody UserBean user) {
		if(user == null || user.getId() == null)
			return null;
		
		User userEntity = userRepository.findById(user.getId()).orElse(null);
		if(userEntity == null || userEntity.getUserVerification() == null)
			return null;
		
		return userEntity.getUserVerification().toBean();		
	}

	@PostMapping("/update")
	public UserVerificationBean createOrUpdateUserVerification(@RequestBody UserVerificationBean userVerificationBean) {
		if(userVerificationBean == null || userVerificationBean.getUserId() == null 
				|| userVerificationBean.getVerifiedBy() == null)
			return null;
		
		User user = userRepository.findById(userVerificationBean.getUserId()).orElse(null);
		if(user == null)
			return null;
		
		User verifiedBy = userRepository.findById(userVerificationBean.getVerifiedBy()).orElse(null);
		if(verifiedBy == null || user.getId().equals(verifiedBy.getId())) {
			System.err.println("Invalid VerifiedBy");
			return null;
		}
		
		UserVerification userVerification = user.getUserVerification();
		if(userVerification == null) {
			userVerification = new UserVerification();
			userVerification.setUser(user);
			userVerification.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
		}
		
		userVerification.setVerifiedBy(UUID.randomUUID()); // TODO
		
		userVerification.setVerificationMode(userVerificationBean.getVerificationMode());
		userVerification.setVerificationSubject(userVerificationBean.getVerificationSubject());
		userVerification.setVerifiedBy(userVerificationBean.getVerifiedBy());
		userVerification.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));

		userVerification.setUser(user);
		userVerification = userVerificationRepository.save(userVerification);
		
		return userVerification.toBean();
	}

	@DeleteMapping("/delete")
	public UserBean deleteUserVerification(@RequestBody UserBean userBean) {
		if(userBean == null || userBean.getId() == null)
			return null;
		
		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null)
			return null;
		
		user.setUserVerification(null);
		userRepository.save(user);
		
		return user.toBean();
	}

}
