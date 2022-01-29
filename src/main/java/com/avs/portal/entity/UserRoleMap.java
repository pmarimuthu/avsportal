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

import com.avs.portal.bean.UserRoleMapBean;
import com.avs.portal.enums.RoleEnum;
import com.avs.portal.util.CommonUtil;

@Entity
@Table(schema = "public", name = "user_role_map_09")
public class UserRoleMap extends BaseEntity {

	@Id
	@Column(name = "id")
	private UUID id;
	
	@MapsId
	@OneToOne(mappedBy = "userRoleMap")
	@JoinColumn(name = "userid")   //same name as id @Column
    private User user;

	@Column(name = "role")
	private RoleEnum role;
	
	@Column(name = "created_on")
	private Timestamp createdOn;
	
	@Column(name = "updated_on")
	private Timestamp updatedOn;

	public UUID getId() {
		return id;
	}

	public UserRoleMap setId(UUID id) {
		this.id = id;
		return this;
	}

	public User getUser() {
		return user;
	}

	public UserRoleMap setUser(User user) {
		this.user = user;
		return this;
	}

	public RoleEnum getRole() {
		return role;
	}

	public UserRoleMap setRole(RoleEnum role) {
		this.role = role;
		return this;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public UserRoleMap setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
		return this;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public UserRoleMap setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
		return this;
	}
	
	@Override
	public UserRoleMapBean toBean() {
		UserRoleMapBean userRoleMapBean = new UserRoleMapBean()
				.setId(id)
				.setUserId(id)
				.setRole(role)
				.setCreatedOn(CommonUtil.toLocalDateTimeOrNull(createdOn))
				.setUpdatedOn(CommonUtil.toLocalDateTimeOrNull(updatedOn));
		
		userRoleMapBean				
				.setHasError(isHasError())
				.setCustomErrorMessages(getCustomErrorMessages())
				.setThrowable(getThrowable());
		
		return userRoleMapBean;
	}

	@Override
	public String toString() {
		return "UserRoleMap [" + 
				"  Id: " + id +
				", User Id: " + user.getId() + 
				", Role: " + role + 
				", Created On: " + createdOn + 
				", Updated On: " + updatedOn + 
				" ]";
	}
	
}
