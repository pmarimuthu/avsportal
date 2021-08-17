package com.avs.portal.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.avs.portal.entity.key.UserUserAddressKey;

@Entity
@Table(schema = "public", name = "user_useraddress_90")
public class UserUserAddress {

	@EmbeddedId
	private UserUserAddressKey id;
	
	@ManyToOne
	@MapsId("userId")
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne
	@MapsId("userAddressId")
	@JoinColumn(name = "useraddress_id")
	private UserAddress userAddress;

	public UserUserAddressKey getId() {
		return id;
	}

	public void setId(UserUserAddressKey id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserAddress getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(UserAddress userAddress) {
		this.userAddress = userAddress;
	}
	
}
