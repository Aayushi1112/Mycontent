package com.integration.auth.common.errors;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collections;
import org.junit.jupiter.api.Test;

class ErrorsTest {

	@Test
	void testErrorsConstructorAndGetters() {
		String target = "Target";
		String message = "ErrorMessage";
		int code = 123;

		Errors error = new Errors(target, message, code);

		assertNotNull(error);
		assertEquals(target, error.getTarget());
		assertEquals(message, error.getMessage());
		assertEquals(code, error.getCode());
		assertNotNull(error.getTimestamp());

	}

	@Test
	void testSetDetailsAndGetDetails() {
		Errors error = new Errors("Target", "Message", 123);
		ErrorDetail errorDetail = new ErrorDetail("Detail1", "abc", 0);

		// Test setting and getting details
		error.setDetails(Collections.singletonList(errorDetail));
		assertEquals(1, error.getDetails().size());
	}

	@Test
	void testEqualsAndHashCode() {
		Errors error1 = new Errors("Target", "Message", 123);
		Errors error2 = new Errors("Target", "Message", 123);
		Errors error3 = new Errors("AnotherTarget", "DifferentMessage", 456);

		// Test equals method
		assertEquals(error1, error2);
		assertNotEquals(error1, error3);
		// Test hashCode method
		assertEquals(error1.hashCode(), error2.hashCode());
		assertNotEquals(error1.hashCode(), error3.hashCode());
	}

	@Test
	void testToString() {
		Errors error = new Errors("Target", "Message", 123);
		ErrorDetail errorDetail = new ErrorDetail("Detail1", "abc", 0);
		error.setDetails(Collections.singletonList(errorDetail));

		String expectedToString = "Errors{message='Message', code=123, target='Target', timestamp='"
				+ error.getTimestamp() + "', details=[ErrorDetail{code=0, target='Detail1', message='abc'}]}";

		assertEquals(expectedToString, error.toString());
	}

	@Test
	void testEqualsMethod() {
		Errors error1 = new Errors("Target1", "Message1", 123);
		Errors error2 = new Errors("Target2", "Message2", 123);
		Errors error3 = new Errors("Target3", "Message3", 456);
		assertEquals(error1, error2);
		assertNotEquals(error1, error3);
	}

}
