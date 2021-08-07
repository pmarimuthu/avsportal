package com.avs.portal.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avs.portal.bean.UserBean;
import com.avs.portal.entity.LoginHistory;
import com.avs.portal.entity.Notification;
import com.avs.portal.entity.TempPassword;
import com.avs.portal.entity.User;
import com.avs.portal.entity.UserAccountStatus;
import com.avs.portal.entity.UserAddress;
import com.avs.portal.entity.UserCredential;
import com.avs.portal.entity.UserInformation;
import com.avs.portal.entity.UserPreferences;
import com.avs.portal.entity.UserProfile;
import com.avs.portal.entity.UserReferrerMap;
import com.avs.portal.entity.UserRelationToMeMap;
import com.avs.portal.entity.UserRoleMap;
import com.avs.portal.entity.UserVerification;
import com.avs.portal.enums.LanguageEnum;
import com.avs.portal.enums.RoleEnum;
import com.avs.portal.enums.VisibilityEnum;
import com.avs.portal.repository.LoginHistoryRepository;
import com.avs.portal.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private LoginHistoryRepository loginHistoryRepository;

	// READ {ALL}
	public List<UserBean> getUsers() {
		return userRepository.findAll().stream().map(t -> {
			try {
				return t.toBean();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}).collect(Collectors.toList());
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
		createTempPassword(user);
		createUserPreferences(user);
		createUserInformation(user);
		createUserAddress(user);
		createUserProfile(user);
		createUserRoleMap(user);
		createUserVerification(user);
		createUserReferrerMap(user);
		createNotification(user);
		createUserRelationToMeMap(user);
		
		/**
		List<LoginHistory> loginHistories = createLoginHistories(user);
		for (LoginHistory loginHistory : loginHistories) {
			loginHistoryRepository.save(loginHistory);
		}
		*/
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
		userCredential.setPassword(UUID.randomUUID().toString());
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

	// TempPassword :: temp_password_04
	// --------------------------------
	private TempPassword createTempPassword(User user) {
		TempPassword tempPassword = new TempPassword();
		tempPassword.setIsUsed(Boolean.FALSE);
		tempPassword.setGeneratedPassword(UUID.randomUUID().toString());
		tempPassword.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
		tempPassword.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		tempPassword.setUser(user);
		user.setTempPassword(tempPassword);
		
		return tempPassword;
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
	
	// UserAddress :: user_address_07
	// ------------------------------
	private UserAddress createUserAddress(User user) {
		UserAddress userAddress = new UserAddress();
		userAddress.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
		userAddress.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		userAddress.setUser(user);
		user.setUserAddress(userAddress);
		
		return userAddress;
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

	// UserVerification :: user_verification_10
	// -------------------------------
	private UserVerification createUserVerification(User user) {
		UserVerification userVerification = new UserVerification();
		userVerification.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
		userVerification.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		userVerification.setUser(user);
		user.setUserVerification(userVerification);
		
		return userVerification;
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
		notification.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
		notification.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		notification.setUser(user);
		user.setNotification(notification);
		
		return notification;
	}

	// LoginHistory :: login_history_13
	// --------------------------------
	private List<LoginHistory> createLoginHistories(User user) {
		LoginHistory loginHistory1 = new LoginHistory();
		loginHistory1.setConsecutiveFailedLoginCount(0);
		loginHistory1.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
		loginHistory1.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		LoginHistory loginHistory2 = new LoginHistory();
		loginHistory2.setConsecutiveFailedLoginCount(0);
		loginHistory2.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
		loginHistory2.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		loginHistory1.setUser(user);
		loginHistory2.setUser(user);
		
		return Arrays.asList(loginHistory1, loginHistory2);
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
