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

import com.avs.portal.bean.UserPreferencesBean;
import com.avs.portal.enums.LanguageEnum;
import com.avs.portal.enums.VisibilityEnum;

@Entity
@Table(schema = "public", name = "user_preferences_05")
public class UserPreferences {

	@Id
	@Column(name = "userid")
	private UUID userId;
	
	@MapsId
	@OneToOne(mappedBy = "userPreferences")
	@JoinColumn(name = "userid")   //same name as id @Column
    private User user;

	@Column(name = "visibility_level")
	private VisibilityEnum visibility;
	
	@Column(name = "language")
	private LanguageEnum language;
	
	@Column(name = "advertisement_opt")
	private Boolean advertisement;
	
	@Column(name = "created_on")
	private Timestamp createdOn;
	
	@Column(name = "updated_on")
	private Timestamp updatedOn;

	public UUID getUserId() {
		return userId;
	}

	public UserPreferences setUserId(UUID userId) {
		this.userId = userId;
		return this;
	}

	public User getUser() {
		return user;
	}

	public UserPreferences setUser(User user) {
		this.user = user;
		return this;
	}

	public VisibilityEnum getVisibility() {
		return visibility;
	}

	public UserPreferences setVisibility(VisibilityEnum visibility) {
		this.visibility = visibility;
		return this;
	}

	public LanguageEnum getLanguage() {
		return language;
	}

	public UserPreferences setLanguage(LanguageEnum language) {
		this.language = language;
		return this;
	}

	public Boolean getAdvertisement() {
		return advertisement;
	}

	public UserPreferences setAdvertisement(Boolean advertisement) {
		this.advertisement = advertisement;
		return this;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public UserPreferences setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
		return this;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public UserPreferences setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
		return this;
	}

	public UserPreferencesBean toBean() {
		return new UserPreferencesBean()
				.setUserId(userId)
				.setVisibilityLevel(this.visibility)
				.setLanguage(language)
				.setAdvertisementOpt(this.advertisement)
				.setCreatedOn(createdOn.toLocalDateTime())
				.setUpdatedOn(updatedOn.toLocalDateTime());
				
	}

	@Override
	public String toString() {
		return "UserPreferences [userId=" + userId + ", visibility=" + visibility + ", language="
				+ language + ", advertisement=" + advertisement + ", createdOn=" + createdOn + ", updatedOn="
				+ updatedOn + "]";
	}

}
