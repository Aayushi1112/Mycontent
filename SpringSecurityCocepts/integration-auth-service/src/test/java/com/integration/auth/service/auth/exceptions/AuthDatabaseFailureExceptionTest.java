package com.integration.auth.service.auth.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.integration.auth.common.errors.Errors;

class AuthDatabaseFailureExceptionTest {

	@Test
	void testConstructorWithException() {
		Exception ex = new Exception("Some exception message");
		AuthDatabaseFailureException exception = new AuthDatabaseFailureException(ex);

		assertNotNull(exception);
		assertEquals(ex, exception.getCause());

	}

	@Test
	void testConstructorWithExceptionAndErrors() {
		Exception ex = new Exception("Some exception message");
		Errors errors = new Errors("Target", "Message", 123);
		AuthDatabaseFailureException exception = new AuthDatabaseFailureException(ex, errors);

		assertNotNull(exception);
		assertEquals(errors, exception.getErrors());
		assertEquals(ex, exception.getCause());
	}

}
