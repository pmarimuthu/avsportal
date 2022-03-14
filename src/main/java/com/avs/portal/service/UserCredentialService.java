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

	public List<UserCredentialBean> getAllUsersCredentials() {
		return userRepository.findAll().stream().map(
				user -> {
					return user.getUserCredential() != null 
							? user.getUserCredential().toBean() 
									: new UserCredentialBean().setUserId(user.getId());
				}).collect(Collectors.toList());
	}

	public UserCredentialBean getUserCredential(UserBean userBean) {
		if(userBean == null || userBean.getId() == null)
			return null;

		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null || user.getUserCredential() == null)
			return null;

		return user.getUserCredential().toBean();
	}

	public UserCredentialBean createUserCredential(UserBean userBean, UserCredentialBean userCredentialBean) {
		if(userBean == null || userBean.getId() == null || userCredentialBean == null || !userCredentialBean.getUserId().equals(userBean.getId()))
			return null;

		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null)
			return null;

		UserCredential userCredential = user.getUserCredential();
		if(userCredential != null) {
			return null;
		}

		if(CommonUtil.getValidatedPassword(userCredentialBean.getPassword()) == null) {
			return null;
		}

		userCredential = new UserCredential();

		userCredential.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()))
			.setPassword(CommonUtil.getValidatedPassword(userCredentialBean.getPassword()))
			.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()))
			.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		user.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));

		userCredential.setUser(user);
		user.setUserCredential(userCredential);

		userCredential = userCredentialRepository.save(userCredential);

		return userCredential.toBean();
	}

	public UserBean updateUserCredential(UserCredentialBean userCredentialBean) {
		if(userCredentialBean == null || userCredentialBean.getUserId() == null)
			return null;

		User user = userRepository.findById(userCredentialBean.getUserId()).orElse(null);
		if(user == null)
			return null;

		UserCredential userCredential = user.getUserCredential();
		if(userCredential == null) {
			return null;
		}

		if(CommonUtil.getValidatedPassword(userCredentialBean.getPassword()) == null) {
			return null;
		}

		userCredential = new UserCredential();

		userCredential.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()))
		.setPassword(CommonUtil.getValidatedPassword(userCredentialBean.getPassword()))
		.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));

		userCredential.setUser(user);
		user.setUserCredential(userCredential);

		userCredentialRepository.save(userCredential);

		return user.toBean();
	}

	public UserBean deleteUserCredential(UserBean userBean) {
		if(userBean == null || userBean.getId() == null)
			return null;

		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null || user.getUserCredential() == null)
			return null;

		UserCredential userCredential = user.getUserCredential();
		if(userCredential == null) {
			return null;
		}


		user.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		userCredential.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));

		userCredential.setUser(null);
		user.setUserCredential(null);
		
		userCredentialRepository.delete(userCredential);

		return user.toBean();
	}

}
