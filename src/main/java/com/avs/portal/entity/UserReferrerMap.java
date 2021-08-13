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

import com.avs.portal.bean.UserReferrerMapBean;

@Entity
@Table(schema = "public", name = "user_referrer_map_11")
public class UserReferrerMap {

	@Id
	@Column(name = "id")
	private UUID id;
	
	@MapsId
	@OneToOne(mappedBy = "tempPassword")
	@JoinColumn(name = "userid")   //same name as id @Column
    private User user;

	@Column(name = "referred_by_user_id")
	private UUID referredByUserId;
	
	@Column(name = "created_on")
	private Timestamp createdOn;
	
	@Column(name = "updated_on")
	private Timestamp updatedOn;

	public UUID getId() {
		return id;
	}

	public UserReferrerMap setId(UUID id) {
		this.id = id;
		return this;
	}

	public User getUser() {
		return user;
	}

	public UserReferrerMap setUser(User user) {
		this.user = user;
		return this;
	}

	public UUID getReferredByUserId() {
		return referredByUserId;
	}

	public UserReferrerMap setReferredByUserId(UUID referredByUserId) {
		this.referredByUserId = referredByUserId;
		return this;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public UserReferrerMap setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
		return this;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public UserReferrerMap setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
		return this;
	}

	public UserReferrerMapBean toBean() {
		return new UserReferrerMapBean()
				.setId(id)
				.setUserId(user.getId())
				.setReferredByUserId(referredByUserId)
				.setCreatedOn(createdOn.toLocalDateTime())
				.setUpdatedOn(updatedOn.toLocalDateTime());
	}

	@Override
	public String toString() {
		return "\nUserReferrerMap [" + 
				"  Id: " + id +
				", User Id: " + user.getId() + 
				", Referred By User Id: " + referredByUserId + 
				", Created On: " + createdOn + 
				", Updated On: " + updatedOn + 
				"]";
	}

}
