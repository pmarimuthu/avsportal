package com.avs.portal.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avs.portal.bean.UserBean;
import com.avs.portal.bean.UserProfileBean;
import com.avs.portal.entity.User;
import com.avs.portal.entity.UserProfile;
import com.avs.portal.repository.UserProfileRepository;
import com.avs.portal.repository.UserRepository;

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
	public UserProfileBean createUserProfile(UserBean userBean, UserProfileBean userProfileBean) {
		if(userBean == null || userBean.getId() == null || userProfileBean == null)
			return null;

		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null)
			return null;
		
		UserProfile userProfile = user.getUserProfile();
		if(userProfile != null) {
			return null;
		}
		
		userProfile = new UserProfile();
		userProfile.setBirthTimestamp(userProfileBean.getBirthTimestamp() == null ? null : Timestamp.valueOf(userProfileBean.getBirthTimestamp()));
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
		
		user.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		user = userRepository.save(user);
		
		return user.getUserProfile().toBean();
	}

	// UPDATE
	public UserBean updateUserProfile(UserProfileBean userProfileBean) {
		if(userProfileBean == null || userProfileBean.getUserId() == null)
			return null;

		User user = userRepository.findById(userProfileBean.getUserId()).orElse(null);
		if(user == null)
			return null;
		
		UserProfile userProfile = user.getUserProfile();
		if(userProfile == null) {
			return null;
		}
		
		userProfile.setMaritalStatus(userProfileBean.getMaritalStatus());
		userProfile.setReligion(userProfileBean.getReligion());
		userProfile.setCaste(userProfileBean.getCaste());
		userProfile.setSubcaste(userProfileBean.getSubcaste());
		userProfile.setKoththiram(userProfileBean.getKoththiram());
		userProfile.setPlaceOfBirth(userProfileBean.getPlaceOfBirth());
		userProfile.setBirthTimestamp(userProfileBean.getBirthTimestamp() == null ? null : Timestamp.valueOf(userProfileBean.getBirthTimestamp()));
		userProfile.setRaasi(userProfileBean.getRaasi());
		userProfile.setNatchaththiram(userProfileBean.getNatchaththiram());
			
		userProfile.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		userProfile.setUser(user);
		user.setUserProfile(userProfile);
		
		user.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		user = userRepository.save(user);
		
		return user.toBean();
	}

	// DELETE
	public UserBean deleteTempPassword(UserBean userBean, UserProfileBean userProfileBean) {
		if(userBean == null || userBean.getId() == null)
			return userBean;

		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null || user.getUserProfile() == null)
			return null;

		UserProfile userProfile = user.getUserProfile();
		
		userProfile.setUser(null);
		user.setUserProfile(null);
		
		user.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		user = userRepository.save(user);

		return user.toBean();
	}

}
