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

import com.avs.portal.bean.UserReferralMapBean;
import com.avs.portal.enums.ReferralTypeEnum;

@Entity
@Table(schema = "public", name = "user_referral_map")
public class UserReferralMap extends BaseEntity {

	@Id
	@Column(name = "id")
	private UUID id;
	
	@MapsId
	@OneToOne(mappedBy = "userReferralMap")
	@JoinColumn(name = "userid")   //same name as id @Column
    private User user;

	@Column(name = "referral_type")
	private ReferralTypeEnum referralType;	

	@Column(name = "referral_user_id")
	private UUID referralUserId;
	
	@Column(name = "created_on")
	private Timestamp createdOn;
	
	@Column(name = "updated_on")
	private Timestamp updatedOn;

	public UUID getId() {
		return id;
	}

	public UserReferralMap setId(UUID id) {
		this.id = id;
		return this;
	}

	public User getUser() {
		return user;
	}

	public UserReferralMap setUser(User user) {
		this.user = user;
		return this;
	}

	public ReferralTypeEnum getReferralType() {
		return referralType;
	}

	public UserReferralMap setReferralType(ReferralTypeEnum referralType) {
		this.referralType = referralType;
		return this;
	}

	public UUID getReferralUserId() {
		return referralUserId;
	}

	public UserReferralMap setReferralUserId(UUID referralUserId) {
		this.referralUserId = referralUserId;
		return this;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public UserReferralMap setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
		return this;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public UserReferralMap setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
		return this;
	}
	
	@Override
	public UserReferralMapBean toBean() {
		UserReferralMapBean userReferralBean = new UserReferralMapBean()
				.setId(id)
				.setUserId(user == null ? null : user.getId())
				.setReferralType(referralType)
				.setReferralUserId(referralUserId)
				.setCreatedOn(createdOn)
				.setUpdatedOn(updatedOn);
				
		userReferralBean				
				.setHasError(isHasError())
				.setCustomErrorMessages(getCustomErrorMessages())
				.setThrowable(getThrowable());		
		
		return userReferralBean;
	}
	
}
