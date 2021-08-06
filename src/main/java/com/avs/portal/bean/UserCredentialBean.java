package com.avs.portal.bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class UserCredentialBean implements Serializable {

	private static final long serialVersionUID = -3205984755210708781L;
	
	private UUID id;
	
	private String password;
	
	private LocalDateTime createdOn;
	
	private LocalDateTime updatedOn;

	public UUID getId() {
		return id;
	}

	public UserCredentialBean setId(UUID userId) {
		this.id = userId;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public UserCredentialBean setPassword(String password) {
		this.password = password;
		return this;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public UserCredentialBean setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
		return this;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public UserCredentialBean setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
		return this;
	}

	@Override
	public String toString() {
		return "\nUserCredentialBean [ Id: " + id + ", Password: " + password + ", Created On: "
				+ createdOn + ", Updated On: " + updatedOn + " ]";
	}

}
