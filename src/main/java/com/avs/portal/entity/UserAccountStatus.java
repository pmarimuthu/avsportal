package com.avs.portal.entity;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.avs.portal.bean.UserAccountStatusBean;

@Entity
@Table(schema = "public", name = "user_account_status_03")
public class UserAccountStatus extends BaseEntity {

	@Id
	@Column(name = "id")
	private UUID id;
	
	@MapsId 
    @OneToOne(mappedBy = "userAccountStatus")
    @JoinColumn(name = "userid")   //same name as id @Column
    private User user;

	@Column(name = "is_verified")
	private Boolean isVerified;
	
	@Column(name = "is_active")
	private Boolean isActive;
	
	@Column(name = "is_locked")
	private Boolean isLocked;
	
	@Column(name = "is_blocked")
	private Boolean isBlocked;
	
	@Column(name = "is_deleted")
	private Boolean isDeleted;
		
	@Column(name = "created_on")
	private Timestamp createdOn;
	
	@Column(name = "updated_on")
	private Timestamp updatedOn;

	public UUID getId() {
		return id;
	}

	public UserAccountStatus setId(UUID userId) {
		this.id = userId;
		return this;
	}

	public User getUser() {
		return user;
	}

	public UserAccountStatus setUser(User user) {
		this.user = user;
		return this;
	}

	public Boolean getIsVerified() {
		return isVerified;
	}

	public UserAccountStatus setIsVerified(Boolean isVerified) {
		this.isVerified = isVerified;
		return this;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public UserAccountStatus setIsActive(Boolean isActive) {
		this.isActive = isActive;
		return this;
	}

	public Boolean getIsLocked() {
		return isLocked;
	}

	public UserAccountStatus setIsLocked(Boolean isLocked) {
		this.isLocked = isLocked;
		return this;
	}

	public Boolean getIsBlocked() {
		return isBlocked;
	}

	public UserAccountStatus setIsBlocked(Boolean isBlocked) {
		this.isBlocked = isBlocked;
		return this;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public UserAccountStatus setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
		return this;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public UserAccountStatus setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
		return this;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public UserAccountStatus setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
		return this;
	}

	@Override
	public UserAccountStatusBean toBean() {
		UserAccountStatusBean userAccountStatusBean = new UserAccountStatusBean()
				.setId(id)
				.setUserId(user.getId())
				.setIsActive(isActive)
				.setIsBlocked(isBlocked)
				.setIsDeleted(isDeleted)
				.setIsLocked(isLocked)
				.setIsVerified(isVerified)
				.setCreatedOn(createdOn.toLocalDateTime())
				.setUpdatedOn(updatedOn.toLocalDateTime());
		
		userAccountStatusBean				
				.setHasError(isHasError())
				.setCustomErrorMessages(getCustomErrorMessages())
				.setThrowable(getThrowable());
		
		return userAccountStatusBean;
	}

	@Override
	public String toString() {
		return "UserAccountStatus [ " +
				"  Id: " + id + 
				", User Id: " +  (user == null ? "NULL" : user.getId()) + 
				", Is Verified: " + isVerified + 
				", Is Active: " + isActive + 
				", Is Locked: " + isLocked + 
				", Is Blocked: " + isBlocked + 
				", Is Deleted: " + isDeleted + 
				", Created On: " + createdOn + 
				", Updated On: " + updatedOn + 
				" ]";
	}
	
}
