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
		
		if(loginBean == null || loginBean.getLoginId() == null || loginBean.getPassword() == null)
			return null;
		
		String loginId = loginBean.getLoginId();
		String password = loginBean.getPassword();
		
		LoginKeyEnum keyType = null;
		
		UUID userUUID = null;
		try {
			userUUID = UUID.fromString(loginId);
			keyType = LoginKeyEnum.UUID;
		} catch (Exception e) {
			System.err.println("given loginId is not a UUID");
		}
		
		Long phone = null;
		try {
			if(CommonUtil.isValidPhone(Long.parseLong(loginId))) {
				phone = Long.parseLong(loginId);
				keyType = LoginKeyEnum.PHONE;
				
			}
		} catch (NumberFormatException e) {
			System.err.println("given loginId is not a PHONE Number");
		}
		
		String email = null;
		try {
			if(CommonUtil.isValidEmail(loginId)) {
				email = loginId;
				keyType = LoginKeyEnum.EMAIL;
			}			
		} catch (NumberFormatException e) {
			System.err.println("given loginId is not a EMAILr");
		}
		
		if(keyType == null)
			return null;
		
		User user = null;
		
		switch (keyType.toString()) {
		case "UUID":
			user = userRepository.findById(userUUID).orElse(null);
			break;
			
		case "PHONE":
			List<User> users = userRepository.findByPhone(phone);
			if(users.size() == 1)
				user = users.get(0);
			break;
			
		case "EMAIL":
			List<User> users2 = userRepository.findByEmail(email);
			if(users2.size() == 1)
				user = users2.get(0);
			break;

		default:
			break;
		}
		
		if(user == null)
			return null;
		
		if(user.getUserCredential().getPassword().equals(password)) {
			doPostLoginAttempt(user, ipAddress, userAgentEnum, Constants.LOGIN_SUCCESS);
			return user.toBean();
		}
		else {
			doPostLoginAttempt(user, ipAddress, userAgentEnum, Constants.LOGIN_FAILED);
		}
		
		return null;
	}

	private void doPostLoginAttempt(User user, String ipAddress, UserAgentEnum userAgentEnum, Boolean flag) {
		LoginHistory loginHistory = new LoginHistory();
		loginHistory.setIpAddress(null);
		loginHistory.setDeviceType(null);
		loginHistory.setUserAgent(null);		
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
			}
		}
		else {
			System.err.println("Unknown Login Attempt Status.");
		}
		
		loginHistory.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		user.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		loginHistory.setUser(user);
		user.getLoginHistories().add(loginHistory);
		
		loginHistory = loginHistoryRepository.save(loginHistory);
		userRepository.save(user);
	}

}
