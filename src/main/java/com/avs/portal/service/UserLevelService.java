package com.avs.portal.service;

import java.util.ArrayList;
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

	public List<UserBean> getGrandparentsBeans(UserBean userBean) {
		if(userBean == null || userBean.getId() == null)
			return Collections.emptyList();

		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null)
			return null;

		return getLevelMinusTwoUsers(user, null).stream().map(User::toBean).collect(Collectors.toList());
	}

	public List<UserBean> getParentsBeans(UserBean userBean) {
		if(userBean == null || userBean.getId() == null)
			return Collections.emptyList();

		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null)
			return null;

		return getLevelMinusOneUsers(user).stream().map(User::toBean).collect(Collectors.toList());
	}

	public List<UserBean> getUserFamilyBeans(UserBean userBean) {
		if(userBean == null || userBean.getId() == null)
			return null;

		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null)
			return null;

		return getLevelZeroUsers(user).stream().map(User::toBean).collect(Collectors.toList());

	}

	public List<UserBean> getChildrenBeans(UserBean userBean) {
		if(userBean == null || userBean.getId() == null)
			return null;

		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null)
			return null;

		//return getChildren(user, null).stream().map(User::toBean).collect(Collectors.toList());
		return getLevelOneUsers(user).stream().map(User::toBean).collect(Collectors.toList());
	}

	public List<UserBean> getGrandchildrenBeans(UserBean userBean) {
		if(userBean == null || userBean.getId() == null)
			return null;

		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null)
			return null;

		return getLevelTwoUsers(user, null).stream().map(User::toBean).collect(Collectors.toList());
	}
	
	// Grand parents
	public List<User> getLevelMinusTwoUsers(User user, List<User> users) {
		if(user == null || user.getId() == null)
			return Collections.emptyList();

		List<User> gparents = new ArrayList<>();
		
		List<User> parents = getLevelMinusOneUsers(user);
		
		for (User parent : parents) {
			List<User> grandparents = getLevelMinusOneUsers(parent);
			for (User gparent : grandparents) {
				gparents.add(gparent);
			}
		}

		return gparents;
	}

	// Parents
	public List<User> getLevelMinusOneUsers(User user) {
		if(user == null || user.getId() == null)
			return Collections.emptyList();

		if(user.getUserProfile() == null || user.getUserProfile().getMaritalStatus() == null)
			return Collections.emptyList();

		List<User> parents = new ArrayList<>();
		UUID parentId = userRepository.fnGetParent(user.getId());
		if(parentId == null)
			return parents;


		User head = userRepository.findById(parentId).orElse(null);
		if(head != null) {
			parents.add(head);
			User spouse = fnGetSpouse(head);
			if(spouse != null)
				parents.add(spouse);
		}

		return parents;
	}

	// Family
	public List<User> getLevelZeroUsers(User user) {
		if(user == null || user.getId() == null)
			return Collections.emptyList();

		List<User> levelZeroUsers = new ArrayList<>();
		levelZeroUsers.add(user);

		User spouseUser = fnGetSpouse(user);
		if(spouseUser != null)
			levelZeroUsers.add(spouseUser);

		return levelZeroUsers;
	}

	// Children
	public List<User> getLevelOneUsers(User user) {
		if(user == null || user.getId() == null || !userRepository.existsById(user.getId()))
			return Collections.emptyList();

		List<User> children = new ArrayList<>();

		List<User> users = userRepository.findAll();

		for (User member : users) {
			List<User> parents = getLevelMinusOneUsers(member);
			for (User parent : parents) {
				if(parent.getId().equals(user.getId()))
					children.add(member);
			}
		}

		return children;
	}

	// Grand Children
	public List<User> getLevelTwoUsers(User user, List<User> users) {
		if(user == null || user.getId() == null || !userRepository.existsById(user.getId()))
			return Collections.emptyList();

		List<User> grandchildren = new ArrayList<>();

		if(users == null)
			users = userRepository.findAll();

		for (User member : users) {
			List<User> parents = getLevelMinusOneUsers(member);
			for (User parent : parents) {
				List<User> grandparents = getLevelMinusOneUsers(parent);
				for (User grandparent : grandparents) {
					if(grandparent.getId().equals(user.getId()))
						grandchildren.add(member);
				}
			}
		}

		return grandchildren;
	}

	public List<User> getChildren(User user, List<User> users) {
		if(user == null || user.getId() == null || !userRepository.existsById(user.getId()))
			return Collections.emptyList();

		List<User> children = new ArrayList<>();


		if(users == null)		
			users = userRepository.findAll();

		for (User member : users) {
			List<User> parents = getLevelMinusOneUsers(member);
			for (User parent : parents) {
				if(parent.getId().equals(user.getId()))
					children.add(member);
			}
		}

		return children;
	}
	
	private User fnGetSpouse(User user) {
		/*
		if(user == null || user.getId() == null)
			return null;
		*/
		UUID spouseId = userRepository.fnGetSpouse(user.getId());
		if(spouseId == null)
			return null;
		
		return userRepository.findById(spouseId).orElse(null);
		
	}


}
