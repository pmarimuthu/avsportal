package com.avs.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avs.portal.bean.NotificationBean;
import com.avs.portal.bean.UserBean;
import com.avs.portal.service.NotificationService;

@RestController
@RequestMapping(path = "/api/user-relation-tome-map")
public class UserRelationToMeMapController {

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
	
	@PostMapping("/user")
	public List<NotificationBean> getNotifications(@RequestBody UserBean userBean) {
		return notificationService.getNotifications(userBean);
	}

	@PostMapping("/update")
	public List<NotificationBean> addOrEditNotification(@RequestBody NotificationBean notificationBean) {
		return notificationService.addOrEditNotification(notificationBean);
	}
	
	@DeleteMapping("/delete")
	public List<NotificationBean> deleteNotification(@RequestBody NotificationBean notificationBean) {
		return notificationService.deleteNotification(notificationBean);
	}
	
}
