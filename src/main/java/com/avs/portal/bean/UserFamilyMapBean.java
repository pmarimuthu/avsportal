package com.avs.portal.bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import com.avs.portal.enums.FamilyMemberTitleEnum;
import com.avs.portal.enums.LiveStatusEnum;

public class UserFamilyMapBean extends BaseBean implements Serializable {
	
	private static final long serialVersionUID = 1617763884379262944L;

	private UUID id;	

	private UUID userId;
	
	private FamilyMemberTitleEnum title;
	
	private UUID familyHeadId;
	
	private UUID parentFamilyHeadId;
	
	private LiveStatusEnum liveStatus;
	
	private transient LocalDateTime createdOn;
	
	private transient LocalDateTime updatedOn;

	public UUID getId() {
		return id;
	}

	public UserFamilyMapBean setId(UUID id) {
		this.id = id;
		return this;
	}

	public UUID getParentFamilyHeadId() {
		return parentFamilyHeadId;
	}

	public UserFamilyMapBean setParentFamilyHeadId(UUID parentFamilyId) {
		this.parentFamilyHeadId = parentFamilyId;
		return this;
	}

	public UUID getFamilyHeadId() {
		return familyHeadId;
	}

	public UserFamilyMapBean setFamilyHeadId(UUID familyHeadId) {
		this.familyHeadId = familyHeadId;
		return this;
	}

	public FamilyMemberTitleEnum getTitle() {
		return title;
	}

	public UserFamilyMapBean setTitle(FamilyMemberTitleEnum title) {
		this.title = title;
		return this;
	}

	public LiveStatusEnum getLiveStatus() {
		return liveStatus;
	}

	public UserFamilyMapBean setLiveStatus(LiveStatusEnum liveStatus) {
		this.liveStatus = liveStatus;
		return this;
	}

	public UUID getUserId() {
		return userId;
	}

	public UserFamilyMapBean setUserId(UUID userId) {
		this.userId = userId;
		return this;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public UserFamilyMapBean setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
		return this;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public UserFamilyMapBean setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
		return this;
	}

	@Override
	public String toString() {
		return "\nUserFamilyMapBean [" + 
					"  \n\t Id: " + id +
					", \n\t UserId: " + userId +
					", \n\t Title: " + title +
					", \n\t Family-Head-ID: " + familyHeadId + 
					", \n\t Parent-Family-Head-ID: " + parentFamilyHeadId +
					", \n\t Alive Status: " + liveStatus + 
					", Created On: " + createdOn + 
					", Updated On: " + updatedOn +
					"]";
	}
		
}
