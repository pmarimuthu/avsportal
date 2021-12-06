package com.avs.portal.controller;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.avs.portal.bean.UserBean;
import com.avs.portal.bean.UserVerificationBean;
import com.avs.portal.enums.VerificationModeEnum;
import com.avs.portal.enums.VerificationSubjectEnum;
import com.avs.portal.mail.EmailService;
import com.avs.portal.service.UserService;
import com.avs.portal.service.UserVerificationService;

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

	@PostMapping("/list/{userId}")
	public List<UserBean> listUsers(@PathVariable(name = "userId") String userId) {
		UserBean userBean = this.getUser(userId);
		if(userBean != null && !userBean.getHasError()) {
			return userService.getUsers();
		}
		
		throw new ResponseStatusException(HttpStatus.OK, "Unable to find User: " + userId);
	}

	@PostMapping("/get/{userId}")
	public UserBean getUser(@PathVariable(name = "userId") String userId) {
		try {
			return userService.getUser(new UserBean().setId(UUID.fromString(userId)));
		} catch (Exception e) {
			throw new ResponseStatusException(200, "Unable to find User: " + userId, e);
		}
	}

	@PostMapping("/find")
	public UserBean findUser(@RequestBody UserBean userBean) {
		UserBean foundUser = new UserBean();
		
		try {
			List<UserBean> users = userService.getUsersByIdOrEmailAndPhone(userBean);
			
			if(users.size() > 1) {
				System.err.println("Duplicate Users Found. " + users.toString());
				throw new ResponseStatusException(HttpStatus.OK, "Duplicate Users Found. " + users.size());
			}
			
			if(!users.isEmpty()) {
				String otpString = EmailService.sendOTP(users.get(0));
				users.get(0).setExtraInfo(otpString);
				return users.get(0);
			}
			
			foundUser.setHasError(true);
			foundUser.getCustomErrorMessages().add("User Not Found");
			
		} catch (Exception e) {
			foundUser.setHasError(true);
			foundUser.getCustomErrorMessages().add("User not found.");
			foundUser.setThrowable(e);
		}
		
		return foundUser;
	}

	@PostMapping("/create")
	public UserBean createUser(@RequestBody UserBean userBean) {
		UserBean createdUserBean = new UserBean();
		try {
			final UserBean createdUserBean2 = userService.createUser(userBean);
			if(createdUserBean2.getHasError() == false) {
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						doPostCreate(createdUserBean2);
						System.out.println("Post Create done.");
					}
				}).start();
				System.out.println("Created! Post Create Triggered ...");
				return createdUserBean2;
			}
		} catch (Exception e) {
			createdUserBean.setHasError(true);
			createdUserBean.getCustomErrorMessages().add("Email/Phone already exists.");
			System.out.println(e.getMessage());
			return createdUserBean;
		}
		
		return null;
	}

	private void doPostCreate(UserBean user) {
		try {
			EmailService.sendConfirmEmailAddress(user);
		} catch (Exception e) {
			System.err.println("Unable to send email: " + e.getMessage());
		}
	}

	@PutMapping("/edit")
	public UserBean editUser(@RequestBody UserBean user) {
		return userService.editUser(user);
	}

	@DeleteMapping("/delete/{userId}")
	public UserBean deleteUser(@PathVariable(name = "userId") String userId) {
		return userService.deleteUser(new UserBean().setId(UUID.fromString(userId)));
	}
	
	@GetMapping(path = "/verify/email/{userId}")
	public @ResponseBody UserBean verifyUserEmail(@PathVariable(name = "userId") String userId) {
		try {
			UserBean userBean = userService.getUser(new UserBean().setId(UUID.fromString(userId)));
			if(userBean == null || userBean.getId() == null)
				throw new ResponseStatusException(200, "Unable to find User: " + userId, null);
			
			UserVerificationBean verificationBean = new UserVerificationBean();
			verificationBean.setVerificationMode(VerificationModeEnum.EMAIL);
			verificationBean.setVerificationSubject(VerificationSubjectEnum.USER);
			verificationBean.setVerifiedBy(null);
			verificationBean.setVerifiedBy(UUID.fromString("5e114a10-6275-47f5-bf3b-a9c0e8233f62")); // ADMIN
			
			Set<UserVerificationBean> userVerifications = userVerificationService.createOrEditUserVerification(userBean, verificationBean);
			
			return userBean;
			
		} catch (Exception e) {
			throw new ResponseStatusException(200, "Unable to find User: " + userId, e);
		}
	}

	
}
