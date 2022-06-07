package com.avs.portal.bean;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@ExtendWith(LoginBeanParameterResolve.class)
class LoginBeanDITest {

	@RepeatedTest(10)
	@DisplayName("UT | LoginBeanDITest | Valid")
	public void testLoginBeanDITestValid(LoginBean loginBean, RepetitionInfo repetitionInfo) {
		System.out.println(repetitionInfo.getCurrentRepetition() + " ## " + loginBean.toString());
		
		assumingThat(loginBean != null, () -> {
			assertTrue(loginBean.getPassword().length() > 0);
			assertTrue(!loginBean.getHasError());
			assertTrue(loginBean.getCustomErrorMessages().size() == 0);
		});
	}
	
	@ParameterizedTest
	@ValueSource(strings = { "100", "200", "300", "400" })
	@DisplayName("UT | LoginBeanDITest | Valid | Prameterized IDs")
	public void testLoginBeanDITestValidParameterized(String loginId, LoginBean loginBean) {
		loginBean.setLoginId(loginId);
		System.out.println(loginBean.toString());
		
		assumingThat(loginBean != null, () -> {
			assertTrue(loginBean.getPassword().length() > 0);
			assertTrue(!loginBean.getHasError());
			assertTrue(loginBean.getCustomErrorMessages().size() == 0);
		});
	}

}
