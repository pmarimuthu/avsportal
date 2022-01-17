package com.avs.portal.bean;

import java.io.Serializable;

public class LoginBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 4858178880110583705L;
	
	private String loginId;
	
	private String password;

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
