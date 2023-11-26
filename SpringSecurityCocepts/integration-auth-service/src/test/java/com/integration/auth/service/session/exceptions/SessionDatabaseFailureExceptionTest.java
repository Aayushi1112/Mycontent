package com.integration.auth.service.session.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.integration.auth.common.errors.Errors;

class SessionDatabaseFailureExceptionTest {

	@Test
	void testConstructorWithException() {
		Exception ex = new Exception("Some exception message");
		SessionDatabaseFailureException exception = new SessionDatabaseFailureException(ex);

		assertNotNull(exception);
		assertEquals(ex, exception.getCause());
	}

	@Test
	void testConstructorWithExceptionAndErrors() {
		Exception ex = new Exception("Some exception message");
		Errors errors = new Errors("Target", "Message", 123);
		SessionDatabaseFailureException exception = new SessionDatabaseFailureException(ex, errors);

		assertNotNull(exception);
		assertEquals(errors, exception.getErrors());
		assertEquals(ex, exception.getCause());
	}

}
