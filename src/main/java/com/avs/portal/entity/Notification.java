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

import com.avs.portal.bean.NotificationBean;
import com.avs.portal.enums.NotificationTypeEnum;

@Entity
@Table(schema = "public", name = "notification_12")
public class Notification {

	@Id
	@Column(name = "userid")
	private UUID userId;
	
	@MapsId
	@OneToOne(mappedBy = "notification")
	@JoinColumn(name = "userid")   //same name as id @Column
    private User user;

	@Column(name = "notification_type")
	private NotificationTypeEnum notificationType;
	
	@Column(name = "message_text")
	private String messageText;
	
	@Column(name = "is_read")
	private Boolean isRead;
	
	@Column(name = "created_on")
	private Timestamp createdOn;
	
	@Column(name = "updated_on")
	private Timestamp updatedOn;

	public UUID getUserId() {
		return userId;
	}

	public Notification setUserId(UUID userId) {
		this.userId = userId;
		return this;
	}

	public User getUser() {
		return user;
	}

	public Notification setUser(User user) {
		this.user = user;
		return this;
	}

	public NotificationTypeEnum getNotificationType() {
		return notificationType;
	}

	public Notification setNotificationType(NotificationTypeEnum notificationType) {
		this.notificationType = notificationType;
		return this;
	}

	public String getMessageText() {
		return messageText;
	}

	public Notification setMessageText(String messageText) {
		this.messageText = messageText;
		return this;
	}

	public Boolean getIsRead() {
		return isRead;
	}

	public Notification setIsRead(Boolean isRead) {
		this.isRead = isRead;
		return this;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public Notification setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
		return this;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public Notification setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
		return this;
	}

	public NotificationBean toBean() {
		return new NotificationBean()
				.setUserId(userId)
				.setNotificationType(notificationType)
				.setMessageText(messageText)
				.setIsRead(isRead)
				.setCreatedOn(createdOn.toLocalDateTime())
				.setUpdatedOn(updatedOn.toLocalDateTime());
	}

	@Override
	public String toString() {
		return "\nNotification [ " + 
				"userId=" + userId + 
				", user=" + user + 
				", notificationType=" + notificationType + 
				", messageText=" + messageText + 
				", isRead=" + isRead + 
				", createdOn=" + createdOn + 
				", updatedOn=" + updatedOn + 
				"]";
	}
	
}
