package com.avs.portal.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.avs.portal.bean.UserBean;
import com.avs.portal.bean.UserVerificationBean;
import com.avs.portal.enums.LogStatusEnum;
import com.avs.portal.enums.VerificationSubjectEnum;
import com.avs.portal.exception.AVSApplicationException;
import com.avs.portal.mail.EmailService;
import com.avs.portal.service.UserService;
import com.avs.portal.service.UserVerificationService;
import com.avs.portal.util.CommonUtil;
import com.avs.portal.util.Logger;

@RestController
@RequestMapping(path = "/api/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserVerificationService userVerificationService;

	@GetMapping("/health")
	public String sayHello() {
		return "UserController is Alive!!";
	}

	@PostMapping("/list")
	public List<UserBean> listUsers() {
		return userService.getUsers();
	}

	@PostMapping("/get")
	public UserBean getUser(@RequestBody UserBean userBean) {
		return userService.getUser(userBean);
	}

	@PostMapping("/fetch-id")
	public UserBean fetchUser(@RequestBody UserBean userBean) {
		if(userBean == null || userBean.getId() == null)
			return null;
		
		return userService.getUser(new UserBean().setId(userBean.getId()));
	}
	
	@PostMapping("/list-user-ids")
	public List<UUID> listUserIds() {		
		return userService.getAllUserId();
	}

	@PostMapping("/find")
	public UserBean findUser(@RequestBody UserBean userBean) {
		UserBean foundUserBean = new UserBean();
		
		try {
			List<UserBean> users = userService.getUsersByIdOrEmailAndPhone(userBean);
			
			if(users.size() > 1) {
				throw new ResponseStatusException(HttpStatus.OK, "Duplicate Users Found. " + users.size());
			}
			
			if(!users.isEmpty()) {
				String otpString = EmailService.sendOTP(users.get(0));
				Logger.log(LogStatusEnum.INFO, "1. UserController > findUser >", "TODO ## Generated OTP: " + otpString);
				foundUserBean = users.get(0);
				
				return foundUserBean;
			}
			
			foundUserBean.setHasError(true);
			foundUserBean.getCustomErrorMessages().add("User Not Found");
			
		} catch (Exception e) {
			foundUserBean.setHasError(true);
			foundUserBean.getCustomErrorMessages().add("User not found.");
			foundUserBean.setThrowable(e);
		}
		
		return foundUserBean;
	}

	@PostMapping("/create")
	public UserBean createUser(@RequestBody UserBean userBean) {
		UserBean createdUserBean = new UserBean();

		final UserBean createdUserBean2 = userService.createUser(userBean);
		if(!createdUserBean2.getHasError()) {
			new Thread( () -> {
				try {
					doPostCreate(createdUserBean2);
					Logger.log(LogStatusEnum.INFO, "2. UserController > createUser > postCreate >", "done.");
				} catch (AVSApplicationException e) {
					Logger.log(LogStatusEnum.ERROR, "3. UserController > createUser >", e.getMessage());
				}
			}).start();
			Logger.log(LogStatusEnum.SUCCESS, "4. UserController > createUser >",  "Created! Post Create Triggered ...");
			return createdUserBean2;
		}

		createdUserBean.setHasError(true);
		createdUserBean.getCustomErrorMessages().add("Email/Phone already exists. 1");
		return createdUserBean;
	}

	@PostMapping("/create/proxy")
	public UserBean createProxyUser(@RequestBody UserBean userBean) {
		userBean.setProxy(true);
		userBean.setPhone(CommonUtil.generateProxyPhone());
		userBean.setEmail(CommonUtil.generateProxyEmail(userBean.getPhone()));
		
		UserBean createdUserBean = new UserBean();

		final UserBean createdUserBean2 = userService.createUser(userBean);
		if(!createdUserBean2.getHasError()) {
			new Thread( () -> {
				try {
					doPostCreate(createdUserBean2);
					Logger.log(LogStatusEnum.INFO, "5. UserController > createUser > postCreate >", "done.");
				} catch (AVSApplicationException e) {
					Logger.log(LogStatusEnum.ERROR, "6. UserController > createUser >", e.getMessage());
				}
			}).start();
			Logger.log(LogStatusEnum.SUCCESS, "7. UserController > createUser >",  "Created! Post Create Triggered ...");
			return createdUserBean2;
		}

		createdUserBean.setHasError(true);
		createdUserBean.getCustomErrorMessages().add("Email/Phone already exists. 2");
		return createdUserBean;
	}

	private void doPostCreate(UserBean user) throws AVSApplicationException {
		try {
			EmailService.sendConfirmEmailAddress(user);
		} catch (Exception e) {
			Logger.log(LogStatusEnum.ERROR, "8. UserController > doPostCreate > sendConfirmEmailAddress", e.getMessage());
			throw new AVSApplicationException(e.getMessage(), e);
		}
	}

	@PatchMapping("/update")
	public UserBean updateUser(@RequestBody UserBean user) {
		return userService.updateUser(user);
	}

	@DeleteMapping("/delete")
	public UserBean deleteUser(@RequestBody UserBean userBean) {
		return userService.deleteUser(userBean);
	}
	
	@GetMapping(path = "/verify/email/{userId}")
	public UserBean verifyUserEmail(@PathVariable(name = "userId") String userId) {
		try {
			UserBean userBean = userService.getUser(new UserBean().setId(UUID.fromString(userId)));
			if(userBean == null || userBean.getId() == null)
				throw new ResponseStatusException(200, "1. Unable to find User: " + userId, null);
			
			UserVerificationBean verificationBean = new UserVerificationBean();
			verificationBean.setComment(null);
			verificationBean.setVerificationSubject(VerificationSubjectEnum.USER);
			verificationBean.setVerifiedBy(null);
			verificationBean.setVerifiedBy(UUID.fromString(userId));
			
			userBean = userVerificationService.updateUserVerification(userBean, verificationBean);
			
			return userBean;
			
		} catch (Exception e) {
			throw new ResponseStatusException(200, "2. Unable to find User: " + userId, e);
		}
	}
	
}
