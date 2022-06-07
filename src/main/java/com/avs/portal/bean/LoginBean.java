package com.avs.portal.bean;

import java.io.Serializable;

public class LoginBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 4858178880110583705L;
	
	private String loginId;
	
	private String password;

	public String getLoginId() {
		return loginId;
	}

	public LoginBean setLoginId(String loginId) {
		this.loginId = loginId;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public LoginBean setPassword(String password) {
		this.password = password;
		return this;
	}

	@Override
	public String toString() {
		return "LoginBean [loginId=" + loginId + ", password=" + password + 
				// ", BaseBean=" + super.toString() + 
				"]";
	}

	

}
