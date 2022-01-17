package com.avs.portal.bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import com.avs.portal.enums.DeviceTypeEnum;
import com.avs.portal.enums.UserAgentEnum;
import com.avs.portal.util.CommonUtil;

public class LoginHistoryBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = -3160929326776115755L;

	private UUID id;
	
	private UUID userId;
	
	private Integer consecutiveFailedLoginCount;
	
	private String ipAddress;
	
	private DeviceTypeEnum deviceType;
	
	private UserAgentEnum userAgent;
	
	private LocalDateTime createdOn;
	
	private LocalDateTime updatedOn;

	public UUID getId() {
		return id;
	}

	public LoginHistoryBean setId(UUID id) {
		this.id = id;
		return this;
	}

	public UUID getUserId() {
		return userId;
	}

	public LoginHistoryBean setUserId(UUID userId) {
		this.userId = userId;
		return this;
	}

	public Integer getConsecutiveFailedLoginCount() {
		return consecutiveFailedLoginCount;
	}

	public LoginHistoryBean setConsecutiveFailedLoginCount(Integer consecutiveFailedLoginCount) {
		this.consecutiveFailedLoginCount = consecutiveFailedLoginCount;
		return this;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public LoginHistoryBean setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
		return this;
	}

	public DeviceTypeEnum getDeviceType() {
		return deviceType;
	}

	public LoginHistoryBean setDeviceType(DeviceTypeEnum deviceType) {
		this.deviceType = deviceType;
		return this;
	}

	public UserAgentEnum getUserAgent() {
		return userAgent;
	}

	public LoginHistoryBean setUserAgent(UserAgentEnum userAgent) {
		this.userAgent = userAgent;
		return this;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public LoginHistoryBean setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
		return this;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public LoginHistoryBean setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
		return this;
	}

	@Override
	public String toString() {
		return "\nLoginHistoryBean [ " +
				"  Id: " + id + 
				", UserId: " + userId + 
				", Consecutive Failed Login Count: " + consecutiveFailedLoginCount + 
				", IPAddress: " + ipAddress + 
				", Device Type: " + deviceType + 
				", User Agent: " + userAgent + 
				", Created On: " + CommonUtil.toString(createdOn) + 
				", Updated On: " + CommonUtil.toString(updatedOn) + 
				" ]";
	}
	
}
