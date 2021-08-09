package com.avs.portal.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
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
	public List<UserVerificationBean> getAllUsersVerification() {
		return userVerificationRepository.findAll().stream().map(UserVerification :: toBean).collect(Collectors.toList());
	}

	// READ (USER's)
	public UserVerificationBean getUserVerification(UserBean user) {
		if(user == null || user.getId() == null)
			return null;

		User userEntity = userRepository.findById(user.getId()).orElse(null);
		if(userEntity == null)
			return null;

		if(userEntity.getUserVerification() == null)
			return null;

		return userEntity.getUserVerification().toBean();
	}

	// CREATE / UPDATE an UserVerification
	public UserVerificationBean updateUserVerification(UserVerificationBean bean) {
		if(bean == null || bean.getUserId() == null)
			return null;

		User userEntity = userRepository.findById(bean.getUserId()).orElse(null);
		if(userEntity == null)
			return null;

		UserVerification userVerification = userEntity.getUserVerification();

		if(userVerification == null) {
			userVerification = new UserVerification();
			userVerification.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
			userEntity.setUserVerification(userVerification);
		}

		userVerification.setUser(userEntity);
		userVerification.setVerificationMode(bean.getVerificationMode());
		userVerification.setVerificationSubject(bean.getVerificationSubject());
		userVerification.setVerifiedBy(bean.getVerifiedBy());
		userVerification.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));

		userVerification = userVerificationRepository.save(userVerification);

		return userVerification.toBean();
	}

	public UserBean deleteUserVerificationBean(UserBean userBean) {
		if(userBean == null || userBean.getId() == null)
			return null;

		User userEntity = userRepository.findById(userBean.getId()).orElse(null);
		if(userEntity == null)
			return null;

		UserVerification userVerification = userEntity.getUserVerification();
		if(userVerification != null)
			userVerification.setUser(null);

		userEntity.setUserVerification(null);
		
		return userBean;
	}

}
