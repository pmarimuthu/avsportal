package com.avs.portal.bean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.avs.portal.enums.FamilyMemberTitleEnum;
import com.avs.portal.enums.LiveStatusEnum;

public class UserFamilyMapBean {
	
	private UUID id;	

	private UUID parentFamilyHeadId;

	private UUID familyHeadId;
	
	private FamilyMemberTitleEnum title;
	
	private LiveStatusEnum liveStatus;
	
	private List<UUID> userIds = new ArrayList<UUID>();
	
	private LocalDateTime createdOn;
	
	private LocalDateTime updatedOn;

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

	public List<UUID> getUserIds() {
		return userIds;
	}

	public UserFamilyMapBean setUserIds(List<UUID> userIds) {
		this.userIds = userIds;
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
					"  Id: " + id + 
					", Parent Family Head Id: " + parentFamilyHeadId + 
					", Family Head Id: " + familyHeadId + 
					", Family Member Title: " + title + 
					", Alive Status: " + liveStatus + 
					", Family Members: " + userIds + 
					", Created On: " + createdOn + 
					", Updated On: " + updatedOn + 
					"]";
	}
		
}
