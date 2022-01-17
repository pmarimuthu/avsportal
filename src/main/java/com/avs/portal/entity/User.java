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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.avs.portal.bean.UserBean;

@Entity
@Table(schema = "public", name = "user_01")
public class User extends BaseEntity {

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
    @JoinColumn(name = "user")
    private UserCredential userCredential;
	
	@OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @JoinColumn(name = "user")
    private UserAccountStatus userAccountStatus;
	
	@OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @JoinColumn(name = "user")
    private UserPreferences userPreferences;
	
	@OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @JoinColumn(name = "user")
    private UserInformation userInformation;
	
	@OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @JoinColumn(name = "user")
    private UserProfile userProfile;
	
	@OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @JoinColumn(name = "user")
	private UserFamilyMap userFamilyMap; // ref
	
	@OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @JoinColumn(name = "user")
    private UserRoleMap userRoleMap;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserRelationToMeMap> userRelationToMeMap = new ArrayList<UserRelationToMeMap>();
	
	@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_useraddress_join", 
        joinColumns = { @JoinColumn(name = "USER_ID") }, 
        inverseJoinColumns = { @JoinColumn(name = "USERADDRESS_ID") })
	private List<UserAddress> userAddresses = new ArrayList<UserAddress>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Notification> notifications = new ArrayList<Notification>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserVerification> userVerifications = new ArrayList<UserVerification>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<LoginHistory> loginHistories = new ArrayList<LoginHistory>();

	@Transient
	public List<UserInformation> distinctParentFamilyHeads = new ArrayList<>();

	@Transient
	public List<UserInformation> distinctFamilyHeads = new ArrayList<>();
	
	List<UserAddress> internalAddUserAddress(UserAddress userAddress) {
		this.userAddresses.add(userAddress);
		return this.userAddresses;
	}
	
	List<UserAddress> internalRemoveUserAddress(UserAddress userAddress) {
		this.userAddresses.remove(userAddress);
		return this.userAddresses;
	}

	public User addUserVerification(UserVerification userVerification) {
		userVerification.setUser(this);
		return this;
	}
	
	public User removeUserVerification(UserVerification userVerification) {
		userVerification.setUser(null);
		return this;
	}
	
	List<UserVerification> internalAddUserVerification(UserVerification userVerification) {
		this.userVerifications.add(userVerification);
		return this.userVerifications;
	}
	
	List<UserVerification> internalRemoveUserVerification(UserVerification userVerification) {
		this.userVerifications.remove(userVerification);
		return this.userVerifications;
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

	public User addNotification(Notification notification) {
		notification.setUser(this);
		return this;
	}
	
	public User removeNotification(Notification notification) {
		notification.setUser(null);
		return this;
	}

	User internalAddNotification(Notification notification) {
		notifications.add(notification);
		return this;
	}
	
	User internalRemoveNotification(Notification notification) {
		notifications.remove(notification);
		return this;
	}

	// Ref <<<< https://xebia.com/blog/jpa-implementation-patterns-bidirectional-assocations/
	
	public UUID getId() {
		return id;
	}

	public User setId(UUID id) {
		this.id = id;
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

	public UserFamilyMap getUserFamilyMap() {
		return userFamilyMap;
	}

	public User setUserFamilyMap(UserFamilyMap userFamilyMap) {
		this.userFamilyMap = userFamilyMap;
		return this;
	}

	public UserRoleMap getUserRoleMap() {
		return userRoleMap;
	}

	public User setUserRoleMap(UserRoleMap userRoleMap) {
		this.userRoleMap = userRoleMap;
		return this;
	}

	public List<UserRelationToMeMap> getUserRelationToMeMap() {
		return userRelationToMeMap;
	}

	public User setUserRelationToMeMap(List<UserRelationToMeMap> userRelationToMeMap) {
		this.userRelationToMeMap = userRelationToMeMap;
		return this;
	}

	public List<UserAddress> getUserAddresses() {
		return userAddresses;
	}

	public User setUserAddresses(List<UserAddress> userAddresses) {
		this.userAddresses = userAddresses;
		return this;
	}

	public List<Notification> getNotifications() {
		return notifications;
	}

	public User setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
		return this;
	}

	public List<UserVerification> getUserVerifications() {
		return userVerifications;
	}

	public User setUserVerifications(List<UserVerification> userVerifications) {
		this.userVerifications = userVerifications;
		return this;
	}

	public List<LoginHistory> getLoginHistories() {
		return loginHistories;
	}

	public User setLoginHistories(List<LoginHistory> loginHistories) {
		this.loginHistories = loginHistories;
		return this;
	}

	public List<UserInformation> getDistinctParentFamilyHeads() {
		return distinctParentFamilyHeads;
	}

	public User setDistinctParentFamilyHeads(List<UserInformation> distinctParentFamilyHeads) {
		this.distinctParentFamilyHeads = distinctParentFamilyHeads;
		return this;
	}

	public List<UserInformation> getDistinctFamilyHeads() {
		return distinctFamilyHeads;
	}

	public User setDistinctFamilyHeads(List<UserInformation> distinctFamilyHeads) {
		this.distinctFamilyHeads = distinctFamilyHeads;
		return this;
	}

	public UserBean toBean() {
		UserBean userBean = new UserBean()
				.setId(id)
				.setPhone(phone)
				.setEmail(email)
				.setCreatedOn(createdOn.toLocalDateTime())
				.setUpdatedOn(updatedOn.toLocalDateTime())
			
				.setUserCredential(userCredential == null ? null : userCredential.toBean())
				.setUserAccountStatus(userAccountStatus == null ? null : userAccountStatus.toBean())
				.setUserPreferences(userPreferences == null ? null : userPreferences.toBean())
				.setUserInformation(userInformation == null ? null : userInformation.toBean())
				.setUserProfile(userProfile == null ? null : userProfile.toBean())
				.setUserFamilyMap(userFamilyMap == null ? null : userFamilyMap.toBean())
				
				.setUserRoleMap(userRoleMap == null ? null : userRoleMap.toBean())
				
				.setUserAddresses(userAddresses.stream().map(UserAddress :: toBean).collect(Collectors.toList()))
				.setUserVerifications(userVerifications.stream().map(UserVerification :: toBean).collect(Collectors.toList()))
				.setUserRelationToMeMap(userRelationToMeMap.stream().map(UserRelationToMeMap :: toBean).collect(Collectors.toList()));
		
		/*
		userBean.setLoginHistories(loginHistories.stream()
						.sorted(Comparator.comparing(LoginHistory :: getUpdatedOn).reversed())
						.map(LoginHistory :: toBean)
						.limit(10).
						collect(Collectors.toList()));
		*/
		
		// userBean.setNotifications(notifications.stream().map(Notification :: toBean).collect(Collectors.toList()));
		
		userBean.setHasError(isHasError());
		userBean.setCustomErrorMessages(getCustomErrorMessages());
		userBean.setThrowable(getThrowable());
		
		return userBean;		
	}

	@Override
	public String toString() {
		return "\nUser [ " + 
				"Id: " + id + 
				", Phone: " + phone + 
				", Email: " + email + 
				", CreatedOn: " + createdOn + 
				", UpdatedOn: " + updatedOn + 
				
				", User Credential: " + (userCredential != null ? userCredential.toString() : "NULL") + 
				", User Account Status: " + (userAccountStatus != null ? userAccountStatus.toString() : "NULL") + 
				", User Preferences: " + (userPreferences != null ? userPreferences.toString() : "NULL") + 
				", User Information: " + (userInformation != null ? userInformation.toString() : "NULL") + 
				", User Profile: " + (userProfile != null ? userProfile.toString() : "NULL") + 
				", User Family Map: " + (userFamilyMap != null ? userFamilyMap.toString() : "NULL") + 
				", User Role Map: " + (userRoleMap != null ? userRoleMap.toString() : "NULL") + 
				", User RelationToMe Map(s): " + (userRelationToMeMap != null ? userRelationToMeMap.toString() : "EMPTY") + 
				", User Address(s): " + (userAddresses != null ? userAddresses.toString() : "EMPTY") + 
				", Notification(s): " + (notifications != null ? notifications.toString() : "NULL") +
				", User Verification(s): " + (userVerifications != null ? userVerifications.toString() : "EMPTY") + 
				", Login History(s): " + (loginHistories != null ? loginHistories.toString() : "NULL") +
				" ]";
	}
	
}
