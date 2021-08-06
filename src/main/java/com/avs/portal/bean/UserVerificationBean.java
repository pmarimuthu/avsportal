package com.avs.portal.bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import com.avs.portal.enums.VerificationModeEnum;
import com.avs.portal.enums.VerificationSubjectEnum;

public class UserVerificationBean implements Serializable {

	private static final long serialVersionUID = 8971866578188742401L;

	private UUID userId;
	
	private VerificationSubjectEnum verificationSubject;
	
	private VerificationModeEnum verificationMode;
	
	private UUID verifiedBy;
	
	private LocalDateTime createdOn;
	
	private LocalDateTime updatedOn;

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

	public VerificationModeEnum getVerificationMode() {
		return verificationMode;
	}

	public UserVerificationBean setVerificationMode(VerificationModeEnum verificationMode) {
		this.verificationMode = verificationMode;
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
		return "\nUserVerificationBean [userId=" + userId + ", verificationSubject=" + verificationSubject
				+ ", verificationMode=" + verificationMode + ", verifiedBy=" + verifiedBy + ", createdOn=" + createdOn
				+ ", updatedOn=" + updatedOn + "]";
	}
	
}
