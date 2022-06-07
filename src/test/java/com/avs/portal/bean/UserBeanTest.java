package com.avs.portal.bean;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assumptions.assumingThat;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@ExtendWith(UserBeanParameterResolver.class)
public class UserBeanTest {

	@ParameterizedTest
	@CsvSource({"abc@xyz.com, 1234567890"})
	@Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
	public void testUserBean(String email, String phone, UserBean userBean) {
		userBean.setEmail(email)
			.setPhone(Long.parseLong(phone));
		
		assumingThat(userBean != null, () -> {
			assertEquals(email, userBean.getEmail());
			assertEquals(Long.valueOf(phone), userBean.getPhone());
			assertNotNull(UUID.fromString(userBean.getId().toString()));
		});
	}
	
	@ParameterizedTest
	@CsvSource({"abc@xyz.com, 1234567890"})
	public void testUserBeanTimeout(String email, String phone, UserBean userBean) {
		userBean.setEmail(email)
			.setPhone(Long.parseLong(phone));
		
		assumingThat(userBean != null, () -> {

			assertEquals(email, userBean.getEmail());
			assertEquals(Long.valueOf(phone), userBean.getPhone());
			assertNotNull(UUID.fromString(userBean.getId().toString()));
		});
		
		assertTimeout(Duration.ofMillis(500), () -> {
			assertEquals(email, userBean.getEmail());
		});
	}

}
