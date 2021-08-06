package com.avs.portal.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.avs.portal.entity.Notification;

public interface NotificationRepository extends CrudRepository<Notification, UUID> {

}
