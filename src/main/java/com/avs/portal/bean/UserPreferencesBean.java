package com.avs.portal.bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import com.avs.portal.enums.LanguageEnum;
import com.avs.portal.enums.VisibilityEnum;

public class UserPreferencesBean implements Serializable {

	private static final long serialVersionUID = 7773159516719852765L;
	
	private UUID userId;
	
	private VisibilityEnum visibilityLevel;
	
	private LanguageEnum language;
	
	private Boolean advertisementOpt;

	
	private LocalDateTime createdOn;
	
	private LocalDateTime updatedOn;
	
	public UUID getUserId() {
		return userId;
	}

	public UserPreferencesBean setUserId(UUID userId) {
		this.userId = userId;
		return this;
	}

	public VisibilityEnum getVisibilityLevel() {
		return visibilityLevel;
	}

	public UserPreferencesBean setVisibilityLevel(VisibilityEnum visibilityLevel) {
		this.visibilityLevel = visibilityLevel;
		return this;
	}

	public LanguageEnum getLanguage() {
		return language;
	}

	public UserPreferencesBean setLanguage(LanguageEnum language) {
		this.language = language;
		return this;
	}

	public Boolean getAdvertisementOpt() {
		return advertisementOpt;
	}

	public UserPreferencesBean setAdvertisementOpt(Boolean advertisementOpt) {
		this.advertisementOpt = advertisementOpt;
		return this;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public UserPreferencesBean setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
		return this;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public UserPreferencesBean setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
		return this;
	}

	@Override
	public String toString() {
		return "\nUserPreferencesBean [userId=" + userId + ", visibilityLevel=" + visibilityLevel + ", language="
				+ language + ", advertisementOpt=" + advertisementOpt + ", createdOn=" + createdOn + ", updatedOn="
				+ updatedOn + "]";
	}
	
}
