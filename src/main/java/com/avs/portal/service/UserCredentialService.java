package com.avs.portal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avs.portal.bean.UserBean;
import com.avs.portal.bean.UserCredentialBean;
import com.avs.portal.entity.UserCredential;
import com.avs.portal.repository.UserCredentialRepository;
import com.avs.portal.util.AVSPortalSecurityUtil;

@Service
public class UserCredentialService {

	@Autowired
	private UserCredentialRepository userCredentialRepository;
	
	@Autowired
	private UserService userService;
	
	public UserBean onAuthenticateUser(UserBean userBean, UserCredentialBean userCredentialBean) throws Exception {
		if(userBean == null || userCredentialBean == null)
			return onPostAuthenticateUser(userBean, null);
		
		userBean = userService.getUser(userBean);
		if(userBean == null)
			return onPostAuthenticateUser(userBean, null);
		
		List<UserCredential> userCredentials = userCredentialRepository.findByUserIdAndPassword(userBean.getId(), userCredentialBean.getPassword());
		if(userCredentials.size() == 0)
			return onPostAuthenticateUser(userBean, null);
		
		if(userCredentials.size() == 1)
			return onPostAuthenticateUser(userBean, Boolean.TRUE);
		
		if(userCredentials.size() > 1)
			throw new Exception("[ERR] more than one user with same credentials.");
		
		return null;
	}
	
	private UserBean onPostAuthenticateUser(UserBean userBean, Boolean authResult) throws Exception {
		if(authResult == null)
			return null;
		
		if(authResult == Boolean.FALSE) {
			// ++ LoginHistoryBean
			return null;
		}
		
		if(authResult == Boolean.TRUE) {
			// ++ JWT + LoginHistoryBean
			String jwtToken = AVSPortalSecurityUtil.getJWTToken(userBean);
			System.out.println(jwtToken);
		}
			
		return null;
	}

}
