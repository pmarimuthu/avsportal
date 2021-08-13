package com.avs.portal.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avs.portal.bean.TempPasswordBean;
import com.avs.portal.bean.UserBean;
import com.avs.portal.bean.UserProfileBean;
import com.avs.portal.entity.TempPassword;
import com.avs.portal.entity.User;
import com.avs.portal.entity.UserProfile;
import com.avs.portal.repository.TempPasswordRepository;
import com.avs.portal.repository.UserProfileRepository;
import com.avs.portal.repository.UserRepository;
import com.avs.portal.util.CommonUtil;

@Service
public class UserProfileService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserProfileRepository userProfileRepository;
	
	// READ {ALL}
	public List<UserProfileBean> getAllUserProfiles() {
		return userProfileRepository.findAll().stream().map(UserProfile :: toBean).collect(Collectors.toList());
	}

	// READ {ONE}
	public UserProfileBean getUserProfile(UserBean userBean) {
		if(userBean == null || userBean.getId() == null)
			return null;
		
		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null || user.getUserProfile() == null)
			return null;
		
		return user.getUserProfile().toBean();
	}

	// CREATE
	public UserProfileBean createUserProfile(UserProfileBean userProfileBean) {
		if(userProfileBean == null || userProfileBean.getId() == null)
			return null;

		User user = userRepository.findById(userProfileBean.getUserId()).orElse(null);
		if(user == null)
			return null;
		
		UserProfile userProfile = user.getUserProfile();
		if(userProfile != null) {
			System.err.println("UserProfile exists !! for the User: " + userProfile.getUser().getId());
			return null;
		}
		
		userProfile = new UserProfile();
		userProfile.setBirthTimestamp(Timestamp.valueOf(userProfileBean.getBirthTimestamp()));
		userProfile.setCaste(userProfileBean.getCaste());
		userProfile.setKoththiram(userProfileBean.getKoththiram());
		userProfile.setMaritalStatus(userProfileBean.getMaritalStatus());
		userProfile.setNatchaththiram(userProfileBean.getNatchaththiram());
		userProfile.setPlaceOfBirth(userProfileBean.getPlaceOfBirth());
		userProfile.setRaasi(userProfileBean.getRaasi());
		userProfile.setReligion(userProfileBean.getReligion());
		userProfile.setSubcaste(userProfileBean.getSubcaste());
			
		userProfile.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
		userProfile.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		userProfile.setUser(user);
		user.setUserProfile(userProfile);
		
		user = userRepository.save(user);
		
		return user.getUserProfile().toBean();
	}

	// UPDATE
	public UserProfileBean updateUserProfile(UserProfileBean userProfileBean) {
		if(userProfileBean == null || userProfileBean.getId() == null)
			return null;

		User user = userRepository.findById(userProfileBean.getUserId()).orElse(null);
		if(user == null)
			return null;
		
		UserProfile userProfile = user.getUserProfile();
		if(userProfile == null) {
			System.err.println("UserProfile doesn't exists !! for the User: " + userProfileBean.getUserId());
			return null;
		}
		
		userProfile.setBirthTimestamp(Timestamp.valueOf(userProfileBean.getBirthTimestamp()));
		userProfile.setCaste(userProfileBean.getCaste());
		userProfile.setKoththiram(userProfileBean.getKoththiram());
		userProfile.setMaritalStatus(userProfileBean.getMaritalStatus());
		userProfile.setNatchaththiram(userProfileBean.getNatchaththiram());
		userProfile.setPlaceOfBirth(userProfileBean.getPlaceOfBirth());
		userProfile.setRaasi(userProfileBean.getRaasi());
		userProfile.setReligion(userProfileBean.getReligion());
		userProfile.setSubcaste(userProfileBean.getSubcaste());
			
		userProfile.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
		userProfile.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		userProfile.setUser(user);
		user.setUserProfile(userProfile);
		
		user = userRepository.save(user);
		
		return user.getUserProfile().toBean();
	}

	// DELETE
	public UserBean deleteTempPassword(UserBean userBean) {
		if(userBean == null || userBean.getId() == null)
			return userBean;

		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null || user.getUserProfile() == null)
			return null;

		UserProfile userProfile = user.getUserProfile();
		user.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		userProfile.setUser(null);
		user.setUserProfile(null);
		
		user = userRepository.save(user);

		return user.toBean();
	}

}
