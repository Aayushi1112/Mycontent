package com.integration.auth.common.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.integration.auth.common.errors.Errors;

class ValidationFailureExceptionTest {

	@Test
	void testConstructor() {
		String message = "Some error message";
		Errors errors = new Errors("Target", "Message", 123);

		ValidationFailureException exception = new ValidationFailureException(message, errors);

		assertNotNull(exception);
		assertEquals(message, exception.getMessage());
		assertEquals(errors, exception.getErrors());
	}

	@Test
	void testGetInstance() {
		Errors errors = new Errors("Target", "Message", 123);

		ValidationFailureException exception = ValidationFailureException.getInstance(errors);

		assertNotNull(exception);
		assertEquals("validation failure", exception.getMessage());
		assertEquals(errors, exception.getErrors());
	}

}
