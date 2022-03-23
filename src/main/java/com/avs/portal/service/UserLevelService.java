package com.avs.portal.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
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

		return getLevelMinusTwoUsers(user, null).stream().map(User::toBean).collect(Collectors.toList());
	}

	public List<UserBean> getParents(UserBean userBean) {
		if(userBean == null || userBean.getId() == null)
			return Collections.emptyList();

		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null)
			return null;

		return getLevelMinusOneUsers(user).stream().map(User::toBean).collect(Collectors.toList());
	}

	public List<UserBean> getUserFamilyMembers(UserBean userBean) {
		if(userBean == null || userBean.getId() == null)
			return null;

		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null)
			return null;

		return getLevelZeroUsers(user).stream().map(User::toBean).collect(Collectors.toList());

	}

	public List<UserBean> getChildren(UserBean userBean) {
		if(userBean == null || userBean.getId() == null)
			return null;
		
		String csvChildrenId  = userRepository.fnGetChildren(userBean.getId());
		if(csvChildrenId == null || csvChildrenId.trim().length() == 0)
			return Collections.emptyList();
		
		List<UUID> childrenId = new ArrayList<>();
		
		for (String strId : Arrays.asList(csvChildrenId.split(", "))) {
			try {
				childrenId.add(UUID.fromString(strId.trim()));
				
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}			
		}
		
		if(childrenId.size() == 0)
			return Collections.emptyList();
		
		
		List<User> children = userRepository.findAllByIdIn(childrenId);
		return children.stream().map(User::toBean).collect(Collectors.toList());
	}

	public List<UserBean> getGrandchildrenBeans(UserBean userBean) {
		if(userBean == null || userBean.getId() == null)
			return null;
		
		String csvChildrenId  = userRepository.fnGetChildren(userBean.getId());
		if(csvChildrenId == null || csvChildrenId.trim().length() == 0)
			return Collections.emptyList();
		
		List<UUID> childrenId = new ArrayList<>();
		
		for (String strId : Arrays.asList(csvChildrenId.split(", "))) {
			try {
				childrenId.add(UUID.fromString(strId.trim()));				
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
				for (String strId : Arrays.asList(csvGrandchildrenId.split(", "))) {
					try {
						grandchildrenId.add(UUID.fromString(strId.trim()));
					} catch (Exception e) {
						System.err.println(e.getMessage());
					}			
				}
			}
				
		}
		
		if(grandchildrenId.size() == 0)
			return Collections.emptyList();
		
		List<User> grandchildren = userRepository.findAllByIdIn(grandchildrenId);
		return grandchildren.stream().map(User::toBean).collect(Collectors.toList());
	}
	
	// -------------------------------------------------------------------------------------------------------------------------------------------
	// Grand parents
	private List<User> getLevelMinusTwoUsers(User user, List<User> users) {
		if(user == null || user.getId() == null)
			return Collections.emptyList();

		if(user.getUserProfile() == null || user.getUserProfile().getMaritalStatus() == null)
			return Collections.emptyList();

		List<UUID> listGrandparentId = new ArrayList<>();

		String csvParentsId = userRepository.fnGetParents(user.getId());
		if(csvParentsId.trim().length() > 0) {
			List<UUID> parentsId = Arrays.asList(csvParentsId.split(",")).stream().map(parentId -> {
				return UUID.fromString(parentId.trim());
			}).collect(Collectors.toList());
			
			for (UUID parentId : parentsId) {
				String csvGrandparentsId = userRepository.fnGetParents(parentId);
				if(csvGrandparentsId.trim().length() > 0) {
					System.err.println("[" + csvGrandparentsId + "]");
					listGrandparentId.addAll(
							Arrays.asList(csvGrandparentsId.split(",")).stream().map(grandparentId -> {
								return UUID.fromString(grandparentId.trim());
							})
							.collect(Collectors.toList()));
				}
			}
		}

		return userRepository.findAllByIdIn(listGrandparentId);
	}

	// Parents
	private List<User> getLevelMinusOneUsers(User user) {
		if(user == null || user.getId() == null)
			return Collections.emptyList();

		if(user.getUserProfile() == null || user.getUserProfile().getMaritalStatus() == null)
			return Collections.emptyList();
		
		String csvParentsId = userRepository.fnGetParents(user.getId());
		List<UUID> parentsId = Arrays.asList(csvParentsId.split(",")).stream().map(parentId -> {
			return UUID.fromString(parentId.trim());
		}).collect(Collectors.toList());
		
		return userRepository.findAllByIdIn(parentsId);
		
		/*
		List<User> parents = new ArrayList<>();
		
		UUID parentId = userRepository.fnGetParent(user.getId());
		if(parentId == null)
			return parents;
		
		User head = userRepository.findById(parentId).orElse(null);
		if(head != null) {
			parents.add(head);
			UUID spouseId = userRepository.fnGetSpouse(parentId);
			if(spouseId != null) {
				User spouse = userRepository.findById(spouseId).orElse(null);
				if(spouse != null)
					parents.add(spouse);
			}
		}
		*/
	}

	// Family
	private List<User> getLevelZeroUsers(User user) {
		if(user == null || user.getId() == null)
			return Collections.emptyList();

		List<User> levelZeroUsers = new ArrayList<>();
		levelZeroUsers.add(user);

		UUID spouseId = userRepository.fnGetSpouse(user.getId());
		if(spouseId != null) {
			User spouse = userRepository.findById(spouseId).orElse(null);
			if(spouse != null)
				levelZeroUsers.add(spouse);
		}

		return levelZeroUsers;
	}

	// Children
	private List<User> getLevelOneUsers(User user) {		
		if(user == null || user.getId() == null)
			return Collections.emptyList();

		if(user.getUserProfile() == null || user.getUserProfile().getMaritalStatus() == null)
			return Collections.emptyList();

		List<User> children = new ArrayList<>();

		List<String> listUserId = userRepository.findNativeAllId();
		listUserId.remove(user.getId().toString());

		
		for (String id : listUserId) {
			UUID userId = UUID.fromString(id);
			UUID headId = null, spouseId = null;
			
			headId = userRepository.fnGetParent(userId);
			spouseId = userRepository.fnGetSpouse(headId);
			
			if(user.getId().equals(headId) || user.getId().equals(spouseId)) {
				User child = userRepository.findById(userId).orElse(null);
				if(child != null)
					children.add(child);
			}
		}

		return children;
	}

	// Grand Children
	private List<User> getLevelTwoUsers(User user) {
		List<User> children = getLevelOneUsers(user);
		
		List<User> grandchildren = new ArrayList<>();
		for (User child : children) {
			grandchildren.addAll(getLevelOneUsers(child));
		}
		
		return grandchildren;
	}


}
