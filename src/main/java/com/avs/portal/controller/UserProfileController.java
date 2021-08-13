package com.avs.portal.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avs.portal.bean.UserBean;
import com.avs.portal.bean.UserProfileBean;
import com.avs.portal.entity.User;
import com.avs.portal.entity.UserProfile;
import com.avs.portal.repository.UserProfileRepository;
import com.avs.portal.repository.UserRepository;

@SuppressWarnings("unused")
@RestController
@RequestMapping(path = "/api/user-profile")
public class UserProfileController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserProfileRepository userProfileRepository;

	@GetMapping("/health")
	public String sayHello() {
		return "UserProfileController is Alive!!";
	}

	@PostMapping("/list")
	public List<UserProfileBean> getAllUserProfiles() {
		return userProfileRepository.findAll().stream().map(UserProfile :: toBean).collect(Collectors.toList());
	}

	@PostMapping("/get")
	public UserProfileBean getUserProfile(@RequestBody UserBean userBean) {
		if(userBean == null || userBean.getId() == null)
			return null;
		
		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null || user.getUserProfile() == null)
			return null;
		
		return user.getUserProfile().toBean();
	}

	@PostMapping("/add")
	public UserProfileBean createUserProfile(@RequestBody UserProfileBean userProfileBean) {
		if(userProfileBean == null || userProfileBean.getUserId() == null)
			return null;

		User user = userRepository.findById(userProfileBean.getUserId()).orElse(null);
		if(user == null) 
			return null;

		UserProfile userProfile = user.getUserProfile();
		if(userProfile != null) {
			System.err.println("UserProfile already exists !! for the User: " + user.getId());
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
		
		userProfile.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		user.setUserProfile(userProfile);
		userProfile.setUser(user);
		
		userProfile = userProfileRepository.save(userProfile);
		
		return userProfile.toBean();
	}
	
	@PutMapping("/edit")
	public UserProfileBean updateUserProfile(@RequestBody UserProfileBean userProfileBean) {
		if(userProfileBean == null || userProfileBean.getUserId() == null)
			return null;

		User user = userRepository.findById(userProfileBean.getUserId()).orElse(null);
		if(user == null || user.getUserProfile() == null) 
			return null;

		UserProfile userProfile = user.getUserProfile();
		if(userProfile == null) {
			System.err.println("UserProfile doesn't exists !! for the User: " + user.getId());
			return null;
		}
		
		userProfile.setBirthTimestamp(userProfileBean.getBirthTimestamp() == null ? null : Timestamp.valueOf(userProfileBean.getBirthTimestamp()));
		userProfile.setCaste(userProfileBean.getCaste());
		userProfile.setKoththiram(userProfileBean.getKoththiram());
		userProfile.setMaritalStatus(userProfileBean.getMaritalStatus());
		userProfile.setNatchaththiram(userProfileBean.getNatchaththiram());
		userProfile.setPlaceOfBirth(userProfileBean.getPlaceOfBirth());
		userProfile.setRaasi(userProfileBean.getRaasi());
		userProfile.setReligion(userProfileBean.getReligion());
		userProfile.setSubcaste(userProfileBean.getSubcaste());
		
		userProfile.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		user.setUserProfile(userProfile);
		userProfile.setUser(user);
		
		user = userRepository.save(user); 
		
		return user.getUserProfile().toBean();
	}	

	@DeleteMapping("/delete")
	public UserBean deleteUserProfile(@RequestBody UserBean userBean) {
		if(userBean == null || userBean.getId() == null)
			return null;
		
		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null || user.getUserProfile() == null)
			return null;
		
		UserProfile userProfile = user.getUserProfile();
		userProfile.setUser(null);
		user.setUserProfile(null);
		
		user = userRepository.save(user);
		
		return user.toBean();
	}

}
