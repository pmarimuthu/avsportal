package com.avs.portal.service;

import java.util.ArrayList;
import java.util.Arrays;
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
		
		List<User> grandparents = new ArrayList<>();
		
		List<User> parents = getParentUsers(user);
		for (User parent : parents) {
			grandparents.addAll(getParentUsers(parent));
		}
		
		return grandparents;
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
				System.err.println(e.getMessage());
			}			
		}
		
		if(parentIds.size() == 0)
			return Collections.emptyList();
		
		
		return userRepository.findAllByIdIn(parentIds);
	}

	public List<UserBean> getFamilyMembers(UserBean userBean) {
		if(userBean == null || userBean.getId() == null)
			return null;

		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null)
			return null;

		return getFamilyHeadAndSpouseUsers(user).stream().map(User::toBean).collect(Collectors.toList());

	}

	public List<User> getFamilyHeadAndSpouseUsers(User user) {
		if(user == null || user.getId() == null)
			return Collections.emptyList();

		List<User> familyHeadAndSpouseUsers = new ArrayList<>();
		familyHeadAndSpouseUsers.add(user);

		String csvSpouseIds = userRepository.fnGetSpouse(user.getId());
		System.out.println("csv-spouse-ids: " + csvSpouseIds);
		
		if(csvSpouseIds == null || csvSpouseIds.trim().length() == 0)
			return familyHeadAndSpouseUsers;
		
		List<UUID> spouseIds = new ArrayList<>();
		for (String strSpouseId : Arrays.asList(csvSpouseIds.split(","))) {
			try {
				spouseIds.add(UUID.fromString(strSpouseId));
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
		
		if(spouseIds.size() == 0)
			return familyHeadAndSpouseUsers;
		
		familyHeadAndSpouseUsers.addAll(userRepository.findAllByIdIn(spouseIds));
		
		return familyHeadAndSpouseUsers;
		
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
				System.err.println(e.getMessage());
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
	
	public List<User> getGrandchildrenUsers(User user) {
		if(user == null || user.getId() == null)
			return Collections.emptyList();
		
		String csvChildrenId  = userRepository.fnGetChildren(user.getId());
		if(csvChildrenId == null || csvChildrenId.trim().length() == 0)
			return Collections.emptyList();
		
		List<UUID> childrenId = new ArrayList<>();
		
		for (String strChildId : Arrays.asList(csvChildrenId.split(", "))) {
			try {
				childrenId.add(UUID.fromString(strChildId.trim()));				
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}			
		}
		
		if(childrenId.size() == 0)
			return Collections.emptyList();
		
		// ------------------------------------------------------------------------
		
		List<UUID> grandchildrenId = new ArrayList<>();
		
		for (UUID childId : childrenId) {
			String csvGrandchildrenId = userRepository.fnGetChildren(childId);
			if(csvGrandchildrenId != null && csvGrandchildrenId.trim().length() > 0) {
				for (String strGrandchildId : Arrays.asList(csvGrandchildrenId.split(","))) {
					try {
						grandchildrenId.add(UUID.fromString(strGrandchildId.trim()));
					} catch (Exception e) {
						System.err.println(e.getMessage());
					}			
				}
			}				
		}
		
		if(grandchildrenId.size() == 0)
			return Collections.emptyList();
		
		return userRepository.findAllByIdIn(grandchildrenId);
	}


}
