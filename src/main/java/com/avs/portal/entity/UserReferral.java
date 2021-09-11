package com.avs.portal.entity;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.avs.portal.bean.UserReferralBean;
import com.avs.portal.enums.UserReferralStatusEnum;

@Entity
@Table(schema = "public", name = "user_referral_11")
public class UserReferral {

	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
	private UUID id;
	
	@Column(name = "referral_code")
	private String referralCode;
	
	@Column(name = "referrer", nullable = false)
	private UUID referrer;
	
	@Column(name = "referee")
	private UUID referee;

	@Column(name = "referral_status")
	private UserReferralStatusEnum status = UserReferralStatusEnum.UNAVAILED;
	
	@Column(name = "created_on")
	private Timestamp createdOn;
	
	@Column(name = "updated_on")
	private Timestamp updatedOn;

	public UUID getId() {
		return id;
	}

	public UserReferral setId(UUID id) {
		this.id = id;
		return this;
	}

	public String getReferralCode() {
		return referralCode;
	}

	public UserReferral setReferralCode(String referralCode) {
		this.referralCode = referralCode;
		return this;
	}

	public UUID getReferrer() {
		return referrer;
	}

	public UserReferral setReferrer(UUID referrer) {
		this.referrer = referrer;
		return this;
	}

	public UUID getReferee() {
		return referee;
	}

	public UserReferral setReferee(UUID referee) {
		this.referee = referee;
		return this;
	}

	public UserReferralStatusEnum getStatus() {
		return status;
	}

	public UserReferral setStatus(UserReferralStatusEnum status) {
		this.status = status;
		return this;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public UserReferral setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
		return this;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public UserReferral setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
		return this;
	}

	public UserReferralBean toBean() {
		return new UserReferralBean()
				.setId(id)
				.setReferralCode(referralCode)
				.setReferrer(referrer)
				.setReferee(referee)
				.setStatus(status)
				.setCreatedOn(createdOn)
				.setUpdatedOn(updatedOn);

	}
	
}
