package com.avs.portal.bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import com.avs.portal.enums.LanguageEnum;
import com.avs.portal.enums.VisibilityEnum;

public class UserPreferencesBean implements Serializable {

	private static final long serialVersionUID = 7773159516719852765L;
	
	private UUID id;
	
	private VisibilityEnum visibilityLevel;
	
	private LanguageEnum language;
	
	private Boolean advertisementOpt;

	
	private LocalDateTime createdOn;
	
	private LocalDateTime updatedOn;
	
	public UUID getId() {
		return id;
	}

	public UserPreferencesBean setId(UUID userId) {
		this.id = userId;
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
		return "\nUserPreferencesBean [ Id: " + id + ", Visibility Level: " + visibilityLevel + ", Language: "
				+ language + ", Advertisement Opt: " + advertisementOpt + ", Created On: " + createdOn + ", Updated On: "
				+ updatedOn + " ]";
	}
	
}
