package com.avs.portal.bean;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginBeanTest {

	private static LoginBean loginBean = buildLoginBean();
	
	@Test
	@Order(2)
	@DisplayName("UT | LoginBean | Valid")
	public void testLoginBeanValid() {
		clearErrors();
		assumingThat(loginBean != null, () -> {
			assertTrue(loginBean.getPassword().length() > 0);
			assertTrue(!loginBean.getHasError());
			assertTrue(loginBean.getCustomErrorMessages().size() == 0);
		});
	}

	@Test
	@Order(1)
	@DisplayName("UT | LoginBean | Invalid")
	public void testLoginBeanInvalid() {
		addErrors();
		assumingThat(loginBean != null, () -> {
			assertTrue(loginBean.getPassword().length() > 0);
			assertTrue(loginBean.getHasError());
			assertTrue(loginBean.getCustomErrorMessages().size() > 0);
		});
	}

	private static LoginBean buildLoginBean() {
		return new LoginBean()
				.setLoginId(UUID.randomUUID().toString())
				.setPassword("random-password");
	}
	
	private static LoginBean addErrors() {
		if(loginBean == null)
			buildLoginBean();
		
		List<String> customErrorMessages = new ArrayList<String>();
		customErrorMessages.add("CUST_ERROR_MESSAGE_01");
		customErrorMessages.add("CUST_ERROR_MESSAGE_02");
		customErrorMessages.add("CUST_ERROR_MESSAGE_03");
		loginBean.setCustomErrorMessages(customErrorMessages);
		
		loginBean.setCustomErrorMessages(customErrorMessages);
		loginBean.setHasError(true);
		
		System.out.println(loginBean.toString());
		return loginBean;
	}

	private static LoginBean clearErrors() {
		if(loginBean == null)
			buildLoginBean();
		
		loginBean.setHasError(false);
		loginBean.setCustomErrorMessages(Collections.emptyList());
		
		System.out.println(loginBean.toString());
		return loginBean;
	}	

}
