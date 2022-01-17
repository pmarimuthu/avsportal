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

@Service
public class UserFamilyMapService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;

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

				System.out.println(String.format("%s/%s/%s", parentFamilyHeadName, familyHeadName, userName));
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

		System.out.println("## Before Validation: Family-Head-Id: " + userFamilyMapBean.getFamilyHeadId());
		validateUserFamilyMap(userFamilyMap, userFamilyMapBean);
		System.out.println("## After Validation: Family-Head-Id: " + userFamilyMapBean.getFamilyHeadId());
		
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

		user = userRepository.save(user);

		return user.toBean();		

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

		user = userRepository.save(user);

		return user.toBean();

	}

	// VALIDATIONS
	private void validateUserFamilyMap(UserFamilyMap entity, UserFamilyMapBean bean) {
		if(bean == null || bean.getTitle() == null)
			return;

		switch (bean.getTitle().getText()) {
		case "HEAD":
			bean.setFamilyHeadId(entity.getUser().getId());				
			break;

		case "SPOUSE":
		case "SON":		
		case "DAUGHTER":
			if(entity.getUser().getId().equals(bean.getFamilyHeadId()))
				bean.setFamilyHeadId(null);
			break;

		default:
			break;
		}
	}

	public List<UserInformationBean> listDistinctParentFamilyHeads() {
		List<UserInformationBean> parentFamilyHeadUserInformationBeans = new ArrayList<>();
		
		List<UUID> parentFamilyHeadUUIDs = userFamilyMapRepository.findDistinctByParentFamilyHeadIdNotNull().stream().map(UserFamilyMap :: getParentFamilyHeadId).collect(Collectors.toList());
		System.out.println("Before Filter: " + parentFamilyHeadUUIDs);
		
		Set<UUID> uuids = new HashSet<>();
		List<UUID> distinctParentFamilyHeadUUIDs = parentFamilyHeadUUIDs.stream().filter(id -> uuids.add(id)).collect(Collectors.toList());
		System.out.println("Aftere Filter: " + distinctParentFamilyHeadUUIDs);
		
		System.out.println("#### distinctParentFamilyHeads :::");
		for (UUID parentFamilyHeadId : distinctParentFamilyHeadUUIDs) {
			User parentFamilyHead = userRepository.findById(parentFamilyHeadId).orElse(null);
			parentFamilyHeadUserInformationBeans.add(parentFamilyHead.getUserInformation().toBean());
		}
		
		System.out.println("By for-each ... " + parentFamilyHeadUserInformationBeans.toString());
		
		//////
		List<UserInformationBean> theUserInformationBeans = userFamilyMapRepository.findDistinctByParentFamilyHeadIdNotNull().stream()
			.map(UserFamilyMap :: getParentFamilyHeadId)
			.map(id -> userRepository.findById(id).orElse(null))
			.map(user -> user.getUserInformation().toBean())
			.collect(Collectors.toList());
		
		System.out.println("By Stream ... " + theUserInformationBeans.toString());

		return parentFamilyHeadUserInformationBeans;
	}

	public List<UserInformationBean> listDistinctFamilyHeads() {
		List<UserInformationBean> familyHeadUserInformationBeans = new ArrayList<>();
		
		List<UUID> familyHeadUUIDs = userFamilyMapRepository.findDistinctByFamilyHeadIdNotNull().stream().map(UserFamilyMap :: getFamilyHeadId).collect(Collectors.toList());
		System.out.println("Before Filter: " + familyHeadUUIDs);
		
		Set<UUID> uuids = new HashSet<>();
		List<UUID> distinctFamilyHeadUUIDs = familyHeadUUIDs.stream().filter(id -> uuids.add(id)).collect(Collectors.toList());
		System.out.println("Aftere Filter: " + distinctFamilyHeadUUIDs);
		
		System.out.println("#### distinctFamilyHeads :::");
		for (UUID familyHeadId : distinctFamilyHeadUUIDs) {
			User familyHead = userRepository.findById(familyHeadId).orElse(null);
			familyHeadUserInformationBeans.add(familyHead.getUserInformation().toBean());
		}
		
		System.out.println("By for-each ... " + familyHeadUserInformationBeans.toString());
		
		//////
		List<UserInformationBean> theUserInformationBeans = userFamilyMapRepository.findDistinctByFamilyHeadIdNotNull().stream()
			.map(UserFamilyMap :: getFamilyHeadId)
			.map(id -> userRepository.findById(id).orElse(null))
			.map(user -> user.getUserInformation().toBean())
			.collect(Collectors.toList());
		
		System.out.println("By Stream ... " + theUserInformationBeans.toString());

		return familyHeadUserInformationBeans;
	}

}
