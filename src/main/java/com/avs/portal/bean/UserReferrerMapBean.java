package com.avs.portal.bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class UserReferrerMapBean implements Serializable {

	private static final long serialVersionUID = 9055563320640094809L;

	private UUID id;
	
	private UUID userId;
	
	private UUID referredByUserId;
	
	private LocalDateTime createdOn;
	
	private LocalDateTime updatedOn;

	public UUID getId() {
		return id;
	}

	public UserReferrerMapBean setId(UUID userId) {
		this.id = userId;
		return this;
	}

	public UUID getUserId() {
		return userId;
	}

	public UserReferrerMapBean setUserId(UUID userId) {
		this.userId = userId;
		return this;
	}

	public UUID getReferredByUserId() {
		return referredByUserId;
	}

	public UserReferrerMapBean setReferredByUserId(UUID referredByUserId) {
		this.referredByUserId = referredByUserId;
		return this;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public UserReferrerMapBean setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
		return this;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public UserReferrerMapBean setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
		return this;
	}

	@Override
	public String toString() {
		return "\nUserReferrerMapBean [ " + 
				"  Id: " + id + 
				", User Id: " + userId + 
				", Referred By User Id: " + referredByUserId + 
				", Created On: " + createdOn + 
				", Updated On: " + updatedOn + 
				" ]";
	}

}
