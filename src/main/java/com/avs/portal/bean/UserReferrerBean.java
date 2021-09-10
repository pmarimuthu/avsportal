package com.avs.portal.bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class UserReferrerBean implements Serializable {

	private static final long serialVersionUID = 9055563320640094809L;

	private UUID id;
	
	private UUID userId;
	
	private UUID referredByUserId;
	
	private LocalDateTime createdOn;
	
	private LocalDateTime updatedOn;

	public UUID getId() {
		return id;
	}

	public UserReferrerBean setId(UUID userId) {
		this.id = userId;
		return this;
	}

	public UUID getUserId() {
		return userId;
	}

	public UserReferrerBean setUserId(UUID userId) {
		this.userId = userId;
		return this;
	}

	public UUID getReferredByUserId() {
		return referredByUserId;
	}

	public UserReferrerBean setReferredByUserId(UUID referredByUserId) {
		this.referredByUserId = referredByUserId;
		return this;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public UserReferrerBean setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
		return this;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public UserReferrerBean setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
		return this;
	}

	@Override
	public String toString() {
		return "\nUserReferrerBean [ " + 
				"  Id: " + id + 
				", User Id: " + userId + 
				", Referred By User Id: " + referredByUserId + 
				", Created On: " + createdOn + 
				", Updated On: " + updatedOn + 
				" ]";
	}

}
