package com.avs.portal.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avs.portal.bean.UserBean;
import com.avs.portal.bean.NotificationBean;
import com.avs.portal.entity.User;
import com.avs.portal.entity.Notification;
import com.avs.portal.repository.NotificationRepository;
import com.avs.portal.repository.UserRepository;

@Service
public class NotificationService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private NotificationRepository notificationRepository;
	
	// READ (ALL)
	public List<NotificationBean> getAllUsersNotificationes() {
		return notificationRepository.findAll().stream().map(Notification :: toBean).collect(Collectors.toList());
	}

	// READ (USER's)
	public List<NotificationBean> getNotifications(UserBean bean) {
		if(bean == null || bean.getId() == null)
			return Collections.emptyList();
		
		User user = userRepository.findById(bean.getId()).orElse(null);
		if(user == null)
			return Collections.emptyList();
		
		return user.getNotifications().stream().map(Notification :: toBean).collect(Collectors.toList());
	}

	// CREATE / UPDATE one Notification
	public List<NotificationBean> addOrEditNotification(UserBean userBean, NotificationBean notificationBean) {
		if(userBean == null || userBean.getId() == null || notificationBean.getNotificationType() == null)
			return Collections.emptyList();
		
		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null)
			return Collections.emptyList();
		
		Notification userNotification = user.getNotifications().stream().filter(entity -> entity.getNotificationType() != null && entity.getNotificationType().equals(notificationBean.getNotificationType())).findFirst().orElse(null);
		if(userNotification == null) {
			userNotification = new Notification();
			userNotification.setNotificationType(userNotification.getNotificationType());
			userNotification.setUser(user);
			userNotification.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
		}
		
		userNotification.setIsRead(notificationBean.getIsRead());
		userNotification.setMessageText(notificationBean.getMessageText());
		userNotification.setNotificationType(notificationBean.getNotificationType());
		userNotification.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		user.getNotifications().add(userNotification);
		user = userRepository.save(user);
		
		return user.getNotifications().stream().map(Notification :: toBean).collect(Collectors.toList());
	}

	public List<NotificationBean> deleteNotification(UserBean userBean, NotificationBean notificationBean) {
		if(userBean == null || userBean.getId() == null || notificationBean == null)
			return Collections.emptyList();
		
		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null)
			return Collections.emptyList();
		
		Notification userNotification = user.getNotifications().stream().filter(notification -> notification.getId().equals(notificationBean.getId())).findFirst().orElse(null);
		if(userNotification == null)
			return Collections.emptyList();
		
		user.getNotifications().remove(userNotification);
		userNotification.setUser(null);
		
		user = userRepository.save(user);
		
		return user.getNotifications().stream().map(Notification :: toBean).collect(Collectors.toList());
	}

}
