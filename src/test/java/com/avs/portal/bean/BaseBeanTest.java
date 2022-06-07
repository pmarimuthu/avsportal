package com.avs.portal.bean;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

class BaseBeanTest {

	@Test
	void testCustomErrorMessages() {
		BaseBean bean = new BaseBean();		
		bean.setHasError(true);
		
		if(bean.getHasError()) {
			assertTrue(bean.getHasError());			
			List<String> errors = new ArrayList<>();
			errors.add("Error-One");
			errors.add("Error-Two");
			
			bean.setCustomErrorMessages(errors);			
			assertEquals(errors.size(), bean.getCustomErrorMessages().size());
		}
		else {
			assertFalse(bean.getHasError());
			bean.setCustomErrorMessages(Collections.emptyList());			
			assertEquals(0, bean.getCustomErrorMessages().size());
		}
		
		Exception expt = new Exception("Sample-Exception");
		bean.setThrowable(expt);
		assertEquals(expt, bean.getThrowable());
		bean.getThrowable();
		
		assertNotNull(bean.toString());
	}

}
