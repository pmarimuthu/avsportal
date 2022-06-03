package com.avs.portal.bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import com.avs.portal.enums.VerificationStatusEnum;
import com.avs.portal.enums.VerificationSubjectEnum;

public class UserVerificationBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 8971866578188742401L;

	private UUID id;
	
	private UUID userId;
	
	private VerificationSubjectEnum verificationSubject;
	
	private VerificationStatusEnum verificationStatus;
	
	private String comment;
	
	private UUID verifiedBy;
	
	private transient LocalDateTime createdOn;
	
	private transient LocalDateTime updatedOn;

	public UUID getId() {
		return id;
	}

	public UserVerificationBean setId(UUID id) {
		this.id = id;
		return this;
	}

	public UUID getUserId() {
		return userId;
	}

	public UserVerificationBean setUserId(UUID userId) {
		this.userId = userId;
		return this;
	}

	public VerificationSubjectEnum getVerificationSubject() {
		return verificationSubject;
	}

	public UserVerificationBean setVerificationSubject(VerificationSubjectEnum verificationSubject) {
		this.verificationSubject = verificationSubject;
		return this;
	}

	public VerificationStatusEnum getVerificationStatus() {
		return verificationStatus;
	}

	public UserVerificationBean setVerificationStatus(VerificationStatusEnum verificationStatus) {
		this.verificationStatus = verificationStatus;
		return this;
	}

	public String getComment() {
		return comment;
	}

	public UserVerificationBean setComment(String comment) {
		this.comment = comment;
		return this;
	}

	public UUID getVerifiedBy() {
		return verifiedBy;
	}

	public UserVerificationBean setVerifiedBy(UUID verifiedBy) {
		this.verifiedBy = verifiedBy;
		return this;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public UserVerificationBean setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
		return this;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public UserVerificationBean setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
		return this;
	}

	@Override
	public String toString() {
		return "\nUserVerificationBean [ "
				+ "Id: " + id +
				", UserId: " + userId + 
				", Verification Subject: " + verificationSubject + 
				", Verification Status: " + verificationStatus +
				", Comment: " + comment + 
				", Verified By: " + verifiedBy + 
				", Created On: " + createdOn + 
				", Updated On: " + updatedOn + 
				" ]";
	}
	
}
