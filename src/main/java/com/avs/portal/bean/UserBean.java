package com.avs.portal.bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import com.avs.portal.util.CommonUtil;

public class UserBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = -5996259344239551268L;

	private UUID id;

	private Long phone;

	private String email;

	private LocalDateTime createdOn;

	private LocalDateTime updatedOn;

	// Relations

	private UserCredentialBean userCredential;

	private UserAccountStatusBean userAccountStatus;

	private UserPreferencesBean userPreferences;

	private UserInformationBean userInformation;

	private UserProfileBean userProfile;

	private UserFamilyMapBean userFamilyMap;

	private UserReferralBean userReferrer;

	private UserRoleMapBean userRoleMap;
	
	private UserReferralMapBean userReferralMap;

	private Collection<UserRelationToMeMapBean> userRelationToMeMap = new ArrayList<>();

	private Collection<UserAddressBean> userAddresses = new ArrayList<>();

	public List<UserInformationBean> distinctFamilyHeads = new ArrayList<>();

	private Collection<NotificationBean> notifications = new ArrayList<>();

	private Collection<UserVerificationBean> userVerifications = new ArrayList<>();

	private Collection<LoginHistoryBean> loginHistories = new ArrayList<>();
	
	private String imageSrc = "https://source.unsplash.com/random/128x128";

	public UUID getId() {
		return id;
	}

	public UserBean setId(UUID id) {
		this.id = id;
		return this;
	}

	public Long getPhone() {
		return phone;
	}

	public UserBean setPhone(Long phone) {
		this.phone = phone;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public UserBean setEmail(String email) {
		this.email = email;
		return this;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public UserBean setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
		return this;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public UserBean setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
		return this;
	}

	public UserCredentialBean getUserCredential() {
		return userCredential;
	}

	public UserBean setUserCredential(UserCredentialBean userCredential) {
		this.userCredential = userCredential;
		return this;
	}

	public UserAccountStatusBean getUserAccountStatus() {
		return userAccountStatus;
	}

	public UserBean setUserAccountStatus(UserAccountStatusBean userAccountStatus) {
		this.userAccountStatus = userAccountStatus;
		return this;
	}

	public UserPreferencesBean getUserPreferences() {
		return userPreferences;
	}

	public UserBean setUserPreferences(UserPreferencesBean userPreferences) {
		this.userPreferences = userPreferences;
		return this;
	}

	public UserInformationBean getUserInformation() {
		return userInformation;
	}

	public UserBean setUserInformation(UserInformationBean userInformation) {
		this.userInformation = userInformation;
		return this;
	}

	public UserProfileBean getUserProfile() {
		return userProfile;
	}

	public UserBean setUserProfile(UserProfileBean userProfile) {
		this.userProfile = userProfile;
		return this;
	}

	public UserFamilyMapBean getUserFamilyMap() {
		return userFamilyMap;
	}

	public UserBean setUserFamilyMap(UserFamilyMapBean userFamilyMap) {
		this.userFamilyMap = userFamilyMap;
		return this;
	}

	public UserReferralBean getUserReferrer() {
		return userReferrer;
	}

	public UserBean setUserReferrer(UserReferralBean userReferrer) {
		this.userReferrer = userReferrer;
		return this;
	}

	public Collection<UserAddressBean> getUserAddresses() {
		return userAddresses;
	}

	public UserBean setUserAddresses(Collection<UserAddressBean> userAddresses) {
		this.userAddresses = userAddresses;
		return this;
	}

	public Collection<UserVerificationBean> getUserVerifications() {
		return userVerifications;
	}

	public UserBean setUserVerifications(Collection<UserVerificationBean> userVerifications) {
		this.userVerifications = userVerifications;
		return this;
	}

	public UserRoleMapBean getUserRoleMap() {
		return userRoleMap;
	}

	public UserBean setUserRoleMap(UserRoleMapBean userRoleMap) {
		this.userRoleMap = userRoleMap;
		return this;
	}

	public UserReferralMapBean getUserReferralMap() {
		return userReferralMap;
	}

	public UserBean setUserReferralMap(UserReferralMapBean userReferralMap) {
		this.userReferralMap = userReferralMap;
		return this;
	}

	public List<UserInformationBean> getDistinctFamilyHeads() {
		return distinctFamilyHeads;
	}

	public UserBean setDistinctFamilyHeads(List<UserInformationBean> distinctFamilyHeads) {
		this.distinctFamilyHeads = distinctFamilyHeads;
		return this;
	}

	public Collection<UserRelationToMeMapBean> getUserRelationToMeMap() {
		return userRelationToMeMap;
	}

	public UserBean setUserRelationToMeMap(Collection<UserRelationToMeMapBean> userRelationToMeMap) {
		this.userRelationToMeMap = userRelationToMeMap;
		return this;
	}

	public Collection<LoginHistoryBean> getLoginHistories() {
		return loginHistories;
	}


	public Collection<LoginHistoryBean> addLoginHistory(LoginHistoryBean bean) {
		loginHistories.add(bean);
		return loginHistories;
	}

	public UserBean setLoginHistories(Collection<LoginHistoryBean> loginHistories) {
		this.loginHistories = loginHistories;
		return this;
	}

	public Collection<NotificationBean> getNotifications() {
		return notifications;
	}

	public UserBean setNotifications(Collection<NotificationBean> notifications) {
		this.notifications = notifications;
		return this;
	}

	public String getImageSrc() {
		return imageSrc;
	}

	public void setImageSrc(String imageSrc) {
		this.imageSrc = imageSrc;
	}

	@Override
	public UserBean setHasError(boolean hasError) {
		super.setHasError(hasError);
		return this;
	}

	@Override
	public UserBean setCustomErrorMessages(List<String> customErrorMessages) {
		super.setCustomErrorMessages(customErrorMessages);
		return this;
	}

	@Override
	public UserBean setThrowable(Throwable throwable) {
		super.setThrowable(throwable);
		return this;
	}

	public UserBean getValidatedUserBean(UserBean userBean) {
		boolean hasError = false;
		if(!CommonUtil.isValidPhone(userBean.getPhone())) {
			userBean.getCustomErrorMessages().add("Invalid Phone number");
			hasError = true;
		}
		if(!CommonUtil.isValidEmail(userBean.getEmail())) {
			userBean.getCustomErrorMessages().add("Invalid Email address");
			hasError = true;
		}
		userBean.setHasError(hasError);
		
		return userBean;
	}

	@Override
	public String toString() {
		return super.toString() + 
				"\nUserBean [ " +
				"Id: " + id + 
				", Phone: " + phone + 
				", Email: " + email + 
				", CreatedOn: " + createdOn + 
				", UpdatedOn: " + updatedOn + 

				", User Credential: " + userCredential + 
				", User Account Status: " + userAccountStatus + 
				", User Preferences: " + userPreferences + 
				", User Information: " + userInformation + 
				", User Profile: " + userProfile +
				", User Family Map: " + userFamilyMap +
				", User Referrer Map: " + userReferrer + 
				", UserRole Map: " + userRoleMap + 
				", User RelationToMe Map: " + userRelationToMeMap + 
				", User Addresse(s): " + userAddresses + 
				", User Verification(s): " + userVerifications + 
				", Login History(s): " + loginHistories + 
				", Notification(s): " + notifications + 
				", Distinct Family-Head-IDs: " + distinctFamilyHeads +
				"]";

	}

}
