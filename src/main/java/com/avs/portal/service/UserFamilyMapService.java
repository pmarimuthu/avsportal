package com.avs.portal.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avs.portal.bean.UserBean;
import com.avs.portal.bean.UserFamilyMapBean;
import com.avs.portal.bean.UserInformationBean;
import com.avs.portal.entity.User;
import com.avs.portal.entity.UserFamilyMap;
import com.avs.portal.enums.FamilyMemberTitleEnum;
import com.avs.portal.repository.UserFamilyMapRepository;
import com.avs.portal.repository.UserRepository;
import com.avs.portal.util.Logger;

@Service
public class UserFamilyMapService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserFamilyMapRepository userFamilyMapRepository;

	public List<UserFamilyMapBean> listUserFamilyMaps() {
		String userName = null, familyHeadName = null, parentFamilyHeadName = null;

		List<UserFamilyMapBean> userFamilyMaps = new ArrayList<>();

		List<User> users = userRepository.findAll().stream().collect(Collectors.toList());
		for (User user : users) {
			UserFamilyMapBean userFamilyMapBean;

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

				userFamilyMaps.add(userFamilyMapBean);
			}
		}
		Logger.info(userName + familyHeadName + parentFamilyHeadName);

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

		if(FamilyMemberTitleEnum.HEAD.equals(userFamilyMapBean.getTitle()))
			userFamilyMap.setFamilyHeadId(user.getId());

		user.setUserFamilyMap(userFamilyMap);
		user.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));

		User theUser = userRepository.save(user);

		return theUser.toBean()
				.setDistinctFamilyHeads(listDistinctFamilyHeads());

	}

	public UserBean deleteUserFamilyMap(UserBean userBean) {
		if(userBean == null || userBean.getId() == null)
			return null;

		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null)
			return null;

		UserFamilyMap userFamilyMap = user.getUserFamilyMap();
		if(userFamilyMap == null) {
			return user.toBean()
					.setDistinctFamilyHeads(listDistinctFamilyHeads());
		}

		user.setUserFamilyMap(null);
		userFamilyMap.setUser(null);
		user.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));

		User theUser = userRepository.save(user);

		return theUser.toBean()
				.setDistinctFamilyHeads(listDistinctFamilyHeads());

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

	public List<UserInformationBean> listDistinctFamilyHeads() {
		
		List<UserInformationBean> familyHeadUserInformationBeans = new ArrayList<>();
		
		List<UUID> familyHeadUUIDs = userFamilyMapRepository.findDistinctByFamilyHeadIdNotNull().stream().map(UserFamilyMap :: getFamilyHeadId).collect(Collectors.toList());
		
		Set<UUID> uuids = new HashSet<>();
		List<UUID> distinctFamilyHeadUUIDs = familyHeadUUIDs.stream().filter(id -> uuids.add(id)).collect(Collectors.toList());
		
		for (UUID familyHeadId : distinctFamilyHeadUUIDs) {
			User familyHead = userRepository.findById(familyHeadId).orElse(null);
			familyHeadUserInformationBeans.add(familyHead.getUserInformation().toBean());
		}
		
		return familyHeadUserInformationBeans;
	}

}
