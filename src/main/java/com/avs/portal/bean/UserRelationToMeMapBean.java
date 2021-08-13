package com.avs.portal.bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import com.avs.portal.enums.RelationToMeEnum;
import com.avs.portal.enums.VerificationStatusEnum;

public class UserRelationToMeMapBean implements Serializable {

	private static final long serialVersionUID = 1264185153560165325L;

	private UUID id;
	
	private UUID userId;
	
	private UUID relativeUserId;
	
	private RelationToMeEnum relationToMe;
	
	private VerificationStatusEnum verificaionStatus;
	
	private UUID verifiedBy;
	
	private LocalDateTime createdOn;
	
	private LocalDateTime updatedOn;

	public UUID getId() {
		return id;
	}

	public UserRelationToMeMapBean setId(UUID id) {
		this.id = id;
		return this;
	}

	public UUID getUserId() {
		return userId;
	}

	public UserRelationToMeMapBean setUserId(UUID userId) {
		this.userId = userId;
		return this;
	}

	public UUID getRelativeUserId() {
		return relativeUserId;
	}

	public UserRelationToMeMapBean setRelativeUserId(UUID relativeUserId) {
		this.relativeUserId = relativeUserId;
		return this;
	}

	public RelationToMeEnum getRelationToMe() {
		return relationToMe;
	}

	public UserRelationToMeMapBean setRelationToMe(RelationToMeEnum relationToMe) {
		this.relationToMe = relationToMe;
		return this;
	}

	public VerificationStatusEnum getVerificaionStatus() {
		return verificaionStatus;
	}

	public UserRelationToMeMapBean setVerificaionStatus(VerificationStatusEnum verificaionStatus) {
		this.verificaionStatus = verificaionStatus;
		return this;
	}

	public UUID getVerifiedBy() {
		return verifiedBy;
	}

	public UserRelationToMeMapBean setVerifiedBy(UUID verifiedBy) {
		this.verifiedBy = verifiedBy;
		return this;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public UserRelationToMeMapBean setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
		return this;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public UserRelationToMeMapBean setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
		return this;
	}

	@Override
	public String toString() {
		return "\nUserRelationToMeMap [" + 
				"  Id: " + id + 
				", Relative User Id: " + relativeUserId + 
				", Relation ToMe: " + relationToMe + 
				", Verificaion Status: " + verificaionStatus + 
				", Verified By: " + verifiedBy + 
				", Created On: " + createdOn + 
				", Updated On: " + updatedOn + 
				" ]";
	}
	
}
