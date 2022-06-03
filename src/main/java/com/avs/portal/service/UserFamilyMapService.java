package com.avs.portal.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avs.portal.bean.UserBean;
import com.avs.portal.bean.UserFamilyMapBean;
import com.avs.portal.entity.User;
import com.avs.portal.entity.UserFamilyMap;
import com.avs.portal.enums.FamilyMemberTitleEnum;
import com.avs.portal.enums.MaritalStatusEnum;
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
			UserFamilyMapBean userFamilyMapBean;

			if(user.getUserFamilyMap() == null)
				return userFamilyMaps;

			userFamilyMapBean = user.getUserFamilyMap().toBean();

			userFamilyMapBean.setId(user.getId());
			if(user.getUserFamilyMap().getFamilyHeadId() != null) {
				User familyHead = userRepository.findById(user.getUserFamilyMap().getFamilyHeadId()).orElse(null);
				if(familyHead != null) {
					userFamilyMapBean.setFamilyHeadId(familyHead.getId());
				}
			}

			if(user.getUserFamilyMap().getParentFamilyHeadId() != null) {
				User parentFamilyHead = userRepository.findById(user.getUserFamilyMap().getParentFamilyHeadId()).orElse(null);
				if(parentFamilyHead != null) {
					userFamilyMapBean.setParentFamilyHeadId(parentFamilyHead.getId());
				}
			}

			userFamilyMaps.add(userFamilyMapBean);
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

	public UserFamilyMapBean createUserFamilyMap(UserFamilyMapBean userFamilyMapBean) {
		if(userFamilyMapBean == null || userFamilyMapBean.getUserId() == null)
			return null;

		User user = userRepository.findById(userFamilyMapBean.getUserId()).orElse(null);
		if(user == null)
			return null;

		UserFamilyMap userFamilyMap = user.getUserFamilyMap();
		if(userFamilyMap != null) {
			return userFamilyMap.toBean();
		}

		userFamilyMap = new UserFamilyMap()
				.setParentFamilyHeadId(userFamilyMapBean.getParentFamilyHeadId());
		
		UUID familyHeadId = userFamilyMapBean.getFamilyHeadId() == null ? null : userFamilyMapBean.getFamilyHeadId();
		userFamilyMap.setFamilyHeadId(
						userFamilyMapBean.getTitle().equals(FamilyMemberTitleEnum.HEAD) 
						? user.getId() 
								: (familyHeadId)
						)
				.setTitle(userFamilyMapBean.getTitle())
				.setLiveStatus(userFamilyMapBean.getLiveStatus())
				.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()))
				.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		if(userFamilyMapBean.getTitle().equals(FamilyMemberTitleEnum.HEAD))
			userFamilyMap.setFamilyHeadId(user.getId());

		userFamilyMap.setUser(user);
		user.setUserFamilyMap(userFamilyMap);

		user.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		user = userRepository.save(user);

		return user.getUserFamilyMap().toBean();
	}

	public UserBean updateUserFamilyMap(UserFamilyMapBean userFamilyMapBean) {
		if(userFamilyMapBean == null || userFamilyMapBean.getUserId() == null)
			return null;

		User user = userRepository.findById(userFamilyMapBean.getUserId()).orElse(null);
		if(user == null)
			return null;

		UserFamilyMap userFamilyMap = user.getUserFamilyMap();
		if(userFamilyMap == null) {
			return null;
		}

		// Validate HeadID, ParentHeadID
		validateUserFamilyMapBean(userFamilyMap, userFamilyMapBean);
		
		userFamilyMap
		.setLiveStatus(userFamilyMapBean.getLiveStatus())
		.setTitle(userFamilyMapBean.getTitle())
		.setFamilyHeadId(userFamilyMapBean.getFamilyHeadId())
		.setParentFamilyHeadId(userFamilyMapBean.getParentFamilyHeadId())
		.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));

		if(userFamilyMapBean.getTitle() != null &&
			userFamilyMapBean.getTitle().equals(FamilyMemberTitleEnum.SON) || userFamilyMapBean.getTitle().equals(FamilyMemberTitleEnum.DAUGHTER))
				user.getUserProfile().setMaritalStatus(MaritalStatusEnum.SINGLE);

		if(FamilyMemberTitleEnum.HEAD.equals(userFamilyMapBean.getTitle()))
			userFamilyMap.setFamilyHeadId(user.getId());

		user.setUserFamilyMap(userFamilyMap);
		user.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));

		User theUser = userRepository.save(user);

		return theUser.toBean();

	}

	public UserBean deleteUserFamilyMap(UserBean userBean) {
		if(userBean == null || userBean.getId() == null)
			return null;

		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null)
			return null;

		UserFamilyMap userFamilyMap = user.getUserFamilyMap();
		if(userFamilyMap == null) {
			return user.toBean();
		}

		user.setUserFamilyMap(null);
		userFamilyMap.setUser(null);
		user.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));

		User theUser = userRepository.save(user);

		return theUser.toBean();

	}

	private UserFamilyMapBean validateUserFamilyMapBean(UserFamilyMap userFamilyEntity, UserFamilyMapBean userFamilyBean) {
		if(userFamilyBean == null || userFamilyBean.getTitle() == null)
			return userFamilyBean;

		if(userFamilyBean.getFamilyHeadId() != null) {
			switch (userFamilyBean.getTitle().getText()) {
			case "HEAD":
				userFamilyBean.setFamilyHeadId(userFamilyEntity.getUser().getId());
				break;

			case "SPOUSE":
			case "SON":		
			case "DAUGHTER":
				if(userFamilyEntity.getUser().getId().equals(userFamilyBean.getFamilyHeadId()))
					userFamilyBean.setFamilyHeadId(null);
				break;

			default:
				break;
			}
		}
		
		if(userFamilyBean.getParentFamilyHeadId() != null) {
			// User can't be ParentHead
			if(userFamilyEntity.getUser().getId().equals(userFamilyBean.getParentFamilyHeadId())) {
				userFamilyBean.setParentFamilyHeadId(null);
			}

			// User's Head can't be ParentHead
			if(userFamilyEntity.getFamilyHeadId() != null && userFamilyEntity.getFamilyHeadId().equals(userFamilyBean.getParentFamilyHeadId())) {
				userFamilyBean.setParentFamilyHeadId(null);
			}			
		}
		
		return userFamilyBean;
	}

	public List<String[]> listDistinctFamilyHeadsInfo() {		
		return userFamilyMapRepository.nativeQueryDistinctFamilyHeads();
	}

	public List<String[]> listDistinctParentFamilyHeadsInfo() {		
		return userFamilyMapRepository.nativeQueryDistinctParentFamilyHeads();
	}

}
