package com.avs.portal.bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import com.avs.portal.util.CommonUtil;

public class UserBean implements Serializable {

	private static final long serialVersionUID = -5996259344239551268L;
	
	private UUID id;
	
	private Long phone;
	
	private String email;
	
	private LocalDateTime createdOn;
	
	private LocalDateTime updatedOn;
	
	// Relations
	
    private UserCredentialBean userCredential;
	
    private UserAccountStatusBean userAccountStatus;
	
    private TempPasswordBean tempPassword;
	
    private UserPreferencesBean userPreferences;
	
    private UserInformationBean userInformation;
	
    private UserProfileBean userProfile;
	
    private UserReferrerMapBean userReferrerMap;
	
    private UserRoleMapBean userRoleMap;
	
    private UserRelationToMeMapBean userRelationToMeMap;
	
    private Collection<UserAddressBean> userAddresses = Collections.emptyList();
	
    private Collection<NotificationBean> notifications = Collections.emptyList();
    
    private Collection<UserVerificationBean> userVerifications = Collections.emptyList();
	
    private Collection<LoginHistoryBean> loginHistories = Collections.emptyList();

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

	public TempPasswordBean getTempPassword() {
		return tempPassword;
	}

	public UserBean setTempPassword(TempPasswordBean tempPassword) {
		this.tempPassword = tempPassword;
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

	public UserReferrerMapBean getUserReferrerMap() {
		return userReferrerMap;
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

	public UserBean setUserReferrerMap(UserReferrerMapBean userReferrerMap) {
		this.userReferrerMap = userReferrerMap;
		return this;
	}

	public UserRoleMapBean getUserRoleMap() {
		return userRoleMap;
	}

	public UserBean setUserRoleMap(UserRoleMapBean userRoleMap) {
		this.userRoleMap = userRoleMap;
		return this;
	}

	public UserRelationToMeMapBean getUserRelationToMeMap() {
		return userRelationToMeMap;
	}

	public UserBean setUserRelationToMeMap(UserRelationToMeMapBean userRelationToMeMap) {
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
	
	public boolean isValid(UserBean user) {
		if(CommonUtil.isValidPhone(user.getPhone()))
			if(CommonUtil.isValidEmail(user.getEmail()))				
				return true;				
			
		return false;
	}

	@Override
	public String toString() {
		return "\nUserBean [ " +
				"Id: " + id + 
				", Phone: " + phone + 
				", Email: " + email + 
				", CreatedOn: " + createdOn + 
				", UpdatedOn: " + updatedOn + 
				
				", User Credential: " + userCredential + 
				", User Account Status: " + userAccountStatus + 
				", Temp Password: " + tempPassword + 
				", User Preferences: " + userPreferences + 
				", User Information: " + userInformation + 
				", User Profile: " + userProfile + 
				", User Referrer Map: " + userReferrerMap + 
				", UserRole Map: " + userRoleMap + 
				", User RelationToMe Map: " + userRelationToMeMap + 
				", User Addresse(s): " + userAddresses + 
				", User Verification(s): " + userVerifications + 
				", Login History(s): " + loginHistories + 
				", Notification(s): " + notifications + 
				"]";
	}

}
