package com.avs.portal.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avs.portal.bean.LoginBean;
import com.avs.portal.bean.UserBean;
import com.avs.portal.entity.LoginHistory;
import com.avs.portal.entity.User;
import com.avs.portal.enums.LoginKeyEnum;
import com.avs.portal.enums.UserAgentEnum;
import com.avs.portal.repository.LoginHistoryRepository;
import com.avs.portal.repository.UserRepository;
import com.avs.portal.util.CommonUtil;
import com.avs.portal.util.Constants;

@Service
public class AuthService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private LoginHistoryRepository loginHistoryRepository;
	
	public UserBean attemptLogin(LoginBean loginBean, String ipAddress, UserAgentEnum userAgentEnum) {
		
		UserBean userBean = new UserBean();
		if(loginBean == null || loginBean.getLoginId() == null || loginBean.getPassword() == null) {
			userBean.setHasError(true);
			userBean.getCustomErrorMessages().add("Invalid Inputs");
			return userBean;
		}
		
		String loginId = loginBean.getLoginId();
		String password = loginBean.getPassword();
		
		LoginKeyEnum keyType = null;
		
		UUID userUUID = null;
		try {
			userUUID = UUID.fromString(loginId);
			keyType = LoginKeyEnum.UUID;
		} catch (Exception e) {
			//System.err.println("given loginId is not a UUID");
		}
		
		Long phone = null;
		try {
			if(CommonUtil.isValidPhone(Long.parseLong(loginId))) {
				phone = Long.parseLong(loginId);
				keyType = LoginKeyEnum.PHONE;
				
			}
		} catch (NumberFormatException e) {
			//System.err.println("given loginId is not a PHONE Number");
		}
		
		String email = null;
		try {
			if(CommonUtil.isValidEmail(loginId)) {
				email = loginId;
				keyType = LoginKeyEnum.EMAIL;
			}			
		} catch (NumberFormatException e) {
			//System.err.println("given loginId is not a EMAIL");
		}
		
		if(keyType == null) {
			userBean.setHasError(true);
			userBean.getCustomErrorMessages().add("Invalid Inputs");
			return userBean;
		}
		
		User user = null;
		
		switch (keyType.toString()) {
		case "UUID":
			user = userRepository.findById(userUUID).orElse(null);
			break;
			
		case "PHONE":
			List<User> usersByPhone = userRepository.findByPhone(phone);
			if(usersByPhone.size() == 1)
				user = usersByPhone.get(0);
			break;
			
		case "EMAIL":
			List<User> usersByEmail = userRepository.findByEmail(email);
			if(usersByEmail.size() == 1)
				user = usersByEmail.get(0);
			break;

		default:
			break;
		}
		
		if(user == null) {
			userBean.setHasError(true);
			userBean.getCustomErrorMessages().add("Invalid Credential");
			return userBean;
		}
		
		if(user.getUserCredential().getPassword().equals(password)) {
			userBean = doPostLoginAttempt(user, ipAddress, userAgentEnum, Constants.LOGIN_SUCCESS);
			return userBean;
		}
		else {
			userBean = doPostLoginAttempt(user, ipAddress, userAgentEnum, Constants.LOGIN_FAILED);
			return userBean;
		}
		
	}

	private UserBean doPostLoginAttempt(User user, String ipAddress, UserAgentEnum userAgentEnum, Boolean flag) {
		UserBean userBean = new UserBean();
		
		LoginHistory loginHistory = new LoginHistory();
		loginHistory.setIpAddress(ipAddress);
		loginHistory.setUserAgent(userAgentEnum);
		loginHistory.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));		
		
		if(flag == Constants.LOGIN_SUCCESS) {
			loginHistory.setConsecutiveFailedLoginCount(0);
		}
		else if(flag == Constants.LOGIN_FAILED) {
			List<LoginHistory> loginHistories = user.getLoginHistories()
				.stream().sorted(Comparator.comparing(LoginHistory :: getUpdatedOn).reversed())
				.collect(Collectors.toList());
			
			if(loginHistories != null && loginHistories.size() > 0) {
				LoginHistory recentHistory = loginHistories.get(0);				
				loginHistory.setConsecutiveFailedLoginCount(recentHistory.getConsecutiveFailedLoginCount() + 1);
				userBean.setHasError(true);
				userBean.getCustomErrorMessages().clear();
				userBean.getCustomErrorMessages().add("Login attempt failed.");
				userBean.getCustomErrorMessages().add("Consecutive failed attempt count: " + loginHistory.getConsecutiveFailedLoginCount());
			}
		}
		else {
			userBean.setHasError(true);
			userBean.getCustomErrorMessages().clear();
			userBean.getCustomErrorMessages().add("[TODO] Unknown Login Attempt Status.");
		}
		
		loginHistory.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		user.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		loginHistory.setUser(user);
		user.getLoginHistories().add(loginHistory);
		
		loginHistory = loginHistoryRepository.save(loginHistory);
		userRepository.save(user);
		
		return userBean;
	}

}
