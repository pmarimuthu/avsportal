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

import com.avs.portal.bean.UserAccountStatusBean;

@Entity
@Table(schema = "public", name = "user_account_status_03")
public class UserAccountStatus {

	@Id
	@Column(name = "userid")
	private UUID userId;
	
	@MapsId 
    @OneToOne(mappedBy = "userAccountStatus")
    @JoinColumn(name = "userid")   //same name as id @Column
    private User user;

	@Column(name = "is_verified")
	private Boolean isVerified;
	
	@Column(name = "is_active")
	private Boolean isActive;
	
	@Column(name = "is_locked")
	private Boolean isLocked;
	
	@Column(name = "is_blocked")
	private Boolean isBlocked;
	
	@Column(name = "is_deleted")
	private Boolean isDeleted;
		
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

	public void setUser(User user) {
		this.user = user;
	}

	public Boolean getIsVerified() {
		return isVerified;
	}

	public void setIsVerified(Boolean isVerified) {
		this.isVerified = isVerified;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(Boolean isLocked) {
		this.isLocked = isLocked;
	}

	public Boolean getIsBlocked() {
		return isBlocked;
	}

	public void setIsBlocked(Boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}

	public UserAccountStatusBean toBean() {
		UserAccountStatusBean bean = new UserAccountStatusBean();
		bean.setUserId(userId);
		bean.setIsActive(isActive);
		bean.setIsBlocked(isBlocked);
		bean.setIsDeleted(isDeleted);
		bean.setIsLocked(isLocked);
		bean.setIsVerified(isVerified);
		bean.setCreatedOn(createdOn.toLocalDateTime());
		bean.setUpdatedOn(updatedOn.toLocalDateTime());
		
		return bean;
	}

	@Override
	public String toString() {
		return "UserAccountStatus [userId=" + userId + ", isVerified=" + isVerified + ", isActive="
				+ isActive + ", isLocked=" + isLocked + ", isBlocked=" + isBlocked + ", isDeleted=" + isDeleted
				+ ", createdOn=" + createdOn + ", updatedOn=" + updatedOn + "]";
	}
}
