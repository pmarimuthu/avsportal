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
import com.avs.portal.enums.UserAgentEnum;
import com.avs.portal.repository.LoginHistoryRepository;
import com.avs.portal.repository.UserCredentialRepository;
import com.avs.portal.repository.UserFamilyMapRepository;
import com.avs.portal.repository.UserRepository;
import com.avs.portal.util.Constants;

import io.jsonwebtoken.lang.Collections;

@Service
public class AuthService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private LoginHistoryRepository loginHistoryRepository;
	
	@Autowired
	private UserCredentialRepository userCredentialRepository;
	
	@Autowired
	private UserFamilyMapRepository userFamilyMapRepository;

	public UserBean attemptLogin(LoginBean loginBean) {
		if(loginBean == null || loginBean.getLoginId() == null || loginBean.getPassword() == null)
			return null;
				
		UUID authUserId = userCredentialRepository.fnAuthUser(loginBean.getLoginId(), loginBean.getPassword());
		UserBean userBean = new UserBean();
		if(authUserId == null) {
			userBean = new UserBean().setHasError(true);
			userBean.getCustomErrorMessages().add("Invalid Credentials");
			return userBean;
		}
		
		List<String[]> distinctFamilyHeads = userFamilyMapRepository.nativeQueryDistinctFamilyHeads();
		System.out.println("DISTINCT FAMILY HEADS: [" + distinctFamilyHeads.size() + "]");
		
		List<String[]> distinctParentFamilyHeads = userFamilyMapRepository.nativeQueryDistinctParentFamilyHeads();
		System.out.println("DISTINCT PARENT FAMILY HEADS: [" + distinctParentFamilyHeads.size() + "]");
		
		User user = userRepository.findById(authUserId).orElse(null);
		
		if(user == null) {
			userBean = new UserBean().setHasError(true);
			userBean.getCustomErrorMessages().add("User Not Found.");
			return userBean;
		}

		return user.toBean();
	}

	public UserBean doPostLoginAttempt(UserBean userBean, String ipAddress, UserAgentEnum userAgentEnum, Boolean flag) {
		if(userBean == null || userBean.getId() == null || flag == null)
			return null;

		User user = userRepository.findById(userBean.getId()).orElse(null);
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

		return theUser.toBean();
	}

}
