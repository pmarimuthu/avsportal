package com.avs.portal.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
		return userFamilyMapRepository.findAll().stream().map(UserFamilyMap :: toBean).collect(Collectors.toList());
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

		userFamilyMap = new UserFamilyMap(user.getId())
				.setParentFamilyHeadId(userFamilyMapBean.getParentFamilyHeadId())
				.setFamilyHeadId(
						userFamilyMapBean.getTitle().equals(FamilyMemberTitleEnum.HEAD) 
							? user.getId() 
							: userFamilyMapBean.getFamilyHeadId()
						)
				.setTitle(userFamilyMapBean.getTitle())
				.setLiveStatus(userFamilyMapBean.getLiveStatus())
				.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()))
				.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));

		userFamilyMap.getUsers().add(user);
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
		
		System.err.println(userFamilyMap.getUsers().size());
		user.setUserFamilyMap(null);
		userFamilyMap.getUsers().remove(user);
		user.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		user = userRepository.save(user);
		if(userFamilyMap.getUsers().size() == 0) {
			userFamilyMapRepository.delete(userFamilyMap);
		}
		
		return user.toBean();		

	}

}
