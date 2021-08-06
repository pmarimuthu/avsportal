package com.avs.portal.bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class TempPasswordBean implements Serializable {

	private static final long serialVersionUID = 3110786373367303923L;
	
	private UUID id;
	
	private String generatedPassword;
	
	private Boolean isUsed;
	
	private LocalDateTime createdOn;
	
	private LocalDateTime updatedOn;

	public UUID getId() {
		return id;
	}

	public TempPasswordBean setId(UUID userId) {
		this.id = userId;
		return this;
	}

	public String getGeneratedPassword() {
		return generatedPassword;
	}

	public TempPasswordBean setGeneratedPassword(String generatedPassword) {
		this.generatedPassword = generatedPassword;
		return this;
	}

	public Boolean getIsUsed() {
		return isUsed;
	}

	public TempPasswordBean setIsUsed(Boolean isUsed) {
		this.isUsed = isUsed;
		return this;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public TempPasswordBean setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
		return this;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public TempPasswordBean setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
		return this;
	}

	@Override
	public String toString() {
		return "\nTempPasswordBean [ Id: " + id + ", Generated Password: " + generatedPassword
				+ ", Is Used: " + isUsed + ", Created On: " + createdOn + ", Updated On: " + updatedOn + " ]";
	}

}
