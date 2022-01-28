package com.avs.portal.bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.avs.portal.enums.AddressTypeEnum;

public class UserAddressBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = -3018205575515028379L;
	
	private UUID id;
	
	private List<UUID> usersIds = new ArrayList<>();
	
	private AddressTypeEnum addressType;
	
	private String addressLine1;
	
	private String city;
	
	private String state;
	
	private String country;
	
	private String pincode;
	
	private String geoLatitude;
	
	private String geoLongitude;
	
	private String ipAddress;
	
	private LocalDateTime createdOn;
	
	private LocalDateTime updatedOn;
	
	private Boolean isDeleted;

	public UUID getId() {
		return id;
	}

	public UserAddressBean setId(UUID id) {
		this.id = id;
		return this;
	}

	public AddressTypeEnum getAddressType() {
		return addressType;
	}

	public List<UUID> getUsersIds() {
		return usersIds;
	}

	public UserAddressBean setUsersIds(List<UUID> usersIds) {
		this.usersIds = usersIds;
		return this;
	}

	public UserAddressBean setAddressType(AddressTypeEnum addressType) {
		this.addressType = addressType;
		return this;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public UserAddressBean setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
		return this;
	}

	public String getCity() {
		return city;
	}

	public UserAddressBean setCity(String city) {
		this.city = city;
		return this;
	}

	public String getState() {
		return state;
	}

	public UserAddressBean setState(String state) {
		this.state = state;
		return this;
	}

	public String getCountry() {
		return country;
	}

	public UserAddressBean setCountry(String country) {
		this.country = country;
		return this;
	}

	public String getPincode() {
		return pincode;
	}

	public UserAddressBean setPincode(String pincode) {
		this.pincode = pincode;
		return this;
	}

	public String getGeoLatitude() {
		return geoLatitude;
	}

	public UserAddressBean setGeoLatitude(String geoLatitude) {
		this.geoLatitude = geoLatitude;
		return this;
	}

	public String getGeoLongitude() {
		return geoLongitude;
	}

	public UserAddressBean setGeoLongitude(String geoLongitude) {
		this.geoLongitude = geoLongitude;
		return this;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public UserAddressBean setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
		return this;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public UserAddressBean setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
		return this;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public UserAddressBean setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
		return this;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public UserAddressBean setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
		return this;
	}

	@Override
	public String toString() {
		return "UserAddress [" + 
				"  Id: " + id + 
				", User(s) Ids: " + usersIds +  
				", Address Type: " + addressType + 
				", Address Line1: " + addressLine1 + 
				", City: " + city + 
				", State: " + state + 
				", Country: " + country + 
				", Pincode: " + pincode + 
				", GEO Latitude: " + geoLatitude + 
				", GEO Longitude: " + geoLongitude + 
				", IPAddress: " + ipAddress + 
				", Created On: " + createdOn + 
				", Updated On: " + updatedOn + 
				", Is Deleted: " + isDeleted + 
				" ]";
	}
}
