package com.avs.portal.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avs.portal.bean.UserBean;
import com.avs.portal.bean.UserPreferencesBean;
import com.avs.portal.entity.User;
import com.avs.portal.entity.UserPreferences;
import com.avs.portal.repository.UserPreferencesRepository;
import com.avs.portal.repository.UserRepository;

@Service
public class UserPreferencesService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserPreferencesRepository userPreferencesRepository;
	
	public List<UserPreferencesBean> getAllUsersPreferences() {
		return userPreferencesRepository.findAll().stream().map(UserPreferences :: toBean).collect(Collectors.toList());
	}

	public UserPreferencesBean getUserPreferences(UserBean userBean) {
		if(userBean == null || userBean.getId() == null)
			return null;
		
		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null)
			return null;
		
		return user.getUserPreferences().toBean();
	}

	public UserPreferencesBean createUserPreferences(UserPreferencesBean userPreferencesBean) {
		if(userPreferencesBean == null || userPreferencesBean.getUserId() == null)
			return null;
		
		User user = userRepository.findById(userPreferencesBean.getUserId()).orElse(null);
		if(user == null) {
			return null;
		}
		
		UserPreferences userPreferences = user.getUserPreferences();
		if(userPreferences != null) {
			return null;
		}
		
		userPreferences = new UserPreferences();
		userPreferences.setAdvertisement(userPreferencesBean.getAdvertisementOpt());
		userPreferences.setLanguage(userPreferencesBean.getLanguage());
		userPreferences.setVisibility(userPreferencesBean.getVisibilityLevel());
		
		userPreferences.setUser(user);
		user.setUserPreferences(userPreferences);
		
		userPreferences.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
		userPreferences.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		userPreferences = userPreferencesRepository.save(userPreferences);
		
		return userPreferences.toBean();
	}

	public UserBean updateUserPreferences(UserPreferencesBean userPreferencesBean) {
		if(userPreferencesBean == null || userPreferencesBean.getUserId() == null) {
			return null;
		}
		
		User user = userRepository.findById(userPreferencesBean.getUserId()).orElse(null);
		if(user == null) {
			return null;
		}
		
		UserPreferences userPreferences = user.getUserPreferences();
		if(userPreferences == null) {
			return null;
		}
		
		userPreferences.setAdvertisement(userPreferencesBean.getAdvertisementOpt());
		userPreferences.setLanguage(userPreferencesBean.getLanguage());
		userPreferences.setVisibility(userPreferencesBean.getVisibilityLevel());
		
		userPreferences.setUser(user);
		user.setUserPreferences(userPreferences);
		
		userPreferences.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
		userPreferences.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		userPreferences = userPreferencesRepository.save(userPreferences);
		
		return user.toBean();
	}

	public UserBean deleteUserPreferences(UserBean userBean) {
		if(userBean == null || userBean.getId() == null)
			return null;
		
		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null || user.getUserPreferences() == null)
			return null;
		
		UserPreferences userPreferences = user.getUserPreferences();
		userPreferences.setUser(null);
		user.setUserPreferences(null);
		
		user = userRepository.save(user);
		
		return user.toBean();	
	}
	
}
