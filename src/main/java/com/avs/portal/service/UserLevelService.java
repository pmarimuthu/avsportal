package com.avs.portal.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
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
			return null;
		
		return getGrandparentUsers(user).stream().map(User::toBean).collect(Collectors.toList());
	}
	
	public List<User> getGrandparentUsers(User user) {
		if(user == null || user.getId() == null)
			return Collections.emptyList();
		
		String csvGrandparentsId = userRepository.fnGetGrandparents(user.getId());
		if(csvGrandparentsId == null || csvGrandparentsId.trim().length() == 0)
			return Collections.emptyList();
		
		List<UUID> grandparentsIds = new ArrayList<>();		
		for (String strGrandparentId : Arrays.asList(csvGrandparentsId.split(","))) {
			try {
				grandparentsIds.add(UUID.fromString(strGrandparentId.trim()));
			} catch (Exception e) {
				System.err.println("getGrandparentUsers > " + user.getEmail() + " > " + e.getMessage());
			}
		}
		
		if(grandparentsIds.size() == 0)
			return Collections.emptyList();
		
		return userRepository.findAllByIdIn(grandparentsIds);
	}

	public List<UserBean> getParents(UserBean userBean) {
		if(userBean == null || userBean.getId() == null)
			return Collections.emptyList();

		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null)
			return null;

		return getParentUsers(user).stream().map(User::toBean).collect(Collectors.toList());
	}
	
	public List<User> getParentUsers(User user) {
		if(user == null || user.getId() == null)
			return Collections.emptyList();
		
		String csvParentsId = userRepository.fnGetParents(user.getId());
		if(csvParentsId == null || csvParentsId.trim().length() == 0)
			return Collections.emptyList();
		
		List<UUID> parentIds = new ArrayList<>();		
		for (String strParentId : Arrays.asList(csvParentsId.split(","))) {
			try {
				parentIds.add(UUID.fromString(strParentId.trim()));				
			} catch (Exception e) {
				System.err.println("getParentUsers > " + user.getEmail() + " > " + e.getMessage());
			}			
		}
		
		if(parentIds.size() == 0)
			return Collections.emptyList();
		
		return userRepository.findAllByIdIn(parentIds);
	}

	public List<UserBean> getFamily(UserBean userBean) {
		if(userBean == null || userBean.getId() == null)
			return null;

		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null)
			return null;

		return getFamilyUsers(user).stream().map(User::toBean).collect(Collectors.toList());

	}

	public List<User> getFamilyUsers(User user) {
		if(user == null || user.getId() == null)
			return Collections.emptyList();

		List<User> familyUsers = new ArrayList<>();
		familyUsers.add(user);

		List<UUID> familyUsersId = new ArrayList<>();
		
		String csvFamilyIds = userRepository.fnGetFamily(user.getId());
		System.out.println("csv-family-ids: [" + csvFamilyIds.length() +"] " + csvFamilyIds);
		
		if(csvFamilyIds == null || csvFamilyIds.trim().length() == 0)
			return familyUsers;
		
		for (String familyUserId : Arrays.asList(csvFamilyIds.split(","))) {
			try {
				familyUsersId.add(UUID.fromString(familyUserId.trim()));
			} catch (Exception e) {
				System.err.println("getFamilyUsers > " + user.getEmail() + " > " + e.getMessage());
			}
		}
		
		if(familyUsersId.size() == 0)
			return familyUsers;
		
		familyUsers.addAll(userRepository.findAllByIdIn(familyUsersId));
		
		return familyUsers;
		
	}

	public List<UserBean> getChildren(UserBean userBean) {
		if(userBean == null || userBean.getId() == null)
			return null;
		
		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null)
			return null;

		return getChildrenUsers(user).stream().map(User::toBean).collect(Collectors.toList());
	}
	
	public List<User> getChildrenUsers(User user) {
		if(user == null || user.getId() == null)
			return Collections.emptyList();
		
		String csvChildrenId  = userRepository.fnGetChildren(user.getId());
		if(csvChildrenId == null || csvChildrenId.trim().length() == 0)
			return Collections.emptyList();
		
		List<UUID> childrenId = new ArrayList<>();		
		for (String strId : Arrays.asList(csvChildrenId.split(","))) {
			try {
				childrenId.add(UUID.fromString(strId.trim()));				
			} catch (Exception e) {
				System.err.println("getChildrenUsers > " + user.getEmail() + " > " + e.getMessage());
			}			
		}
		
		if(childrenId.size() == 0)
			return Collections.emptyList();		
		
		return userRepository.findAllByIdIn(childrenId);
	}

	public List<UserBean> getGrandchildren(UserBean userBean) {
		if(userBean == null || userBean.getId() == null)
			return null;
		
		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null)
			return null;
		
		return getGrandchildrenUsers(user).stream().map(User::toBean).collect(Collectors.toList());
	}
	
	public Collection<User> getGrandchildrenUsers(User user) {
		if(user == null || user.getId() == null)
			return Collections.emptyList();

		String csvGrandchildrenId = userRepository.fnGetGrandchildren(user.getId());
		if(csvGrandchildrenId == null || csvGrandchildrenId.trim().length() == 0)
			return Collections.emptyList();
		
		List<UUID> grandchildrenId = new ArrayList<>();		
		for (String strId : Arrays.asList(csvGrandchildrenId.split(","))) {
			try {
				grandchildrenId.add(UUID.fromString(strId.trim()));				
			} catch (Exception e) {
				System.err.println("getGrandchildrenUsers > " + user.getEmail() + " > " + e.getMessage());
			}			
		}
		
		if(grandchildrenId.size() == 0)
			return Collections.emptyList();		
		
		return userRepository.findAllByIdIn(grandchildrenId);
	}

}
