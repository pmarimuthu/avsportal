package com.avs.portal.bean;

import java.util.UUID;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

public class UserBeanParameterResolver implements ParameterResolver {

	@Override
	public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException {

		return parameterContext.getParameter().getType() == UserBean.class;
	}

	@Override
	public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException {

		return new UserBean()
				.setId(UUID.randomUUID())
				.setEmail("random-email")
				.setPhone(Long.valueOf(1234567890))
				;
	}

}
