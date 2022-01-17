package com.avs.portal.bean;

import java.io.Serializable;
import java.util.UUID;

import com.avs.portal.entity.key.UserUserAddressKey;

public class UserUserAddressBean extends BaseBean implements Serializable {
	
	private static final long serialVersionUID = -4724176326108149029L;

	private UserUserAddressKey id;
	
	private UUID userId;
	
	private UserAddressBean userAddress;
	
	public UserUserAddressKey getId() {
		return id;
	}

	public void setId(UserUserAddressKey id) {
		this.id = id;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public UserAddressBean getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(UserAddressBean userAddress) {
		this.userAddress = userAddress;
	}

	@Override
	public String toString() {
		return super.toString() + 
				"\nUserUserAddressBean [ " +
				"Id: " + id + 
				", User Id: " + userId + 
				", UserAddress: " + userAddress.toString() +
			"]";

	}

}
