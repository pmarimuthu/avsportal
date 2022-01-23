package com.avs.portal.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

import com.avs.portal.enums.ReferralTypeEnum;

public class UserReferralMapBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = -8837701423565608352L;

	private UUID id;
	
	private UUID userId;

	private ReferralTypeEnum referralType;
	
	private UUID referralUserId;
	
	private Timestamp createdOn;
	
	private Timestamp updatedOn;

	public UUID getId() {
		return id;
	}

	public UserReferralMapBean setId(UUID id) {
		this.id = id;
		return this;
	}

	public UUID getUserId() {
		return userId;
	}

	public UserReferralMapBean setUserId(UUID userId) {
		this.userId = userId;
		return this;
	}

	public ReferralTypeEnum getReferralType() {
		return referralType;
	}

	public UserReferralMapBean setReferralType(ReferralTypeEnum referralType) {
		this.referralType = referralType;
		return this;
	}

	public UUID getReferralUserId() {
		return referralUserId;
	}

	public UserReferralMapBean setReferralUserId(UUID referralUserId) {
		this.referralUserId = referralUserId;
		return this;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public UserReferralMapBean setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
		return this;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public UserReferralMapBean setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
		return this;
	}
	
	
}
