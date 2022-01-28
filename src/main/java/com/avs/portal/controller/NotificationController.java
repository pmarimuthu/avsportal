package com.avs.portal.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avs.portal.bean.NotificationBean;
import com.avs.portal.bean.UserBean;
import com.avs.portal.service.NotificationService;

@RestController
@RequestMapping(path = "/api/notification")
public class NotificationController {

	@Autowired
	private NotificationService notificationService;

	@GetMapping("/health")
	public String sayHello() {
		return "NotificationController is Alive!!";
	}

	@PostMapping("/list")
	public List<NotificationBean> getAllNotifications() {
		return notificationService.getAllUsersNotificationes();
	}
	
	@PostMapping("/get/{userId}")
	public List<NotificationBean> getNotifications(@PathVariable(name = "userId") String userId) {
		return notificationService.getNotifications(new UserBean().setId(UUID.fromString(userId)));
	}

	@PutMapping("/edit/{userId}")
	public List<NotificationBean> addOrEditNotification(@PathVariable(name = "userId") String userId, @RequestBody NotificationBean notificationBean) {
		return notificationService.addOrEditNotification(new UserBean().setId(UUID.fromString(userId)), notificationBean);
	}
	
	@DeleteMapping("/delete/{userId}")
	public List<NotificationBean> deleteNotification(@PathVariable(name = "userId") String userId, @RequestBody NotificationBean notificationBean) {
		return notificationService.deleteNotification(new UserBean().setId(UUID.fromString(userId)), notificationBean);
	}
	
}
