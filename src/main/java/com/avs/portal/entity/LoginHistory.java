package com.avs.portal.entity;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.avs.portal.bean.LoginHistoryBean;
import com.avs.portal.enums.DeviceTypeEnum;
import com.avs.portal.enums.UserAgentEnum;

@Entity
@Table(schema = "public", name = "login_history_13")
public class LoginHistory {

	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
	private UUID id;
	
	@ManyToOne
	private User user; // ref

	@Column(name = "consecutive_failed_login_count")
	private Integer consecutiveFailedLoginCount;
	
	@Column(name = "ipaddress")
	private String ipAddress;
	
	@Column(name = "device_type")
	private DeviceTypeEnum deviceType;
	
	@Column(name = "device_useragent_type")
	private UserAgentEnum userAgent;
	
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

	public Integer getConsecutiveFailedLoginCount() {
		return consecutiveFailedLoginCount;
	}

	public LoginHistory setConsecutiveFailedLoginCount(Integer consecutiveFailedLoginCount) {
		this.consecutiveFailedLoginCount = consecutiveFailedLoginCount;
		return this;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public LoginHistory setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
		return this;
	}

	public DeviceTypeEnum getDeviceType() {
		return deviceType;
	}

	public LoginHistory setDeviceType(DeviceTypeEnum deviceType) {
		this.deviceType = deviceType;
		return this;
	}

	public UserAgentEnum getUserAgent() {
		return userAgent;
	}

	public LoginHistory setUserAgent(UserAgentEnum userAgent) {
		this.userAgent = userAgent;
		return this;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public LoginHistory setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
		return this;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public LoginHistory setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
		return this;
	}

	public LoginHistoryBean toBean() {
		return new LoginHistoryBean()
				.setId(id)
				.setUserId(id)
				.setConsecutiveFailedLoginCount(consecutiveFailedLoginCount)
				.setIpAddress(ipAddress)
				.setDeviceType(deviceType)
				.setUserAgent(userAgent)
				.setCreatedOn(createdOn.toLocalDateTime())
				.setUpdatedOn(updatedOn.toLocalDateTime());
	}

	@Override
	public String toString() {
		return "\nLoginHistory [" +
				"Id: " + id + 
				", User: " + user + 
				", Consecutive Failed Login Count: " + consecutiveFailedLoginCount + 
				", IPAddress: " + ipAddress + 
				", Device Type: " + deviceType + 
				", User Agent: " + userAgent + 
				", Created On: " + createdOn + 
				", Updated On: " + updatedOn + 
				"]";
	}
	
}
