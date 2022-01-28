package com.avs.portal.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avs.portal.bean.UserBean;
import com.avs.portal.bean.UserVerificationBean;
import com.avs.portal.entity.User;
import com.avs.portal.entity.UserVerification;
import com.avs.portal.repository.UserRepository;
import com.avs.portal.repository.UserVerificationRepository;

@Service
public class UserVerificationService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserVerificationRepository userVerificationRepository;
	
	// READ (ALL)
	public Set<UserVerificationBean> getAllUsersVerifications() {
		return userVerificationRepository.findAll().stream().map(UserVerification :: toBean).collect(Collectors.toSet());
	}

	// READ (USER's)
	public Set<UserVerificationBean> getUserVerifications(UserBean userBean) {
		if(userBean == null || userBean.getId() == null)
			return null;
		
		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null)
			return null;
		
		return user.getUserVerifications().stream().map(UserVerification :: toBean).collect(Collectors.toSet());
	}

	// CREATE / UPDATE an Verification
	public UserBean updateUserVerification(UserBean userBean, UserVerificationBean userVerificationBean) {
		if(userBean == null || userBean.getId() == null || userVerificationBean == null || userVerificationBean.getVerificationSubject() == null)
			return null;
		
		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null)
			return null;
		
		UserVerification userSubjectVerification = user.getUserVerifications()
				.stream()
				.filter(verification -> (verification.getVerificationSubject().equals(userVerificationBean.getVerificationSubject())))
				.findFirst()
				.orElse(null);
		
		if(userSubjectVerification == null)
			return null;
		
		userSubjectVerification.setVerificationMode(userVerificationBean.getVerificationMode());
		userSubjectVerification.setVerifiedBy(userVerificationBean.getVerifiedBy());
		userSubjectVerification.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		user = userRepository.save(user);
		
		return user.toBean();
	}

	public Set<UserVerificationBean> deleteUserVerification(UserBean userBean, UserVerificationBean userVerificationBean) {
		if(userBean == null || userBean.getId() == null || userVerificationBean == null || userVerificationBean.getVerificationSubject() == null)
			return null;
		
		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null)
			return null;
		
		UserVerification userVerification = user.getUserVerifications()
				.stream()
				.filter(verification -> verification.getVerificationSubject().equals(userVerificationBean.getVerificationSubject()))
				.findFirst().orElse(null);
		
		if(userVerification == null)
			return null;
		
		user.getUserVerifications().remove(userVerification);
		userVerification.setUser(null);
		
		user = userRepository.save(user);
		
		return user.getUserVerifications().stream().map(UserVerification :: toBean).collect(Collectors.toSet());
	}

}
