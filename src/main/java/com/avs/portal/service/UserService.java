package com.avs.portal.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.avs.portal.entity.UserFamilyMap;
import com.avs.portal.entity.UserInformation;
import com.avs.portal.entity.UserPreferences;
import com.avs.portal.entity.UserProfile;
import com.avs.portal.entity.UserReferral;
import com.avs.portal.entity.UserRoleMap;
import com.avs.portal.entity.UserVerification;
import com.avs.portal.enums.AddressTypeEnum;
import com.avs.portal.enums.LanguageEnum;
import com.avs.portal.enums.RoleEnum;
import com.avs.portal.enums.VerificationStatusEnum;
import com.avs.portal.enums.VerificationSubjectEnum;
import com.avs.portal.enums.VisibilityEnum;
import com.avs.portal.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserAddressService userAddressService;

	public List<UUID> getAllUserId() {		
		return userRepository.findNativeAllId().stream()
				.map(uuid -> UUID.fromString(uuid.trim())).collect(Collectors.toList());
	}
	
	// FIND (Id or Email & Phone)
	public List<UserBean> getUsersByIdOrEmailAndPhone(UserBean userBean) {
		if(userBean == null || (userBean.getId() == null && userBean.getEmail() == null) || (userBean.getPhone() == null))
			return Collections.emptyList();

		UUID foundUUID = null;

		long start = System.currentTimeMillis();
		foundUUID = userRepository.findIdByIdOrEmailAndPhone(userBean.getId(), userBean.getEmail(), userBean.getPhone()).stream().findFirst().orElse(null);
		long end = System.currentTimeMillis();		
		System.out.println(end - start);

		start = System.currentTimeMillis();
		foundUUID = userRepository.findByNativeTheUserByIdOrEmailAndPhone(userBean.getId(), userBean.getEmail(), userBean.getPhone()).stream().findFirst().orElse(null);
		end = System.currentTimeMillis();		
		System.out.println(end - start);

		List<UserBean> userBeans = new ArrayList<>();
		User user = userRepository.findById(foundUUID).orElse(null);
		if(user != null) {
			userBeans.add(user.toBean());			
		}

		return userBeans;
	}

	// READ {ALL}
	public List<UserBean> getUsers() {
		return userRepository.findAllByIdIn(userRepository.findNativeAllId().stream()
				.map(uuid -> UUID.fromString(uuid.trim())).collect(Collectors.toList())).stream()
				.map(User::toBean).collect(Collectors.toList());
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
		if(userBean == null)
			return userBean;

		UserBean theUserBean = userBean.getValidatedUserBean(userBean);
		if(theUserBean.getHasError())
			return theUserBean;

		// User :: user_01
		// ---------------
		User user = new User();
		user.setPhone(theUserBean.getPhone());
		user.setEmail(theUserBean.getEmail());
		user.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
		user.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));

		defaultUserCredential(user);
		defaultUserAccountStatus(user);
		defaultUserPreferences(user);
		defaultUserInformation(user);
		defaultUserProfile(user);
		defaultUserAddresses(user);
		defaultUserFamilyMap(user);
		defaultUserRoleMap(user);
		defaultUserReferrer(user);
		defaultUserVerifications(user);

		// userRelationToMeMap, userAddresses, notifications, userVerifications, loginHistories

		try {
			user = userRepository.save(user);
		} catch (Exception e) {
			return user.toBean()
					.setHasError(true)
					.setCustomErrorMessages(Arrays.asList(e.getMessage()));

		}

		return user.toBean();
	}

	// UPDATE
	public UserBean updateUser(UserBean bean) {
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
	public UserBean deleteUser(UserBean userBean) {
		if(userBean == null || userBean.getId() == null)
			return userBean;

		User entity = userRepository.findById(userBean.getId()).orElse(null);
		if(entity == null)
			return null;

		userRepository.delete(entity);

		return entity.toBean();
	}

	private UserBean getUserById(UUID id) {
		User user = userRepository.findById(id).orElse(null);
		if(user == null)
			return null;

		return user.toBean();
	}

	private UserBean getUserByEmail(String email) {
		List<User> entities = userRepository.findByEmail(email);
		if(entities.size() != 1)
			return null;

		User user = entities.get(0);

		return user.toBean();
				//.setDistinctFamilyHeads(userFamilyMapService.listDistinctFamilyHeads());
	}

	private UserBean getUserByPhone(Long phone) {
		List<User> entities = userRepository.findByPhone(phone);
		if(entities.size() != 1)
			return null;

		User user = entities.get(0);
		return user.toBean();
	}

	// UserCredential :: user_credential_02
	// -------------------------------------------
	private UserCredential defaultUserCredential(User user) {
		UserCredential userCredential = new UserCredential()
				.setPassword("password") // CommonUtil.generateDefaultPassword())
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

	private void defaultUserAddresses(User user) {
		UserAddress nativeAddress = new UserAddress()
				.setAddressType(AddressTypeEnum.NATIVE)
				.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()))
				.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));

		user.getUserAddresses().add(nativeAddress);
		nativeAddress.getUsers().add(user);

		UserAddress livingAddress = new UserAddress()
				.setAddressType(AddressTypeEnum.LIVING)
				.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()))
				.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));

		user.getUserAddresses().add(livingAddress);
		livingAddress.getUsers().add(user);

		UserAddress officeAddress = new UserAddress()
				.setAddressType(AddressTypeEnum.OFFICE)
				.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()))
				.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));

		user.getUserAddresses().add(officeAddress);
		officeAddress.getUsers().add(user);
	}

	private UserFamilyMap defaultUserFamilyMap(User user) {
		UserFamilyMap userFamilyMap = new UserFamilyMap();

		userFamilyMap.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
		userFamilyMap.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));

		userFamilyMap.setUser(user);
		user.setUserFamilyMap(userFamilyMap);

		return userFamilyMap;
	}

	private UserReferral defaultUserReferrer(User user) {
		UserReferral userReferral = new UserReferral();

		userReferral.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
		userReferral.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));

		return userReferral;		
	}

	private List<UserVerification> defaultUserVerifications(User user) {
		UserVerification userSubjectVerification = new UserVerification();
		userSubjectVerification.setUser(user);
		userSubjectVerification.setVerificationSubject(VerificationSubjectEnum.USER);
		userSubjectVerification.setVerificationStatus(VerificationStatusEnum.NOT_VERIFIED);
		userSubjectVerification.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
		userSubjectVerification.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));

		user.getUserVerifications().add(userSubjectVerification);

		UserVerification profileSubjectVerification = new UserVerification();
		profileSubjectVerification.setUser(user);
		profileSubjectVerification.setVerificationSubject(VerificationSubjectEnum.PROFILE);
		profileSubjectVerification.setVerificationStatus(VerificationStatusEnum.NOT_VERIFIED);
		profileSubjectVerification.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
		profileSubjectVerification.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));

		user.getUserVerifications().add(profileSubjectVerification);

		return user.getUserVerifications();
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
