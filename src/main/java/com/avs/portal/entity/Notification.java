package com.avs.portal.entity;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.avs.portal.bean.NotificationBean;
import com.avs.portal.enums.NotificationTypeEnum;

@Entity
@Table(schema = "public", name = "notification_12")
public class Notification extends BaseEntity {

	@Id
	@Column(name = "id")
	private UUID id;
	
	@ManyToOne
    private User user; // ref

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

	public UUID getId() {
		return id;
	}

	public Notification setId(UUID id) {
		this.id = id;
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
		NotificationBean notificationBean = new NotificationBean()
				.setId(id)
				.setUserId(user == null ? null : user.getId())
				.setNotificationType(notificationType)
				.setMessageText(messageText)
				.setIsRead(isRead)
				.setCreatedOn(createdOn.toLocalDateTime())
				.setUpdatedOn(updatedOn.toLocalDateTime());
		
		notificationBean				
				.setHasError(isHasError())
				.setCustomErrorMessages(getCustomErrorMessages())
				.setThrowable(getThrowable());
		
		return notificationBean;
	}

	@Override
	public String toString() {
		return "\nNotification [ " + 
				"Id: " + id + 
				", User Id: " + (user == null ? "NULL" : user.getId()) + 
				", Notification Type: " + notificationType + 
				", Message Text: " + messageText + 
				", Is Read: " + isRead + 
				", Created On: " + createdOn + 
				", Updated On: " + updatedOn + 
				"]";
	}
	
}
