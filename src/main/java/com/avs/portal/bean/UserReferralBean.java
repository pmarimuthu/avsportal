package com.avs.portal.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

import com.avs.portal.enums.UserReferralStatusEnum;

public class UserReferralBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 6673179011148590119L;
	
	private UUID id;
	
	private String referralCode;
	
	private UUID referrer;
	
	private UUID referee;

	private UserReferralStatusEnum status = UserReferralStatusEnum.UNAVAILED;
	
	private Timestamp createdOn;
	
	private Timestamp updatedOn;

	public UUID getId() {
		return id;
	}

	public UserReferralBean setId(UUID id) {
		this.id = id;
		return this;
	}

	public String getReferralCode() {
		return referralCode;
	}

	public UserReferralBean setReferralCode(String referralCode) {
		this.referralCode = referralCode;
		return this;
	}

	public UUID getReferrer() {
		return referrer;
	}

	public UserReferralBean setReferrer(UUID referrer) {
		this.referrer = referrer;
		return this;
	}

	public UUID getReferee() {
		return referee;
	}

	public UserReferralBean setReferee(UUID referee) {
		this.referee = referee;
		return this;
	}

	public UserReferralStatusEnum getStatus() {
		return status;
	}

	public UserReferralBean setStatus(UserReferralStatusEnum status) {
		this.status = status;
		return this;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public UserReferralBean setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
		return this;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public UserReferralBean setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
		return this;
	}

	@Override
	public String toString() {
		return "\nUserReferralBean\n [" + 
				"  Id: " + id + 
				", Referrer: " + referrer + 
				", Referee: " + referee + 
				", Status: " + status + 
				", Created On: " + createdOn + 
				", Updated On: " + updatedOn + 
				" ]";
	}	
	
}
