package com.avs.portal.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avs.portal.bean.UserAccountStatusBean;
import com.avs.portal.bean.UserBean;
import com.avs.portal.entity.User;
import com.avs.portal.entity.UserAccountStatus;
import com.avs.portal.repository.UserAccountStatusRepository;
import com.avs.portal.repository.UserRepository;

@Service
public class UserAccountStatusService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserAccountStatusRepository userAccountStatusRepository;
	
	public UserAccountStatusBean getUserAccountStatus(UserBean userBean) {
		if(userBean == null || userBean.getId() == null)
			return null;
		
		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null)
			return null;
		
		UserAccountStatus userAccountStatus = user.getUserAccountStatus();
		if(userAccountStatus == null)
			return null;
		
		return userAccountStatus.toBean();
	}
	
	public UserAccountStatusBean updateAccountStatus(UserBean userBean, UserAccountStatusBean userAccountStatusBean) {
		if(userBean == null || userBean.getId() == null || userAccountStatusBean == null || userAccountStatusBean.getId() == null)
			return null;

		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null) 
			return null;

		UserAccountStatus userAccountStatus = user.getUserAccountStatus();
		if(userAccountStatus == null)
			return null;

		userAccountStatus.setIsActive(userAccountStatusBean.getIsActive());
		userAccountStatus.setIsBlocked(userAccountStatusBean.getIsBlocked());
		userAccountStatus.setIsDeleted(userAccountStatusBean.getIsDeleted());
		userAccountStatus.setIsLocked(userAccountStatusBean.getIsLocked());
		userAccountStatus.setIsVerified(userAccountStatusBean.getIsVerified());

		userAccountStatus.setUser(user);
		user.setUserAccountStatus(userAccountStatus);

		userAccountStatus.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));

		user = userRepository.save(user); 
		
		return user.getUserAccountStatus().toBean();	
	}

	public List<UserAccountStatusBean> getAllUsersAccountStatus() {
		return userRepository.findAll()
				.stream().map(User :: getUserAccountStatus).collect(Collectors.toList())
				.stream().map(UserAccountStatus :: toBean).collect(Collectors.toList());
	}

	public UserBean createAccountStatus(UserBean userBean, UserAccountStatusBean userAccountStatusBean) {
		if(userBean == null || userBean.getId() == null || userAccountStatusBean == null)
		return null;
		
		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null)
			return null;
		
		UserAccountStatus userAccountStatus = user.getUserAccountStatus();
		if(userAccountStatus != null) {
			System.err.println("UserAccountStatus already exists for the User: " + user.getId());
			return null;
		}
		
		userAccountStatus = new UserAccountStatus()
				.setIsActive(userAccountStatusBean.getIsActive())
				.setIsBlocked(userAccountStatusBean.getIsBlocked())
				.setIsLocked(userAccountStatusBean.getIsLocked())
				.setIsVerified(userAccountStatusBean.getIsVerified())
				.setIsDeleted(userAccountStatusBean.getIsDeleted())
				.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()))
				.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		user.setUserAccountStatus(userAccountStatus);
		userAccountStatus.setUser(user);
		
		user = userRepository.save(user);
		
		return user.toBean();		
		
	}

	public UserBean deleteAccountStatus(UserBean userBean, UserAccountStatusBean userAccountStatusBean) {
		if(userBean == null || userBean.getId() == null || userAccountStatusBean == null || userAccountStatusBean.getUserId() == null)
			return null;
		
		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null)
			return null;
		
		UserAccountStatus userAccountStatus = user.getUserAccountStatus();
		if(!userAccountStatus.getId().equals(userAccountStatusBean.getId())) {
			System.err.println("AccountStatus not belongs to the User: " + userBean.getId());
			return null;
		}
		
		userAccountStatus.setUser(null);
		user.setUserAccountStatus(null);
		
		userRepository.delete(user);
		
		return user.toBean();
				
	}
	
}
