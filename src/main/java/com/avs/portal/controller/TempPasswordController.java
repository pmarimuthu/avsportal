package com.avs.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avs.portal.bean.TempPasswordBean;
import com.avs.portal.bean.UserBean;
import com.avs.portal.service.TempPasswordService;

@RestController
@RequestMapping(path = "/api/temp-password")
public class TempPasswordController {

	@Autowired
	private TempPasswordService tempPasswordService;
	
	@GetMapping("/health")
	public String sayHello() {
		return "TempPasswordController is Alive!!";
	}
	
	@PostMapping("/list")
	public List<TempPasswordBean> listAllTempPasswords() {
		return tempPasswordService.getAllUsersTempPasswords();
	}
	
	@PostMapping("/create")
	public TempPasswordBean createTempPassword(@RequestBody UserBean userBean) {
		return tempPasswordService.createTempPassword(userBean);
	}
	
	@DeleteMapping("/delete")
	public UserBean deleteTempPassword(@RequestBody UserBean userBean) {
		return tempPasswordService.deleteTempPassword(userBean);
	}
	
}
