package com.avs.portal.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avs.portal.bean.UserBean;
import com.avs.portal.bean.UserFamilyMapBean;
import com.avs.portal.entity.User;
import com.avs.portal.entity.UserFamilyMap;
import com.avs.portal.enums.FamilyMemberTitleEnum;
import com.avs.portal.repository.UserFamilyMapRepository;
import com.avs.portal.repository.UserRepository;

@Service
public class UserFamilyMapService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserFamilyMapRepository userFamilyMapRepository;

	public List<UserFamilyMapBean> listUserFamilyMaps() {
		List<UserFamilyMapBean> userFamilyMaps = new ArrayList<>();
		
		List<User> users = userRepository.findAll().stream().collect(Collectors.toList());
		for (User user : users) {
			UserFamilyMapBean userFamilyMapBean = null;
			String userName = "", familyHeadName = "", parentFamilyHeadName = "";
			
			if(user.getUserFamilyMap() != null) {
				userFamilyMapBean = user.getUserFamilyMap().toBean();
				
				userFamilyMapBean.setId(user.getId());
				userName = user.getEmail();
				
				if(user.getUserFamilyMap().getFamilyHeadId() != null) {
					User familyHead = userRepository.findById(user.getUserFamilyMap().getFamilyHeadId()).orElse(null);
					if(familyHead != null) {
						userFamilyMapBean.setFamilyHeadId(familyHead.getId());
						familyHeadName = familyHead.getEmail();
					}
				}
				
				if(user.getUserFamilyMap().getParentFamilyHeadId() != null) {
					User parentFamilyHead = userRepository.findById(user.getUserFamilyMap().getParentFamilyHeadId()).orElse(null);
					if(parentFamilyHead != null) {
						userFamilyMapBean.setParentFamilyHeadId(parentFamilyHead.getId());
						parentFamilyHeadName = parentFamilyHead.getEmail();
					}
				}
				
				userFamilyMapBean.setPath3(String.format("%s/%s/%s", parentFamilyHeadName, familyHeadName, userName));
				userFamilyMaps.add(userFamilyMapBean);
			}
		}
		
		return userFamilyMaps;
	}

	public UserFamilyMapBean getUserFamilyMap(UserBean userBean) {
		if(userBean == null || userBean.getId() == null)
			return null;

		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null || user.getUserFamilyMap() == null)
			return null;

		return user.getUserFamilyMap().toBean();
	}

	public UserFamilyMapBean createUserFamilyMap(UserBean userBean, UserFamilyMapBean userFamilyMapBean) {
		if(userBean == null || userBean.getId() == null || userFamilyMapBean == null)
			return null;

		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null)
			return null;

		UserFamilyMap userFamilyMap = user.getUserFamilyMap();
		if(userFamilyMap != null) {
			System.err.println("User already associated with the UserFamilyMap: " + userFamilyMap.getId());
			return userFamilyMap.toBean();
		}

		userFamilyMap = new UserFamilyMap()
				.setParentFamilyHeadId(userFamilyMapBean.getParentFamilyHeadId())
				.setFamilyHeadId(
						userFamilyMapBean.getTitle().equals(FamilyMemberTitleEnum.HEAD) 
							? user.getId() 
							: (userFamilyMapBean.getFamilyHeadId() == null ? null : userFamilyMapBean.getFamilyHeadId())
						)
				.setTitle(userFamilyMapBean.getTitle())
				.setLiveStatus(userFamilyMapBean.getLiveStatus())
				.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()))
				.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));

		userFamilyMap.setUser(user);
		user.setUserFamilyMap(userFamilyMap);

		user.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		userFamilyMap = userFamilyMapRepository.save(userFamilyMap);

		return user.getUserFamilyMap().toBean();
	}

	public UserFamilyMapBean editUserFamilyMap(UserBean userBean, UserFamilyMapBean userFamilyMapBean) {
		if(userBean == null || userBean.getId() == null || userFamilyMapBean == null)
			return null;

		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null)
			return null;

		UserFamilyMap userFamilyMap = user.getUserFamilyMap();
		if(userFamilyMap == null) {
			System.err.println("UserFamilyMap doesn't exists for the User: " + user.getId());
			return null;
		}
		
		userFamilyMap
			.setParentFamilyHeadId(userFamilyMapBean.getParentFamilyHeadId())
			.setFamilyHeadId(userFamilyMapBean.getFamilyHeadId())
			.setLiveStatus(userFamilyMapBean.getLiveStatus())
			.setTitle(userFamilyMapBean.getTitle())
			.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		user.setUserFamilyMap(userFamilyMap);
		user.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		user = userRepository.save(user);
		
		return user.getUserFamilyMap().toBean();		

	}

	public UserBean deleteUserFamilyMap(UserBean userBean, UserFamilyMapBean userFamilyMapBean) {
		if(userBean == null || userBean.getId() == null || userFamilyMapBean == null)
			return null;

		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null)
			return null;

		UserFamilyMap userFamilyMap = user.getUserFamilyMap();
		if(userFamilyMap == null) {
			System.err.println("UserFamilyMap doesn't exists for the User: " + user.getId());
			return null;
		}
		
		user.setUserFamilyMap(null);
		userFamilyMap.setUser(null);
		user.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		userFamilyMapRepository.delete(userFamilyMap);
		
		return user.toBean();		

	}

}
