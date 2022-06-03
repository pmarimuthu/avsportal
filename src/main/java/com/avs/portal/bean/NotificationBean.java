package com.avs.portal.bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import com.avs.portal.enums.NotificationTypeEnum;

public class NotificationBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 3302057294948427326L;

	private UUID id;
	
	private UUID userId;
	
	private NotificationTypeEnum notificationType;
	
	private String messageText;
	
	private Boolean isRead;
	
	private transient LocalDateTime createdOn;
	
	private transient LocalDateTime updatedOn;

	public UUID getId() {
		return id;
	}

	public NotificationBean setId(UUID id) {
		this.id = id;
		return this;
	}

	public UUID getUserId() {
		return userId;
	}

	public NotificationBean setUserId(UUID userId) {
		this.userId = userId;
		return this;
	}

	public NotificationTypeEnum getNotificationType() {
		return notificationType;
	}

	public NotificationBean setNotificationType(NotificationTypeEnum notificationType) {
		this.notificationType = notificationType;
		return this;
	}

	public String getMessageText() {
		return messageText;
	}

	public NotificationBean setMessageText(String messageText) {
		this.messageText = messageText;
		return this;
	}

	public Boolean getIsRead() {
		return isRead;
	}

	public NotificationBean setIsRead(Boolean isRead) {
		this.isRead = isRead;
		return this;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public NotificationBean setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
		return this;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public NotificationBean setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
		return this;
	}

	@Override
	public String toString() {
		return "NotificationBean [ " + 
				" Id: " + id + 
				", User Id: " + userId + 
				", Notification Type: " + notificationType + 
				", Message Text: " + messageText + 
				", Is Read: " + isRead + 
				", Created On: " + createdOn + 
				", Updated On: " + updatedOn + 
				" ]";
	}
	
}
