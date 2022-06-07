package com.avs.portal.bean;

import java.util.UUID;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

public class LoginBeanParameterResolve implements ParameterResolver {

	@Override
	public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException {
		
		return parameterContext.getParameter().getType() == LoginBean.class;
	}

	@Override
	public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException {

		return new LoginBean()
				.setLoginId(UUID.randomUUID().toString())
				.setPassword("random-password_by_resolver")
				;
	}

}
