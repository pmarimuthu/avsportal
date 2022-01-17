package com.avs.portal.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avs.portal.bean.UserBean;
import com.avs.portal.bean.UserRoleMapBean;
import com.avs.portal.entity.User;
import com.avs.portal.entity.UserRoleMap;
import com.avs.portal.enums.RoleEnum;
import com.avs.portal.repository.UserRepository;
import com.avs.portal.repository.UserRoleMapRepository;

@Service
public class UserRoleMapService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserRoleMapRepository userRoleMapRepository;

	public List<UserRoleMapBean> listUserRoleMaps() {
		List<UserRoleMapBean> userRoleMaps = new ArrayList<>();
		
		List<User> users = userRepository.findAll().stream().collect(Collectors.toList());
		for (User user : users) {
			UserRoleMapBean userRoleMapBean = null;
			if(user.getUserRoleMap() != null) {
				userRoleMapBean = user.getUserRoleMap().toBean();				
				userRoleMaps.add(userRoleMapBean);
			}
		}
		
		return userRoleMaps;
	}

	public UserRoleMapBean getUserRoleMap(UserBean userBean) {
		if(userBean == null || userBean.getId() == null)
			return null;

		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null)
			return null;
		
		 UserRoleMap userRoleMap = user.getUserRoleMap();
		 if(userRoleMap == null)
			 return null;

		return userRoleMap.toBean();
	}

	public UserRoleMapBean createUserRoleMap(UserRoleMapBean userRoleMapBean) {
		if(userRoleMapBean == null || userRoleMapBean.getId() == null || userRoleMapBean.getUserId() == null)
			return null;

		User user = userRepository.findById(userRoleMapBean.getUserId()).orElse(null);
		if(user == null)
			return null;

		UserRoleMap userRoleMap = user.getUserRoleMap();
		if(userRoleMap == null) {
			userRoleMap = new UserRoleMap();
		}

		userRoleMap
			.setRole(RoleEnum.USER)
			.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()))
			.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));

		userRoleMap.setUser(user);
		user.setUserRoleMap(userRoleMap);

		user.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		userRoleMap = userRoleMapRepository.save(userRoleMap);

		return user.getUserRoleMap().toBean();
	}

	public UserBean updateUserRoleMap(UserRoleMapBean userRoleMapBean) {
		if(userRoleMapBean == null || userRoleMapBean.getId() == null || userRoleMapBean.getUserId() == null)
			return null;

		User user = userRepository.findById(userRoleMapBean.getUserId()).orElse(null);
		if(user == null)
			return null;

		UserRoleMap userRoleMap = user.getUserRoleMap();
		if(userRoleMap == null) {
			return null;
		}
		
		userRoleMap
			.setRole(userRoleMapBean.getRole())
			.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		user.setUserRoleMap(userRoleMap);
		user.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		user = userRepository.save(user);
		
		return user.toBean();		

	}

	public UserBean deleteUserRoleMap(UserBean userBean) {
		if(userBean == null || userBean.getId() == null)
			return null;

		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null)
			return null;

		UserRoleMap userRoleMap = user.getUserRoleMap();
		if(userRoleMap == null) {
			return user.toBean();
		}
		
		user.setUserRoleMap(null);
		userRoleMap.setUser(null);
		user.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		userRoleMapRepository.delete(userRoleMap);
		
		return user.toBean();		

	}

}
