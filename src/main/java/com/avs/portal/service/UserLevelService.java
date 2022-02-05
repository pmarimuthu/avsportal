package com.avs.portal.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avs.portal.bean.UserBean;
import com.avs.portal.entity.User;
import com.avs.portal.entity.UserFamilyMap;
import com.avs.portal.enums.FamilyMemberTitleEnum;
import com.avs.portal.enums.MaritalStatusEnum;
import com.avs.portal.repository.UserFamilyMapRepository;
import com.avs.portal.repository.UserRepository;


@Service
public class UserLevelService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserFamilyMapRepository userFamilyMapRepository;
	
	/**
	 * If Given user
	 * 		is-married: add spouse to the list
	 * 		else: add all siblings to the list
	 * 
	 * @param userBean
	 * @return
	 */
	public List<UserBean> getLevelZeroUsers(UserBean userBean) {
		if(userBean == null || userBean.getId() == null)
			return null;
		
		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null)
			return null;
		
		List<UserBean> levelZeroUsers = new ArrayList<>();
		levelZeroUsers.add(user.toBean());
		
		if(user.getUserProfile().getMaritalStatus().equals(MaritalStatusEnum.MARRIED)) {
			if(user.getUserFamilyMap().getTitle().equals(FamilyMemberTitleEnum.HEAD)) {
				User spouseUser = getSpouse(user);
				if(spouseUser != null)
					levelZeroUsers.add(spouseUser.toBean());
			}
			else if(user.getUserFamilyMap().getTitle().equals(FamilyMemberTitleEnum.SPOUSE)){
				// get Head-Spouse
				
			}
			
		}
		
		
		else {
			// get Siblings
		}
		
		System.out.println(levelZeroUsers.size());
		
		return levelZeroUsers;
	}

	private User getSpouse(User user) {
		if(user.getUserFamilyMap().getTitle().equals(FamilyMemberTitleEnum.HEAD)) {
			// get Spouse
			UserFamilyMap userFamilyMap = userFamilyMapRepository.findDistinctByFamilyHeadIdNotNull().stream().findFirst().orElse(null);
			if(userFamilyMap != null) {
				User userFamilyHead = userRepository.findById(userFamilyMap.getFamilyHeadId()).orElse(null);
				if(userFamilyHead != null) {
					return userFamilyHead;
				}
			}
		}

		return null;
	}

	public List<UserBean> getLevelOneUsers(UserBean userBean) {
		return Collections.emptyList();
	}

	public List<UserBean> getLevelMinusOneUsers(UserBean userBean) {
		return Collections.emptyList();
	}

	public List<UserBean> getLevelTwoUsers(UserBean userBean) {
		return Collections.emptyList();
	}

	public List<UserBean> getLevelMinusTwoUsers(UserBean userBean) {
		return Collections.emptyList();
	}	

}
