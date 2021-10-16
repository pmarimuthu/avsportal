package com.avs.portal.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

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
	private UserService userService;

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

		UserReferral userReferral;
		userReferral = new UserReferral()
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

	// --------- Business APIs --------- 

	public UserReferralBean activateReferee(UserReferralBean userReferralBean) {
		if(userReferralBean == null || userReferralBean.getReferralCode() == null || userReferralBean.getReferralCode().length() != 6)
			return null;

		UserReferral userReferral = userReferralRepository.findByReferralCode(userReferralBean.getReferralCode()).orElse(null);
		if(userReferral == null || !userReferral.getStatus().equals(UserReferralStatusEnum.UNAVAILED))
			return null;

		userReferral.setStatus(UserReferralStatusEnum.AVAILED);
		userReferral.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));

		userReferral = userReferralRepository.save(userReferral);

		return userReferral.toBean();		
	}

	@Transactional
	public UserBean joinUser(UserReferralBean userReferralBean, UserBean userBean) {
		UserBean createdUserBean = new UserBean();
		if(userReferralBean == null || userReferralBean.getReferralCode() == null || userReferralBean.getReferralCode().length() != 6 
				|| userBean == null) {
			createdUserBean.setHasError(true);
			createdUserBean.getCustomErrorMessages().add("Invalid Inputs");
			return createdUserBean;
		}

		UserReferral userReferral = userReferralRepository.findByReferralCode(userReferralBean.getReferralCode()).orElse(null);
		if(userReferral == null || !userReferral.getStatus().equals(UserReferralStatusEnum.UNAVAILED)) {
			createdUserBean.setHasError(true);
			createdUserBean.getCustomErrorMessages().add("Referral Code is already Availed/Expired");
			return createdUserBean;
		}
		
		try {
			userReferral.setStatus(UserReferralStatusEnum.AVAILED);
			userReferral.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));

			userReferral = userReferralRepository.save(userReferral);
			return userService.createUser(userBean);
		} catch (Exception e) {
			userBean.setHasError(true);
			userBean.getCustomErrorMessages().add("Email/Phone already exists.");
		}
		return userBean;
	}

}
