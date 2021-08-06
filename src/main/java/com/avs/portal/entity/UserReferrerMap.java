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
	@Column(name = "userid")
	private UUID userId;
	
	@MapsId
	@OneToOne(mappedBy = "tempPassword")
	@JoinColumn(name = "userid")   //same name as id @Column
    private User user;

	@Column(name = "referred_by")
	private UUID referredBy;
	
	@Column(name = "created_on")
	private Timestamp createdOn;
	
	@Column(name = "updated_on")
	private Timestamp updatedOn;

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public User getUser() {
		return user;
	}

	public UserReferrerMap setUser(User user) {
		this.user = user;
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
				.setUserId(userId)
				.setReferredBy(referredBy)
				.setCreatedOn(createdOn.toLocalDateTime())
				.setUpdatedOn(updatedOn.toLocalDateTime());
	}

	@Override
	public String toString() {
		return "\nUserReferrerMap [ " + 
				"userId=" + userId + 
				", user=" + user + 
				", referredBy=" + referredBy + 
				", createdOn=" + createdOn + 
				", updatedOn=" + updatedOn + 
				"]";
	}

}
