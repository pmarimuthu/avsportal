package com.avs.portal.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avs.portal.bean.UserBean;
import com.avs.portal.bean.UserInformationBean;
import com.avs.portal.entity.User;
import com.avs.portal.entity.UserInformation;
import com.avs.portal.repository.UserInformationRepository;
import com.avs.portal.repository.UserRepository;

@Service
public class UserInformationService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserInformationRepository userInformationRepository;
	
	public List<UserInformationBean> getAllUsersInformation() {
		return userInformationRepository.findAll().stream().map(UserInformation :: toBean).collect(Collectors.toList());
	}

	public UserInformationBean getUserInformation(UserBean bean) {
		if(bean == null || bean.getId() == null)
			return null;
		
		User user = userRepository.findById(bean.getId()).orElse(null);
		if(user == null || user.getUserInformation() == null)
			return null;
		
		return user.getUserInformation().toBean();
	}

	@Transactional
	public UserInformationBean createUserInformation(UserInformationBean userInformationBean) {
		if(userInformationBean == null || userInformationBean.getUserId() == null)
			return null;

		User user = userRepository.findById(userInformationBean.getUserId()).orElse(null);
		if(user == null)
			return null;
		
		UserInformation userInformation = user.getUserInformation();
		if(userInformation != null) {
			return null;
		}
		
		userInformation = new UserInformation();		
		userInformation.setFirstname(userInformationBean.getFirstname());
		userInformation.setLastname(userInformationBean.getLastname());
		userInformation.setGender(userInformationBean.getGender());
		userInformation.setDateOfBirth(userInformationBean.getDateOfBirth());
		userInformation.setProfession(userInformationBean.getProfession());
		userInformation.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
		userInformation.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		user.setUserInformation(userInformation);
		userInformation.setUser(user);
		
		userInformation = userInformationRepository.save(userInformation);
		
		return userInformation.toBean();
	}

	public UserBean updateUserInformation(UserInformationBean userInformationBean) {
		if(userInformationBean == null || userInformationBean.getUserId() == null)
			return null;

		User user = userRepository.findById(userInformationBean.getUserId()).orElse(null);
		if(user == null)
			return null;
		
		UserInformation userInformation = user.getUserInformation();
		if(userInformation == null) {
			return null;
		}
		
		userInformation.setFirstname(userInformationBean.getFirstname());
		userInformation.setLastname(userInformationBean.getLastname());
		userInformation.setGender(userInformationBean.getGender());
		userInformation.setDateOfBirth(userInformationBean.getDateOfBirth());
		userInformation.setProfession(userInformationBean.getProfession());
		userInformation.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		user.setUserInformation(userInformation);
		userInformation.setUser(user);
		
		userInformationRepository.save(userInformation);
		
		return user.toBean();
	}
	
	public UserBean deleteUserInformation(UserBean bean) {
		if(bean == null || bean.getId() == null)
			return bean;

		User user = userRepository.findById(bean.getId()).orElse(null);
		if(user == null || user.getUserInformation() == null)
			return null;

		user.setUserInformation(null);
		user.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		user = userRepository.save(user);

		return user.toBean();
	}

}
