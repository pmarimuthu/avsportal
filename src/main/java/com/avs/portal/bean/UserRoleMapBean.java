package com.avs.portal.bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import com.avs.portal.enums.RoleEnum;

public class UserRoleMapBean implements Serializable {

	private static final long serialVersionUID = 7032801748899001443L;

	private UUID id;
	
	private RoleEnum role;
	
	private LocalDateTime createdOn;
	
	private LocalDateTime updatedOn;

	public UUID getId() {
		return id;
	}

	public UserRoleMapBean setId(UUID id) {
		this.id = id;
		return this;
	}

	public RoleEnum getRole() {
		return role;
	}

	public UserRoleMapBean setRole(RoleEnum role) {
		this.role = role;
		return this;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public UserRoleMapBean setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
		return this;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public UserRoleMapBean setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
		return this;
	}

	@Override
	public String toString() {
		return "\nUserRoleMapBean [ Id: " + id + ", Role: " + role + ", Created On: " + createdOn
				+ ", Updated On: " + updatedOn + " ]";
	}
	
}
