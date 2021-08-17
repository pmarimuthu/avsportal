package com.avs.portal.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.avs.portal.entity.Notification;
import com.avs.portal.entity.User;
import com.avs.portal.enums.NotificationTypeEnum;

public interface NotificationRepository extends CrudRepository<Notification, UUID> {

	List<Notification> findAll();
	
	List<Notification> findByUser(User user);
	
	List<Notification> findByNotificationType(NotificationTypeEnum notificationType);
}
