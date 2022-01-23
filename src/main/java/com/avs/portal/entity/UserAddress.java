package com.avs.portal.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.avs.portal.bean.UserAddressBean;
import com.avs.portal.enums.AddressTypeEnum;

@Entity
@Table(schema = "public", name = "user_address_07")
public class UserAddress extends BaseEntity {

	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
	private UUID id;
	
	@ManyToMany(mappedBy = "userAddresses", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<User> users = new ArrayList<>(); // ref

	@Column(name = "address_type", nullable = false)
	private AddressTypeEnum addressType;
	
	@Column(name = "address_line1")
	private String addressLine1;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "state")
	private String state;
	
	@Column(name = "country")
	private String country;
	
	@Column(name = "pincode")
	private String pincode;
	
	@Column(name = "geo_latitude")
	private String geoLatitude;
	
	@Column(name = "geo_longitude")
	private String geoLongitude;
	
	@Column(name = "ipaddress")
	private String ipAddress;
	
	@Column(name = "created_on")
	private Timestamp createdOn;
	
	@Column(name = "updated_on")
	private Timestamp updatedOn;

	@Column(name = "is_deleted")
	private Boolean isDeleted;

	public UUID getId() {
		return id;
	}

	public UserAddress setId(UUID id) {
		this.id = id;
		return this;
	}

	public List<User> getUsers() {
		return users;
	}

	public UserAddress setUsers(List<User> users) {
		this.users = users;
		return this;
	}

	public AddressTypeEnum getAddressType() {
		return addressType;
	}

	public UserAddress setAddressType(AddressTypeEnum addressType) {
		this.addressType = addressType;
		return this;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public UserAddress setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
		return this;
	}

	public String getCity() {
		return city;
	}

	public UserAddress setCity(String city) {
		this.city = city;
		return this;
	}

	public String getState() {
		return state;
	}

	public UserAddress setState(String state) {
		this.state = state;
		return this;
	}

	public String getCountry() {
		return country;
	}

	public UserAddress setCountry(String country) {
		this.country = country;
		return this;
	}

	public String getPincode() {
		return pincode;
	}

	public UserAddress setPincode(String pincode) {
		this.pincode = pincode;
		return this;
	}

	public String getGeoLatitude() {
		return geoLatitude;
	}

	public UserAddress setGeoLatitude(String geoLatitude) {
		this.geoLatitude = geoLatitude;
		return this;
	}

	public String getGeoLongitude() {
		return geoLongitude;
	}

	public UserAddress setGeoLongitude(String geoLongitude) {
		this.geoLongitude = geoLongitude;
		return this;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public UserAddress setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
		return this;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public UserAddress setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
		return this;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public UserAddress setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
		return this;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public UserAddress setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
		return this;
	}

	public UserAddressBean toBean() {
		UserAddressBean userAddressBean = new UserAddressBean()
				.setId(id)
				.setUsersIds(users.stream().map(User :: getId).collect(Collectors.toList()))
				.setAddressLine1(addressLine1)
				.setAddressType(addressType)
				.setCity(city)
				.setCountry(country)
				.setPincode(pincode)
				.setState(state)
				.setGeoLatitude(geoLatitude)
				.setGeoLongitude(geoLongitude)
				.setIpAddress(ipAddress)
				.setCreatedOn(createdOn.toLocalDateTime())
				.setUpdatedOn(updatedOn.toLocalDateTime())
				.setIsDeleted(isDeleted);
		
		userAddressBean				
				.setHasError(isHasError())
				.setCustomErrorMessages(getCustomErrorMessages())
				.setThrowable(getThrowable());
		
		return userAddressBean;
	}
	
}
