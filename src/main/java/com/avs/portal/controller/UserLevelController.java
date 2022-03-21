package com.avs.portal.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avs.portal.bean.UserBean;
import com.avs.portal.service.UserLevelService;

@RestController
@RequestMapping(path = "/api/user-level")
public class UserLevelController {
	
	private static final int LEVEL_ZERO = 0;
	private static final int LEVEL_ONE = 1;
	private static final int LEVEL_TWO = 2;
	private static final int LEVEL_MINUSONE = -1;
	private static final int LEVEL_MINUSTWO = -2;
	
	@Autowired
	private UserLevelService userLevelService;

	@GetMapping("/health")
	public String sayHello() {
		return "UserLevel is Alive!!";
	}
	
	@PostMapping("/{level}")
	public List<UserBean> getLevelUsers(@PathVariable("level") int level, @RequestBody UserBean userBean) {
		if(userBean ==  null || userBean.getId() == null)
			return null;

		List<UserBean> usersAtTheLevel = new ArrayList<>();
		
		switch (level) {

		case LEVEL_MINUSTWO: // Grandparents
			usersAtTheLevel = userLevelService.getGrandparents(userBean); // getLevelMinusTwoUsers
			break;

		case LEVEL_MINUSONE: // Parents
			usersAtTheLevel = userLevelService.getParents(userBean); // getLevelMinusOneUsers(userBean);
			break;
			
		case LEVEL_ZERO: // Self
			usersAtTheLevel = userLevelService.getUserFamilyMembers(userBean); // getLevelZeroUsers
			break;

		case LEVEL_ONE: // Children
			usersAtTheLevel = userLevelService.getChildren(userBean); // getLevelOneUsers;
			break;

		case LEVEL_TWO: // Grandchildren
			usersAtTheLevel = userLevelService.getGrandchildrenBeans(userBean); // getLevelTwoUsers
			break;

		default:
			break;
		}
		
		return usersAtTheLevel;
	}
}
