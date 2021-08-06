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

import com.avs.portal.bean.TempPasswordBean;

@Entity
@Table(schema = "public", name = "temp_password_04")
public class TempPassword {

	@Id
	@Column(name = "userid")
	private UUID userId;
	
	@MapsId
	@OneToOne(mappedBy = "tempPassword")
	@JoinColumn(name = "userid")   //same name as id @Column
    private User user;

	@Column(name = "generated_password")
	private String generatedPassword;
	
	@Column(name = "is_used")
	private Boolean isUsed;
	
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

	public String getGeneratedPassword() {
		return generatedPassword;
	}

	public void setGeneratedPassword(String generatedPassword) {
		this.generatedPassword = generatedPassword;
	}

	public Boolean getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(Boolean isUsed) {
		this.isUsed = isUsed;
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

	public TempPasswordBean toBean() {
		return new TempPasswordBean()
				.setUserId(userId)
				.setGeneratedPassword(generatedPassword)
				.setIsUsed(isUsed)
				.setCreatedOn(createdOn.toLocalDateTime())
				.setUpdatedOn(updatedOn.toLocalDateTime());
	}

	@Override
	public String toString() {
		return "\nTempPassword [ " + 
				"userId=" + userId + 
				", user=" + user + 
				", generatedPassword=" + generatedPassword + 
				", isUsed=" + isUsed + 
				", createdOn=" + createdOn + 
				", updatedOn=" + updatedOn + 
				"]";
	}
	
}
