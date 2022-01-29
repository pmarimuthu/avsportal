package com.avs.portal.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.regex.PatternSyntaxException;
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
import com.avs.portal.util.Logger;

import io.jsonwebtoken.lang.Collections;

@Service
public class AuthService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserFamilyMapService userFamilyMapService;
	
	@Autowired
	private LoginHistoryRepository loginHistoryRepository;
	
	public UserBean attemptLogin(LoginBean loginBean, String ipAddress, UserAgentEnum userAgentEnum) {

		if(loginBean == null || loginBean.getLoginId() == null || loginBean.getPassword() == null) {
			UserBean userBean = new UserBean()
					.setHasError(true);
			
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
		} catch (IllegalArgumentException e) {
			Logger.logError(e.getMessage());
		}
		
		Long phone = null;
		try {
			if(CommonUtil.isValidPhone(Long.parseLong(loginId))) {
				phone = Long.parseLong(loginId);
				keyType = LoginKeyEnum.PHONE;				
			}
		} catch (NumberFormatException e) {
			Logger.logError(e.getMessage());
		}
		
		String email = null;
		try {
			if(CommonUtil.isValidEmail(loginId)) {
				email = loginId;
				keyType = LoginKeyEnum.EMAIL;
			}			
		} catch (PatternSyntaxException e) {
			Logger.logError(e.getMessage());
		}
		
		if(keyType == null) {
			UserBean userBean = new UserBean()
					.setHasError(true);
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
			UserBean userBean = new UserBean().setHasError(true);
			userBean.getCustomErrorMessages().add("Invalid Credential");
			return userBean;
		}
		
		if(user.getUserCredential().getPassword().equals(password)) {
			return doPostLoginAttempt(user, ipAddress, userAgentEnum, Constants.LOGIN_SUCCESS);
		}
		else {
			return doPostLoginAttempt(user, ipAddress, userAgentEnum, Constants.LOGIN_FAILED);
		}
		
	}

	private UserBean doPostLoginAttempt(User user, String ipAddress, UserAgentEnum userAgentEnum, Boolean flag) {
		if(user == null)
			return null;
		
		List<LoginHistory> loginHistories = user.getLoginHistories()
				.stream().sorted(Comparator.comparing(LoginHistory :: getUpdatedOn).reversed())
				.limit(10)
				.collect(Collectors.toList());
		
		LoginHistory loginHistory = new LoginHistory();
		loginHistory.setIpAddress(ipAddress);
		loginHistory.setUserAgent(userAgentEnum);
		loginHistory.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));		
		
		if(flag == Constants.LOGIN_SUCCESS) {
			loginHistory.setConsecutiveFailedLoginCount(0);
		}
		else if(flag == Constants.LOGIN_FAILED) {
			if(!Collections.isEmpty(loginHistories)) {
				LoginHistory recentHistory = loginHistories.get(0);				
				loginHistory.setConsecutiveFailedLoginCount(recentHistory.getConsecutiveFailedLoginCount() + 1);
				user.setHasError(true);
				user.getCustomErrorMessages().clear();
				user.getCustomErrorMessages().add("Login attempt failed.");
				user.getCustomErrorMessages().add("Consecutive failed attempt count: " + loginHistory.getConsecutiveFailedLoginCount());
			}
		}
		else {
			user.setHasError(true);
			user.getCustomErrorMessages().clear();
			user.getCustomErrorMessages().add("[TODO] Unknown Login Attempt Status.");
		}
		
		loginHistory.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		user.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		loginHistory.setUser(user);
		user.getLoginHistories().add(loginHistory);
		
		loginHistoryRepository.save(loginHistory);
		User theUser = userRepository.save(user);
		
		return theUser.toBean()
				.setDistinctFamilyHeads(userFamilyMapService.listDistinctFamilyHeads());
	}

}
