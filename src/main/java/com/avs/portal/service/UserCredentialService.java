package com.avs.portal.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avs.portal.bean.UserBean;
import com.avs.portal.bean.UserCredentialBean;
import com.avs.portal.entity.User;
import com.avs.portal.entity.UserCredential;
import com.avs.portal.repository.UserCredentialRepository;
import com.avs.portal.repository.UserRepository;
import com.avs.portal.util.CommonUtil;

@Service
public class UserCredentialService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserCredentialRepository userCredentialRepository;
	
	// READ {ALL}
	public List<UserCredentialBean> getAllUsersCredentials() {
		return userCredentialRepository.findAll().stream().map(UserCredential :: toBean).collect(Collectors.toList());
	}

	// READ {ONE}
	public UserCredentialBean getUserCredential(UserBean userBean) {
		if(userBean == null || userBean.getId() == null)
			return null;
		
		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null || user.getUserCredential() == null)
			return null;
		
		return user.getUserCredential().toBean();
	}

	// CREATE
	public UserCredentialBean createUserCredential(UserCredentialBean userCredentialBean) {
		if(userCredentialBean == null || userCredentialBean.getUserId() == null)
			return null;

		User user = userRepository.findById(userCredentialBean.getUserId()).orElse(null);
		if(user == null)
			return null;

		UserCredential userCredential = user.getUserCredential();
		if(userCredential != null) {
			System.err.println("User Credential exists !! for the User: " + userCredential.getUser().getId());
			return null;
		}

		if(CommonUtil.getValidatedPassword(userCredentialBean.getPassword()) == null) {
			System.err.println("Password pattern invalid: " + CommonUtil.PASSWORD_REGEX);
			return null;
		}

		userCredential = new UserCredential();

		userCredential.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()))
			.setPassword(CommonUtil.getValidatedPassword(userCredentialBean.getPassword()))
			.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		userCredential.setUser(user);
		user.setUserCredential(userCredential);

		userCredential = userCredentialRepository.save(userCredential);

		return userCredential.toBean();
	}

	// UPDATE
	public UserCredentialBean updateUserCredential(UserCredentialBean userCredentialBean) {
		if(userCredentialBean == null || userCredentialBean.getUserId() == null)
			return null;

		User user = userRepository.findById(userCredentialBean.getUserId()).orElse(null);
		if(user == null)
			return null;

		UserCredential userCredential = user.getUserCredential();
		if(userCredential == null) {
			System.err.println("User Credential does not exists !! for the User: " + user.getId());
			return null;
		}

		if(CommonUtil.getValidatedPassword(userCredentialBean.getPassword()) == null) {
			System.err.println("Password pattern invalid: " + CommonUtil.PASSWORD_REGEX);
			return null;
		}

		userCredential = new UserCredential();

		userCredential.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()))
			.setPassword(CommonUtil.getValidatedPassword(userCredentialBean.getPassword()))
			.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));

		userCredential.setUser(user);
		user.setUserCredential(userCredential);

		userCredential = userCredentialRepository.save(userCredential);

		return userCredential.toBean();
	}

	// DELETE
	public UserBean deleteUserCredential(UserCredentialBean userCredentialBean) {
		if(userCredentialBean == null || userCredentialBean.getUserId() == null)
			return null;

		User user = userRepository.findById(userCredentialBean.getUserId()).orElse(null);
		if(user == null || user.getUserInformation() == null)
			return null;

		UserCredential userCredential = user.getUserCredential();
		if(userCredential == null) {
			System.err.println("User Credential does not exists !! for the User: " + user.getId());
			return null;
		}
		
		userCredential.setUser(null);
		user.setUserCredential(null);
		user.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		user = userRepository.save(user);

		return user.toBean();
	}

}
