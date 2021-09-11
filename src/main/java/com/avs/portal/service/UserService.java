package com.avs.portal.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avs.portal.bean.UserAddressBean;
import com.avs.portal.bean.UserBean;
import com.avs.portal.entity.User;
import com.avs.portal.entity.UserAccountStatus;
import com.avs.portal.entity.UserAddress;
import com.avs.portal.entity.UserCredential;
import com.avs.portal.entity.UserInformation;
import com.avs.portal.entity.UserPreferences;
import com.avs.portal.entity.UserProfile;
import com.avs.portal.entity.UserRoleMap;
import com.avs.portal.enums.LanguageEnum;
import com.avs.portal.enums.RoleEnum;
import com.avs.portal.enums.VisibilityEnum;
import com.avs.portal.repository.UserRepository;
import com.avs.portal.util.CommonUtil;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserAddressService userAddressService;

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
	public UserBean getUser(UserBean userBean) {
		if(userBean == null)
			return null;

		if(userBean.getId() != null) {
			return getUserById(userBean.getId());
		}

		if(userBean.getPhone() != null) {
			return getUserByPhone(userBean.getPhone());
		}

		if(userBean.getEmail() != null) {
			return getUserByEmail(userBean.getEmail());
		}

		return null;
	}

	// CREATE
	@Transactional
	public UserBean createUser(UserBean userBean) {
		if(userBean == null || !userBean.isValid(userBean))
			return null;

		// User :: user_01
		// ---------------
		User user = new User();
		user.setPhone(userBean.getPhone());
		user.setEmail(userBean.getEmail());
		user.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
		user.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));

		defaultUserCredential(user);
		defaultUserAccountStatus(user);
		defaultUserPreferences(user);
		defaultUserInformation(user);
		defaultUserProfile(user);
		// userFamilyMap, userReferrer
		
		defaultUserRoleMap(user);
		// userRelationToMeMap, userAddresses, notifications, userVerifications, loginHistories

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

	private UserBean getUserByEmail(String email) {
		List<User> entities = userRepository.findByEmail(email);
		if(entities.size() == 1)
			return entities.get(0).toBean();

		if(entities.size() > 1)
			System.err.println("[ERR] more than one user with same Email.");

		return null;
	}

	private UserBean getUserByPhone(Long phone) {
		List<User> entities = userRepository.findByPhone(phone);
		if(entities.size() == 1)
			return entities.get(0).toBean();

		if(entities.size() > 1)
			System.err.println("[ERR] more than one user with same Phone.");

		return null;
	}

	// UserCredential :: user_credential_02
	// -------------------------------------------
	private UserCredential defaultUserCredential(User user) {
		UserCredential userCredential = new UserCredential()
				.setPassword(CommonUtil.generateDefaultPassword())
				.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()))
				.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()))
				.setUser(user);
		
		user.setUserCredential(userCredential);
		
		return userCredential;
	}

	// UserAccountStatus :: user_account_status_03
	// -------------------------------------------
	private UserAccountStatus defaultUserAccountStatus(User user) {
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

	// UserInformation :: user_information_06
	// -------------------------------------------
	private UserInformation defaultUserInformation(User user) {
		UserInformation userInformation = new UserInformation();
		userInformation.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
		userInformation.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));

		userInformation.setUser(user);
		user.setUserInformation(userInformation);

		return userInformation;

	}

	// UserAccountStatus :: user_preferences_05
	// -------------------------------------------
	private UserPreferences defaultUserPreferences(User user) {
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

	// UserRoleMap :: user_role_map_09
	// -------------------------------
	private UserRoleMap defaultUserRoleMap(User user) {
		UserRoleMap userRoleMap = new UserRoleMap();
		userRoleMap.setRole(RoleEnum.USER);

		userRoleMap.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
		userRoleMap.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));

		userRoleMap.setUser(user);
		user.setUserRoleMap(userRoleMap);

		return userRoleMap;
	}

	private UserProfile defaultUserProfile(User user) {
		UserProfile userProfile = new UserProfile();

		userProfile.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
		userProfile.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));

		userProfile.setUser(user);
		user.setUserProfile(userProfile);

		return userProfile;
	}

	public List<UserBean> getUsersByAddress(UserAddressBean userAddressBean) {
		UserAddress userAddress = userAddressService.getUserAddress(userAddressBean);
		if(userAddress == null)
			return Collections.emptyList();

		return userAddress.getUsers().stream().map(User :: toBean).collect(Collectors.toList());
	}

	public UserBean addExistingAddress(UUID userId, UserAddressBean userAddressBean) {
		User user = userRepository.findById(userId).orElse(null);
		if(user == null)
			return null;

		UserAddress userAddress = userAddressService.getUserAddress(userAddressBean);
		if(userAddress == null) {
			System.err.println("No Address Found.");
			return null;
		}
		else {
			user.getUserAddresses().add(userAddress);
			userAddress.getUsers().add(user);

			user = userRepository.save(user);
		}

		return user.toBean();
	}

}
