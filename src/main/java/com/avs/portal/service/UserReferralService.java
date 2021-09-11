package com.avs.portal.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avs.portal.bean.UserBean;
import com.avs.portal.bean.UserReferralBean;
import com.avs.portal.entity.User;
import com.avs.portal.entity.UserReferral;
import com.avs.portal.enums.UserReferralStatusEnum;
import com.avs.portal.repository.UserReferralRepository;
import com.avs.portal.repository.UserRepository;
import com.avs.portal.util.CommonUtil;

@Service
public class UserReferralService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserReferralRepository userReferralRepository;

	// READ {ALL}
	public List<UserReferralBean> getUserReferrals() {
		return userReferralRepository.findAll().stream().map(UserReferral :: toBean).collect(Collectors.toList());
	}

	// READ {MY}
	public List<UserReferralBean> getUserReferrals(UserBean userBean) {
		if(userBean == null || userBean.getId() == null)
			return Collections.emptyList();
		
		return userReferralRepository.findByReferrer(userBean.getId()).stream().map(UserReferral :: toBean).collect(Collectors.toList());
	}

	// CREATE
	public UserReferralBean createUserReferral(UserBean userBean) {
		if(userBean == null || userBean.getId() == null)
			return null;
		
		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null)
			return null;
		
		UserReferral userReferral = new UserReferral()
				.setReferrer(userBean.getId())
				.setReferralCode(CommonUtil.generateSixDigits())
				.setStatus(UserReferralStatusEnum.UNAVAILED)
				.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()))
				.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		userReferral = userReferralRepository.save(userReferral);
		
		return userReferral.toBean();
	}

	// EDIT
	public UserReferralBean editUserReferral(UserReferralBean userReferralBean) {
		if(userReferralBean == null || userReferralBean.getId() == null)
			return null;
		
		UserReferral userReferral = userReferralRepository.findById(userReferralBean.getId()).orElse(null);
		if(userReferral == null) {
			System.err.println("UserReferral doesn't exists to Edit.");
			return null;
		}
		
		userReferral.setStatus(UserReferralStatusEnum.UNAVAILED)
			.setReferee(userReferralBean.getReferee())
			.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		userReferral = userReferralRepository.save(userReferral);
		
		return userReferral.toBean();
	}
	
	// DELETE
	public UserReferralBean deleteUserReferral(UserBean userBean, UserReferralBean userReferralBean) {
		if(userBean == null || userBean.getId() == null || userReferralBean == null || !userBean.getId().equals(userReferralBean.getReferrer()))
			return null;
		
		UserReferral userReferral = userReferralRepository.findById(userReferralBean.getId()).orElse(null);
		if(userReferral == null)
			return null;
		
		userReferralRepository.delete(userReferral);
		
		return userReferral.toBean();				
	}
	
}
