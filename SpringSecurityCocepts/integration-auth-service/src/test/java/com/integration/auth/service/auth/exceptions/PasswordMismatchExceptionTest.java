package com.integration.auth.service.auth.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.integration.auth.common.errors.Errors;

class PasswordMismatchExceptionTest {

	@Test
	void testConstructorWithErrors() {
		Errors errors = new Errors("Target", "Message", 123);
		PasswordMismatchException exception = new PasswordMismatchException(errors);

		assertNotNull(exception);
		assertEquals(errors, exception.getErrors());
	}

}
