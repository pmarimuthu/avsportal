package com.avs.portal.entity;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.avs.portal.bean.UserVerificationBean;
import com.avs.portal.enums.VerificationStatusEnum;
import com.avs.portal.enums.VerificationSubjectEnum;

@Entity
@Table(schema = "public", name = "user_verification_10")
public class UserVerification extends BaseEntity {

	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
	private UUID id;
	
	@ManyToOne
    private User user; // ref

	@Column(name = "verification_subject", nullable = false)
	private VerificationSubjectEnum verificationSubject;
	
	@Column(name = "verification_status", nullable = false)
	private VerificationStatusEnum verificationStatus;
	
	@Column(name = "verified_by")
	private UUID verifiedBy;
	
	@Column(name = "comment")
	private String comment;
	
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

	public VerificationStatusEnum getVerificationStatus() {
		return verificationStatus;
	}

	public void setVerificationStatus(VerificationStatusEnum verificationStatus) {
		this.verificationStatus = verificationStatus;
	}

	public UUID getVerifiedBy() {
		return verifiedBy;
	}

	public UserVerification setVerifiedBy(UUID verifiedBy) {
		this.verifiedBy = verifiedBy;
		return this;
	}

	public String getComment() {
		return comment;
	}

	public UserVerification setComment(String comment) {
		this.comment = comment;
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
	
	@Override
	public UserVerificationBean toBean() {
		UserVerificationBean userVerificationBean = new UserVerificationBean()
				.setId(id)
				.setUserId(id)
				.setVerificationSubject(verificationSubject)
				.setVerificationStatus(verificationStatus)
				.setVerifiedBy(verifiedBy)
				.setComment(comment)
				.setCreatedOn(createdOn.toLocalDateTime())
				.setUpdatedOn(updatedOn.toLocalDateTime());
		
		userVerificationBean				
				.setHasError(isHasError())
				.setCustomErrorMessages(getCustomErrorMessages())
				.setThrowable(getThrowable());
		
		return userVerificationBean;
	}

	@Override
	public String toString() {
		return "UserVerification [ " + 
				" Id: " + id + 
				", User Id: " + (user == null ? "NULL" : user.getId()) + 
				", Verification Subject: " + verificationSubject + 
				", Verification Status: " + verificationStatus +
				", Verified By: " + verifiedBy + 
				", Comment: " + comment + 
				", Created On: " + createdOn + 
				", Updated On: " + updatedOn + 
				"]";
	}
	
}
