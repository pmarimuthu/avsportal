package com.avs.portal.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.avs.portal.bean.UserBean;

@Entity
@Table(schema = "public", name = "user_01")
public class User {

	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
	private UUID id;	
	
	@Column(name = "phone", nullable = false, unique = true)
	private Long phone;
	
	@Column(name = "email", unique = true)
	private String email;
	
	@Column(name = "created_on")
	private Timestamp createdOn;
	
	@Column(name = "updated_on")
	private Timestamp updatedOn;
	
	@OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserCredential userCredential;
	
	@OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserAccountStatus userAccountStatus;
	
	@OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private TempPassword tempPassword;
	
	@OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserPreferences userPreferences;
	
	@OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserInformation userInformation;
	
	@OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserProfile userProfile;
	
	@OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserAddress userAddress;
	
	@OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserReferrerMap userReferrerMap;
	
	@OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserRoleMap userRoleMap;
	
	@OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserVerification userVerification;
	
	@OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserRelationToMeMap userRelationToMeMap;
	
	@OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Notification notification;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
	private List<LoginHistory> loginHistories = new ArrayList<>();

	public UUID getId() {
		return id;
	}

	public User setId(UUID id) {
		this.id = id;
		return this;
	}

	public UserCredential getUserCredential() {
		return userCredential;
	}

	public User setUserCredential(UserCredential userCredential) {
		this.userCredential = userCredential;
		return this;
	}

	public UserAccountStatus getUserAccountStatus() {
		return userAccountStatus;
	}

	public User setUserAccountStatus(UserAccountStatus userAccountStatus) {
		this.userAccountStatus = userAccountStatus;
		return this;
	}

	public TempPassword getTempPassword() {
		return tempPassword;
	}

	public User setTempPassword(TempPassword tempPassword) {
		this.tempPassword = tempPassword;
		return this;
	}

	public UserPreferences getUserPreferences() {
		return userPreferences;
	}

	public User setUserPreferences(UserPreferences userPreferences) {
		this.userPreferences = userPreferences;
		return this;
	}

	public UserInformation getUserInformation() {
		return userInformation;
	}

	public User setUserInformation(UserInformation userInformation) {
		this.userInformation = userInformation;
		return this;
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public User setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
		return this;
	}

	public UserAddress getUserAddress() {
		return userAddress;
	}

	public User setUserAddress(UserAddress userAddress) {
		this.userAddress = userAddress;
		return this;
	}

	public UserReferrerMap getUserReferrerMap() {
		return userReferrerMap;
	}

	public User setUserReferrerMap(UserReferrerMap userReferrerMap) {
		this.userReferrerMap = userReferrerMap;
		return this;
	}

	public UserRoleMap getUserRoleMap() {
		return userRoleMap;
	}

	public User setUserRoleMap(UserRoleMap userRoleMap) {
		this.userRoleMap = userRoleMap;
		return this;
	}

	public UserVerification getUserVerification() {
		return userVerification;
	}

	public User setUserVerification(UserVerification userVerification) {
		this.userVerification = userVerification;
		return this;
	}

	public UserRelationToMeMap getUserRelationToMeMap() {
		return userRelationToMeMap;
	}

	public User setUserRelationToMeMap(UserRelationToMeMap userRelationToMeMap) {
		this.userRelationToMeMap = userRelationToMeMap;
		return this;
	}

	public List<LoginHistory> getLoginHistories() {
		//return Collections.unmodifiableList(loginHistories);
		return loginHistories;
	}

	public User setLoginHistories(List<LoginHistory> loginHistories) {
		this.loginHistories = loginHistories;
		return this;
	}
	
	// Ref >>>> https://xebia.com/blog/jpa-implementation-patterns-bidirectional-assocations/
	
	public User addLoginHistory(LoginHistory loginHistory) {
		loginHistory.setUser(this);
		return this;
	}
	
	public User removeLoginHistory(LoginHistory loginHistory) {
		loginHistory.setUser(null);
		return this;
	}

	User internalAddLoginHistory(LoginHistory loginHistory) {
		loginHistories.add(loginHistory);
		return this;
	}
	
	User internalRemoveLoginHistory(LoginHistory loginHistory) {
		loginHistories.remove(loginHistory);
		return this;
	}

	// Ref <<<< https://xebia.com/blog/jpa-implementation-patterns-bidirectional-assocations/
	
	public Notification getNotification() {
		return notification;
	}

	public User setNotification(Notification notification) {
		this.notification = notification;
		return this;
	}

	public Long getPhone() {
		return phone;
	}

	public User setPhone(Long phone) {
		this.phone = phone;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public User setEmail(String email) {
		this.email = email;
		return this;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public User setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
		return this;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public User setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
		return this;
	}
	
	public UserBean toBean() {
		return new UserBean()
				.setId(id)
				.setPhone(phone)
				.setEmail(email)
				.setCreatedOn(createdOn.toLocalDateTime())
				.setUpdatedOn(updatedOn.toLocalDateTime())
			
				.setUserCredential(userCredential.toBean())
				.setUserAccountStatus(userAccountStatus.toBean())
				.setTempPassword(tempPassword.toBean())
				.setUserPreferences(userPreferences.toBean())
				.setUserInformation(userInformation.toBean())
				.setUserProfile(userProfile.toBean())
				.setUserAddress(userAddress.toBean())
				.setUserReferrerMap(userReferrerMap.toBean())
				.setUserRoleMap(userRoleMap.toBean())
				.setUserVerification(userVerification.toBean())
				.setUserRelationToMeMap(userRelationToMeMap.toBean())
				.setLoginHistories(loginHistories.stream().map(LoginHistory::toBean).collect(Collectors.toList()))
				.setNotification(notification.toBean());
	}

	@Override
	public String toString() {
		return "\nUser [ " + 
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
				", User Address: " + userAddress + 
				", User Referrer Map: " + userReferrerMap + 
				", User Role Map: " + userRoleMap + 
				", User Verification: " + userVerification + 
				", User RelationToMe Map: " + userRelationToMeMap + 
				", Login History: " + loginHistories +
				", Notification=" + notification +
				"]";
	}
	
}
