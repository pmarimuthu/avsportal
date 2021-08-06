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

import com.avs.portal.bean.UserCredentialBean;

@Entity
@Table(schema = "public", name = "user_credential_02")
public class UserCredential {

	@Id
	@Column(name = "id")
	private UUID id;
	
	@MapsId 
    @OneToOne(mappedBy = "userCredential")
    @JoinColumn(name = "userid")   //same name as id @Column
    private User user;

	@Column(name = "password")
	private String password;
	
	@Column(name = "created_on")
	private Timestamp createdOn;
	
	@Column(name = "updated_on")
	private Timestamp updatedOn;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
	
	public UserCredentialBean toBean() {
		return new UserCredentialBean()
		.setId(id)
		.setPassword(password)
		.setCreatedOn(createdOn.toLocalDateTime())
		.setUpdatedOn(updatedOn.toLocalDateTime());
	}

	@Override
	public String toString() {
		return "UserCredential [userId=" + id + ", password=" + password + ", createdOn=" + createdOn
				+ ", updatedOn=" + updatedOn + "]";
	}

	
}
