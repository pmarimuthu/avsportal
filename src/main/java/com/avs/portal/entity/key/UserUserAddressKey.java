package com.avs.portal.entity.key;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserUserAddressKey implements Serializable {

	private static final long serialVersionUID = -7845765692365427357L;

	@Column(name = "user_id")
	private UUID userId;
	
	@Column(name = "useraddress_id")
	private UUID userAddressId;
	
	public UserUserAddressKey() {
		// TODO Auto-generated constructor stub
	}
	
	public UserUserAddressKey(UUID userId, UUID userAddressId) {
		this.userId = userId;
		this.userAddressId = userAddressId;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public UUID getUserAddressId() {
		return userAddressId;
	}

	public void setUserAddressId(UUID userAddressId) {
		this.userAddressId = userAddressId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(userAddressId, userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserUserAddressKey other = (UserUserAddressKey) obj;
		return Objects.equals(userAddressId, other.userAddressId) && Objects.equals(userId, other.userId);
	}
	
}
