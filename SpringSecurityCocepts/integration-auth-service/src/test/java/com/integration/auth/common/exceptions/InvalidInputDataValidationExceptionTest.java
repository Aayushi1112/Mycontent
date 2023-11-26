package com.integration.auth.common.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.integration.auth.common.errors.Errors;

class InvalidInputDataValidationExceptionTest {

	@Test
	void testConstructorWithErrors() {
		Errors errors = new Errors("Target", "Message", 123);
		InvalidInputDataValidationException exception = new InvalidInputDataValidationException(errors);

		assertNotNull(exception);
		assertEquals(errors, exception.getErrors());
	}

	@Test
	void testConstructorWithExceptionAndErrors() {
		Exception ex = new Exception("Some exception message");
		Errors validationError = new Errors("ValidationTarget", "ValidationMessage", 456);
		InvalidInputDataValidationException exception = new InvalidInputDataValidationException(ex, validationError);

		assertNotNull(exception);
		assertEquals(validationError, exception.getErrors());
		assertEquals(ex, exception.getCause());
	}

}
