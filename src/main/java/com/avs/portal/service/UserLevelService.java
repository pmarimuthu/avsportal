package com.avs.portal.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avs.portal.bean.UserBean;
import com.avs.portal.entity.User;
import com.avs.portal.repository.UserRepository;


@Service
public class UserLevelService {

	@Autowired
	private UserRepository userRepository;

	public List<UserBean> getGrandparents(UserBean userBean) {
		if(userBean == null || userBean.getId() == null)
			return Collections.emptyList();

		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null)
			return Collections.emptyList();
		
		return getGrandparentUsers(user).stream().map(User::toBean).collect(Collectors.toList());
	}
	
	public List<User> getGrandparentUsers(User user) {
		if(user == null || user.getId() == null)
			return Collections.emptyList();
		
		List<User> grandparents = new ArrayList<>();
		for (User parent : getParentUsers(user)) {
			grandparents.addAll(getParentUsers(parent));
		}
		
		return grandparents;
	}

	public List<UserBean> getParents(UserBean userBean) {
		if(userBean == null || userBean.getId() == null)
			return Collections.emptyList();

		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null)
			return Collections.emptyList();

		return getParentUsers(user).stream().map(User::toBean).collect(Collectors.toList());
	}
	
	public List<User> getParentUsers(User user) {
		if(user == null || user.getId() == null)
			return Collections.emptyList();
		
		String csvParentsId = userRepository.fnGetParents(user.getId());
		if(csvParentsId == null || csvParentsId.trim().length() == 0)
			return Collections.emptyList();
		
		List<UUID> parentIds = new ArrayList<>();
		StringTokenizer idTokens = new StringTokenizer(csvParentsId, ",");
		while (idTokens.hasMoreTokens()) {
			String parentIdToken = idTokens.nextToken();
			if(parentIdToken != null && !parentIdToken.trim().isEmpty())
				try {
					parentIds.add(UUID.fromString(parentIdToken));
				} catch (Exception e) {
					System.err.println("getParentUsers: " + user.getEmail() + " > " + csvParentsId + " > " + e.getMessage());
				}
		}
		
		if(parentIds.isEmpty())
			return Collections.emptyList();
		
		return userRepository.findAllByIdIn(parentIds);
	}

	public List<UserBean> getMyFamily(UserBean userBean) {
		if(userBean == null || userBean.getId() == null)
			return Collections.emptyList();

		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null)
			return Collections.emptyList();

		return getMyFamilyUsers(user).stream().map(User::toBean).collect(Collectors.toList());

	}

	public List<User> getMyFamilyUsers(User user) {
		if(user == null || user.getId() == null)
			return Collections.emptyList();

		List<User> familyUsers = new ArrayList<>();
		familyUsers.add(user);

		List<UUID> familyUsersId = new ArrayList<>();		
		String csvFamilyIds = userRepository.fnGetMyFamily(user.getId());
		
		if(csvFamilyIds == null || csvFamilyIds.trim().length() == 0)
			return familyUsers;
		
		StringTokenizer familyIdTokens = new StringTokenizer(csvFamilyIds, ",");
		while(familyIdTokens.hasMoreTokens()) {
			String familyIdToken = familyIdTokens.nextToken();
			if(familyIdToken != null && !familyIdToken.trim().isEmpty()) {
				try {
					familyUsersId.add(UUID.fromString(familyIdToken));
				} catch (Exception e) {
					System.err.println("getMyFamilyUsers: " + user.getEmail() + " > " + csvFamilyIds + " > " + e.getMessage());
				}
			}
		}
		
		if(familyUsersId.isEmpty())
			return familyUsers;
		
		familyUsers.addAll(userRepository.findAllByIdIn(familyUsersId));
		
		return familyUsers;
		
	}

	public List<UserBean> getChildren(UserBean userBean) {
		if(userBean == null || userBean.getId() == null)
			return Collections.emptyList();
		
		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null)
			return Collections.emptyList();

		return getChildrenUsers(user).stream().map(User::toBean).collect(Collectors.toList());
	}
	
	public List<User> getChildrenUsers(User user) {
		if(user == null || user.getId() == null)
			return Collections.emptyList();
		
		String csvChildrenId  = userRepository.fnGetChildren(user.getId());
		if(csvChildrenId == null || csvChildrenId.trim().isEmpty())
			return Collections.emptyList();
		
		List<UUID> childrenId = new ArrayList<>();
		StringTokenizer childIdTokens = new StringTokenizer(csvChildrenId, ",");
		while(childIdTokens.hasMoreTokens()) {
			String childIdToken = childIdTokens.nextToken();
			if(childIdToken != null && !childIdToken.trim().isEmpty()) {
				try {
					childrenId.add(UUID.fromString(childIdToken));
				} catch (Exception e) {
					System.err.println("getChildrenUsers: " + user.getEmail() + " > " + csvChildrenId + " > " + e.getMessage());
				}
			}
		}
		
		if(childrenId.isEmpty())
			return Collections.emptyList();		
		
		return userRepository.findAllByIdIn(childrenId);
	}

	public List<UserBean> getGrandchildren(UserBean userBean) {
		if(userBean == null || userBean.getId() == null)
			return Collections.emptyList();
		
		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null)
			return Collections.emptyList();
		
		List<UserBean> grandchildren = new ArrayList<>();
		for (UserBean childBean : getChildren(userBean)) {
			grandchildren.addAll(getChildren(childBean));
		}
		
		return grandchildren;
	}

}
