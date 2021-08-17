package com.avs.portal.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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

@RestController
@RequestMapping(path = "/api/auth")
public class AuthController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private LoginHistoryRepository loginHistoryRepository;
	
	@GetMapping("/health")
	public String sayHello() {
		return "AuthController is Alive!!";
	}
	
	@GetMapping("/ip")
	public String getIPAddress() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
		        .getRequest();
		
		String remoteAddress = "Unknown!!";
		
        if (request != null) {
        	remoteAddress = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddress == null || "".equals(remoteAddress)) {
            	remoteAddress = request.getRemoteAddr();
            }
        }

        return remoteAddress;
	}
	
	@PostMapping("/login")
	public UserBean attemptLogin(@RequestBody LoginBean bean) {
		
		if(bean == null || bean.getLoginId() == null || bean.getPassword() == null)
			return null;
		
		String loginId = bean.getLoginId();
		String password = bean.getPassword();
		
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
			doPostLoginAttempt(user, Constants.LOGIN_SUCCESS);
			return user.toBean();
		}
		else {
			doPostLoginAttempt(user, Constants.LOGIN_FAILED);
		}
		
		return null;
	}

	private void doPostLoginAttempt(User user, Boolean flag) {
		LoginHistory loginHistory = new LoginHistory();
		loginHistory.setIpAddress(null);
		loginHistory.setDeviceType(null);
		loginHistory.setUserAgent(null);		
		loginHistory.setIpAddress(getIPAddress());
		loginHistory.setUserAgent(getUserAgent());;
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

	private UserAgentEnum getUserAgent() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
		        .getRequest();
		
		String userAgent = "Unknown!!";
		
        if (request != null) {
        	userAgent = request.getHeader("User-Agent");
            if (userAgent == null || "".equals(userAgent)) {
            	userAgent = request.getRemoteAddr();
            }
        }

        return UserAgentEnum.OTHER;
	}
	
}
