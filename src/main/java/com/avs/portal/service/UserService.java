package com.avs.portal.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avs.portal.bean.UserBean;
import com.avs.portal.entity.Notification;
import com.avs.portal.entity.User;
import com.avs.portal.entity.UserAccountStatus;
import com.avs.portal.entity.UserCredential;
import com.avs.portal.entity.UserInformation;
import com.avs.portal.entity.UserPreferences;
import com.avs.portal.entity.UserProfile;
import com.avs.portal.entity.UserReferrerMap;
import com.avs.portal.entity.UserRelationToMeMap;
import com.avs.portal.entity.UserRoleMap;
import com.avs.portal.enums.LanguageEnum;
import com.avs.portal.enums.NotificationTypeEnum;
import com.avs.portal.enums.RoleEnum;
import com.avs.portal.enums.VisibilityEnum;
import com.avs.portal.repository.UserRepository;
import com.avs.portal.util.CommonUtil;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	// READ {ALL}
	public List<UserBean> getUsers() {
		List<User> list = userRepository.findAll();
		System.out.println(list.size());
		for (User user : list) {
			System.out.println(user.toBean().toString());
		}
		return userRepository.findAll().stream().map(User :: toBean).collect(Collectors.toList());
	}

	// READ {ONE}
	public UserBean getUser(UserBean bean) throws Exception {
		if(bean == null)
			return null;

		if(bean.getId() != null) {
			return getUserById(bean.getId());
		}

		if(bean.getPhone() != null) {
			return getUserByPhone(bean.getPhone());
		}

		if(bean.getEmail() != null) {
			return getUserByEmail(bean.getEmail());
		}

		return null;
	}

	// CREATE
	@Transactional
	public UserBean addUser(UserBean bean) throws Exception {
		if(bean == null)
			return null;

		// User :: user_01
		// ---------------
		User user = new User();
		user.setPhone(bean.getPhone());
		user.setEmail(bean.getEmail());
		user.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
		user.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		createUserCredential(user);
		createUserAccountStatus(user);
		createUserPreferences(user);
		createUserInformation(user);
		createUserProfile(user);
		createUserRoleMap(user);
		createUserReferrerMap(user);
		createNotification(user);
		createUserRelationToMeMap(user);
		
		user = userRepository.save(user);
		
		return user.toBean();
	}

	// UPDATE
	public UserBean editUser(UserBean bean) {
		if(bean == null || bean.getId() == null)
			return null;

		User entity = userRepository.findById(bean.getId()).orElse(null);
		if(entity == null)
			return null;

		entity.setPhone(bean.getPhone());
		entity.setEmail(bean.getEmail());
		entity.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		entity = userRepository.save(entity);
		
		return entity.toBean();
	}

	// DELETE
	public UserBean deleteUser(UserBean bean) {
		if(bean == null || bean.getId() == null)
			return bean;

		User entity = userRepository.findById(bean.getId()).orElse(null);
		if(entity == null)
			return null;

		userRepository.delete(entity);

		return entity.toBean();
	}

	private UserBean getUserById(UUID id) {
		User entity = userRepository.findById(id).orElse(null);
		return entity != null ? entity.toBean() : null;
	}

	private UserBean getUserByEmail(String email) throws Exception {
		List<User> entities = userRepository.findByEmail(email);
		if(entities.size() == 1)
			return entities.get(0).toBean();

		if(entities.size() > 1)
			throw new Exception("[ERR] more than one user with same Email.");

		return null;
	}

	private UserBean getUserByPhone(Long phone) throws Exception {
		List<User> entities = userRepository.findByPhone(phone);
		if(entities.size() == 1)
			return entities.get(0).toBean();

		if(entities.size() > 1)
			throw new Exception("[ERR] more than one user with same Phone.");

		return null;
	}

	@SuppressWarnings("unused")
	private void onPostCreateNewUser(UserBean user) {
		System.out.println(user.getId());
		System.out.println(user.toString());

	}

	// UserCredential :: user_credential_02
	// ------------------------------------
	private UserCredential createUserCredential(User user) {
		UserCredential userCredential = new UserCredential();
		userCredential.setPassword(CommonUtil.generateTempPassword());
		userCredential.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
		userCredential.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));

		userCredential.setUser(user);
		user.setUserCredential(userCredential);
		
		return userCredential;		
	}

	// UserAccountStatus :: user_account_status_03
	// -------------------------------------------
	private UserAccountStatus createUserAccountStatus(User user) {
		UserAccountStatus userAccountStatus = new UserAccountStatus();
		userAccountStatus.setIsDeleted(Boolean.FALSE);
		userAccountStatus.setIsLocked(Boolean.FALSE);
		userAccountStatus.setIsActive(Boolean.FALSE);
		userAccountStatus.setIsBlocked(Boolean.FALSE);
		userAccountStatus.setIsVerified(Boolean.FALSE);
		userAccountStatus.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
		userAccountStatus.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));

		userAccountStatus.setUser(user);
		user.setUserAccountStatus(userAccountStatus);
		
		return userAccountStatus;

	}

	// UserAccountStatus :: user_preferences_05
	// -------------------------------------------
	private UserPreferences createUserPreferences(User user) {
		UserPreferences userPreferences = new UserPreferences();
		userPreferences.setAdvertisement(Boolean.FALSE);
		userPreferences.setVisibility(VisibilityEnum.FRIENDLY);
		userPreferences.setLanguage(LanguageEnum.ENGLISH);
		
		userPreferences.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
		userPreferences.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		userPreferences.setUser(user);
		user.setUserPreferences(userPreferences);
		
		return userPreferences;
	}

	// UserInformation :: user_information_06
	// --------------------------------------
	private UserInformation createUserInformation(User user) {
		UserInformation userInformation = new UserInformation();
		userInformation.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
		userInformation.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		userInformation.setUser(user);
		user.setUserInformation(userInformation);
		
		return userInformation;
	}
	
	// UserProfile :: user_profile_08
	// ------------------------------
	private UserProfile createUserProfile(User user) {
		UserProfile userProfile = new UserProfile();
		userProfile.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
		userProfile.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		userProfile.setUser(user);
		user.setUserProfile(userProfile);
		
		return userProfile;
	}

	// UserRoleMap :: user_role_map_09
	// -------------------------------
	private UserRoleMap createUserRoleMap(User user) {
		UserRoleMap userRoleMap = new UserRoleMap();
		userRoleMap.setRole(RoleEnum.USER);
		userRoleMap.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
		userRoleMap.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		userRoleMap.setUser(user);
		user.setUserRoleMap(userRoleMap);
		
		return userRoleMap;
	}

	// UserReferrerMap :: user_referrer_map_11
	// ---------------------------------------
	private UserReferrerMap createUserReferrerMap(User user) {
		UserReferrerMap userReferrerMap = new UserReferrerMap();
		userReferrerMap.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
		userReferrerMap.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		userReferrerMap.setUser(user);
		user.setUserReferrerMap(userReferrerMap);
		
		return userReferrerMap;
	}

	// Notification :: notification_12
	// -------------------------------
	private Notification createNotification(User user) {
		Notification notification = new Notification();
		notification.setMessageText("Welcome to AVS Portal!");
		notification.setNotificationType(NotificationTypeEnum.INFORMATION);
		notification.setIsRead(Boolean.FALSE);
		
		notification.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
		notification.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		notification.setUser(user);
		user.setNotification(notification);
		
		return notification;
	}

	// UserRelationToMeMap :: user_relation_to_me_map_14
	// -------------------------------------------------
	private UserRelationToMeMap createUserRelationToMeMap(User user) {
		UserRelationToMeMap userRelationToMeMap = new UserRelationToMeMap();
		userRelationToMeMap.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
		userRelationToMeMap.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		userRelationToMeMap.setUser(user);
		user.setUserRelationToMeMap(userRelationToMeMap);
		
		return userRelationToMeMap;
	}


}
