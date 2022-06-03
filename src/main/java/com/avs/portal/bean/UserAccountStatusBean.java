package com.avs.portal.bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class UserAccountStatusBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 2920506511467215606L;
	
	private UUID id;
	
	private UUID userId;
	
	private Boolean isVerified;
	
	private Boolean isActive;
	
	private Boolean isLocked;
	
	private Boolean isBlocked;
	
	private Boolean isDeleted;
	
	private transient LocalDateTime createdOn;
	
	private transient LocalDateTime updatedOn;

	public UUID getId() {
		return id;
	}

	public UserAccountStatusBean setId(UUID userId) {
		this.id = userId;
		return this;
	}

	public Boolean getIsVerified() {
		return isVerified;
	}

	public UUID getUserId() {
		return userId;
	}

	public UserAccountStatusBean setUserId(UUID userId) {
		this.userId = userId;
		return this;
	}

	public UserAccountStatusBean setIsVerified(Boolean isVerified) {
		this.isVerified = isVerified;
		return this;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public UserAccountStatusBean setIsActive(Boolean isActive) {
		this.isActive = isActive;
		return this;
	}

	public Boolean getIsLocked() {
		return isLocked;
	}

	public UserAccountStatusBean setIsLocked(Boolean isLocked) {
		this.isLocked = isLocked;
		return this;
	}

	public Boolean getIsBlocked() {
		return isBlocked;
	}

	public UserAccountStatusBean setIsBlocked(Boolean isBlocked) {
		this.isBlocked = isBlocked;
		return this;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public UserAccountStatusBean setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
		return this;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public UserAccountStatusBean setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
		return this;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public UserAccountStatusBean setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
		return this;
	}

	@Override
	public String toString() {
		return "\nUserAccountStatusBean [ " + 
				"  Id: " + id + 
				", User Id: " + userId + 
				", Is Verified: " + isVerified + 
				", Is Active: "	+ isActive + 
				", Is Locked: " + isLocked + 
				", Is Blocked: " + isBlocked + 
				", Is Deleted: " + isDeleted + 
				", Created On: " + createdOn + 
				", Updated On: " + updatedOn + 
				" ]";
	}

}
