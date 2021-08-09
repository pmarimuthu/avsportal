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

import com.avs.portal.bean.UserVerificationBean;
import com.avs.portal.enums.VerificationModeEnum;
import com.avs.portal.enums.VerificationSubjectEnum;

@Entity
@Table(schema = "public", name = "user_verification_10")
public class UserVerification {

	@Id
	@Column(name = "id")
	private UUID id;
	
	@MapsId
	@OneToOne(mappedBy = "userVerification")
	@JoinColumn(name = "userid")   //same name as id @Column
    private User user;

	@Column(name = "verification_subject")
	private VerificationSubjectEnum verificationSubject;
	
	@Column(name = "verification_mode")
	private VerificationModeEnum verificationMode;
	
	@Column(name = "verified_by", nullable = false)
	private UUID verifiedBy;
	
	@Column(name = "created_on")
	private Timestamp createdOn;
	
	@Column(name = "updated_on")
	private Timestamp updatedOn;

	public UUID getId() {
		return id;
	}

	public void setId(UUID userId) {
		this.id = userId;
	}

	public User getUser() {
		return user;
	}

	public UserVerification setUser(User user) {
		this.user = user;
		return this;
	}

	public VerificationSubjectEnum getVerificationSubject() {
		return verificationSubject;
	}

	public UserVerification setVerificationSubject(VerificationSubjectEnum verificationSubject) {
		this.verificationSubject = verificationSubject;
		return this;
	}

	public VerificationModeEnum getVerificationMode() {
		return verificationMode;
	}

	public UserVerification setVerificationMode(VerificationModeEnum verificationMode) {
		this.verificationMode = verificationMode;
		return this;
	}

	public UUID getVerifiedBy() {
		return verifiedBy;
	}

	public UserVerification setVerifiedBy(UUID verifiedBy) {
		this.verifiedBy = verifiedBy;
		return this;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public UserVerification setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
		return this;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public UserVerification setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
		return this;
	}
	
	public UserVerificationBean toBean() {
		return new UserVerificationBean()
				.setId(id)
				.setUserId(id)
				.setVerificationSubject(verificationSubject)
				.setVerificationMode(verificationMode)
				.setVerifiedBy(verifiedBy)
				.setCreatedOn(createdOn.toLocalDateTime())
				.setUpdatedOn(updatedOn.toLocalDateTime());
	}

	@Override
	public String toString() {
		return "UserVerification [ " + 
				" Id: " + id + 
				", User (Id): " + (user == null ? "NULL" : user.getId()) + 
				", Verification Subject: " + verificationSubject + 
				", Verification Mode: " + verificationMode + 
				", Verified By: " + verifiedBy + 
				", Created On: " + createdOn + 
				", Updated On: " + updatedOn + 
				"]";
	}
	
}
