package com.integration.auth.service.token.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.integration.auth.common.errors.Errors;

class JWTClaimParserExceptionTest {

	@Test
	void testConstructorWithErrors() {
		Errors errors = new Errors("Target", "Message", 123);
		JWTClaimParserException exception = new JWTClaimParserException(errors);

		assertNotNull(exception);
		assertEquals(errors, exception.getErrors());
	}

	@Test
	void testConstructorWithExceptionAndErrors() {
		Exception ex = new Exception("Some exception message");
		Errors errors = new Errors("Target", "Message", 123);
		JWTClaimParserException exception = new JWTClaimParserException(ex, errors);

		assertNotNull(exception);
		assertEquals(errors, exception.getErrors());
		assertEquals(ex, exception.getCause());
	}

}
