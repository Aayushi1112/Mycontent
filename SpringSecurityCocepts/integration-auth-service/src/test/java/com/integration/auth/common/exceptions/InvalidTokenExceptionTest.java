package com.integration.auth.common.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.integration.auth.common.errors.Errors;

class InvalidTokenExceptionTest {

	@Test
	void testConstructorWithException() {
		Exception ex = new Exception("Some exception message");
		InvalidTokenException exception = new InvalidTokenException(ex);

		assertNotNull(exception);
		assertEquals(ex, exception.getCause());
		// assertNull(exception.getErrors());
	}

	@Test
	void testConstructorWithErrors() {
		Errors errors = new Errors("Target", "Message", 123);
		InvalidTokenException exception = new InvalidTokenException(errors);

		assertNotNull(exception);
		assertEquals(errors, exception.getErrors());
	}

	@Test
	void testConstructorWithExceptionAndErrors() {
		Exception ex = new Exception("Some exception message");
		Errors errors = new Errors("Target", "Message", 123);
		InvalidTokenException exception = new InvalidTokenException(ex, errors);

		assertNotNull(exception);
		assertEquals(errors, exception.getErrors());
		assertEquals(ex, exception.getCause());
	}

}
