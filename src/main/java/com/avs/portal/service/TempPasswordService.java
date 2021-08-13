package com.avs.portal.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avs.portal.bean.TempPasswordBean;
import com.avs.portal.bean.UserBean;
import com.avs.portal.entity.TempPassword;
import com.avs.portal.entity.User;
import com.avs.portal.repository.TempPasswordRepository;
import com.avs.portal.repository.UserRepository;
import com.avs.portal.util.CommonUtil;

@Service
public class TempPasswordService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TempPasswordRepository tempPasswordRepository;
	
	// READ {ALL}
	public List<TempPasswordBean> getAllUsersTempPasswords() {
		return tempPasswordRepository.findAll().stream().map(TempPassword :: toBean).collect(Collectors.toList());
	}

	// READ {ONE}
	public TempPasswordBean getTempPassword(UserBean bean) {
		if(bean == null || bean.getId() == null)
			return null;
		
		User user = userRepository.findById(bean.getId()).orElse(null);
		if(user == null || user.getUserInformation() == null)
			return null;
		
		return user.getTempPassword().toBean();
	}

	// CREATE
	public TempPasswordBean createTempPassword(UserBean userBean) {
		if(userBean == null || userBean.getId() == null)
			return null;

		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null)
			return null;
		
		TempPassword tempPassword = user.getTempPassword();
		if(tempPassword != null) {
			System.err.println("TempPassword exists !! for the User: " + tempPassword.getUser().getId());
			return null;
		}
		
		tempPassword = new TempPassword();
		tempPassword.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
		tempPassword.setGeneratedPassword(CommonUtil.generateTempPassword());
		tempPassword.setIsUsed(Boolean.FALSE);
		tempPassword.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		user.setTempPassword(tempPassword);
		tempPassword.setUser(user);
		
		tempPassword = tempPasswordRepository.save(tempPassword);
		
		return tempPassword.toBean();
	}

	// DELETE
	public UserBean deleteTempPassword(UserBean userBean) {
		if(userBean == null || userBean.getId() == null)
			return userBean;

		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null || user.getUserInformation() == null)
			return null;

		user.setTempPassword(null);
		user.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		user = userRepository.save(user);

		return user.toBean();
	}

}
