package com.avs.portal.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avs.portal.bean.UserVerificationBean;
import com.avs.portal.bean.UserBean;
import com.avs.portal.entity.User;
import com.avs.portal.entity.UserVerification;
import com.avs.portal.repository.UserVerificationRepository;
import com.avs.portal.repository.UserRepository;

@Service
public class UserVerificationService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserVerificationRepository userVerificationRepository;
	
	// READ (ALL)
	public List<UserVerificationBean> getAllUsersVerifications() {
		return userVerificationRepository.findAll().stream().map(UserVerification :: toBean).collect(Collectors.toList());
	}

	// READ (USER's)
	public List<UserVerificationBean> getUserVerifications(UserBean userBean) {
		if(userBean == null || userBean.getId() == null)
			return null;
		
		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null)
			return null;
		
		return user.getUserVerifications().stream().map(UserVerification :: toBean).collect(Collectors.toList());
	}

	// CREATE / UPDATE an Verification
	public List<UserVerificationBean> addOrEditUserVerification(UserVerificationBean bean) {
		if(bean == null || bean.getUserId() == null)
			return null;
		
		User user = userRepository.findById(bean.getUserId()).orElse(null);
		if(user == null)
			return null;
		
		UserVerification userVerification = user.getUserVerifications().stream().filter(entity -> (entity.getVerificationSubject() != null && bean.getVerificationSubject().equals(entity.getVerificationSubject()) )).findFirst().orElse(null);
		if(userVerification == null) {
			userVerification = new UserVerification();
			userVerification.setUser(user);
			userVerification.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
		}
		
		userVerification.setVerificationSubject(bean.getVerificationSubject());
		userVerification.setVerificationMode(bean.getVerificationMode());
		userVerification.setVerifiedBy(bean.getVerifiedBy());
		userVerification.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		user.getUserVerifications().add(userVerification);
		
		user = userRepository.save(user);
		
		return user.getUserVerifications().stream().map(UserVerification :: toBean).collect(Collectors.toList());
	}

	public List<UserVerificationBean> deleteUserVerification(UserVerificationBean bean) {
		if(bean == null || bean.getId() == null)
			return null;
		
		User user = userRepository.findById(bean.getId()).orElse(null);
		if(user == null)
			return null;
		
		UserVerification userVerification = user.getUserVerifications().stream().filter(entity -> entity.getVerificationSubject().equals(bean.getVerificationSubject())).findFirst().orElse(null);
		if(userVerification == null)
			return null;
		
		user.getUserVerifications().remove(userVerification);
		userVerification.setUser(null);
		
		user = userRepository.save(user);
		
		return user.getUserVerifications().stream().map(UserVerification :: toBean).collect(Collectors.toList());
	}

}
