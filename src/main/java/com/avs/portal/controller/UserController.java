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

	@PostMapping("/get")
	public UserBean getUser(@RequestBody UserBean userBean) {
		return userService.getUser(userBean);
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
				System.err.println("TODO ## Generated OTP: " + otpString);
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
			System.out.println(e.getMessage());
			e.printStackTrace();
			
			createdUserBean.setHasError(true);
			createdUserBean.getCustomErrorMessages().add("Email/Phone already exists.");
			return createdUserBean;
		}
		
		return null;
	}

	private void doPostCreate(UserBean user) {
		try {
			EmailService.sendConfirmEmailAddress(user);
		} catch (Exception e) {
			System.out.println(e.getMessage());
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
	public @ResponseBody UserBean verifyUserEmail(@PathVariable(name = "userId") String userId) {
		try {
			UserBean userBean = userService.getUser(new UserBean().setId(UUID.fromString(userId)));
			if(userBean == null || userBean.getId() == null)
				throw new ResponseStatusException(200, "Unable to find User: " + userId, null);
			
			UserVerificationBean verificationBean = new UserVerificationBean();
			verificationBean.setVerificationMode(VerificationModeEnum.EMAIL);
			verificationBean.setVerificationSubject(VerificationSubjectEnum.USER);
			verificationBean.setVerifiedBy(null);
			verificationBean.setVerifiedBy(UUID.fromString(userId)); // ADMIN
			
			userBean = userVerificationService.updateUserVerification(userBean, verificationBean);
			
			return userBean;
			
		} catch (Exception e) {
			throw new ResponseStatusException(200, "Unable to find User: " + userId, e);
		}
	}
	
}
