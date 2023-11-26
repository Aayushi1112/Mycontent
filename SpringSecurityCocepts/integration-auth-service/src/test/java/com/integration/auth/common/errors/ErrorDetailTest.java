package com.integration.auth.common.errors;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ErrorDetailTest {

	@Test
	void testConstructorAndAccessors() {
		int code = 404;
		String target = "API";
		String message = "Resource not found";

		ErrorDetail errorDetail = new ErrorDetail(target, message, code);

		assertEquals(code, errorDetail.getCode());
		assertEquals(target, errorDetail.getTarget());
		assertEquals(message, errorDetail.getMessage());
	}

	@Test
	void testToString() {
		int code = 500;
		String target = "Database";
		String message = "Connection error";

		ErrorDetail errorDetail = new ErrorDetail(target, message, code);

		String expectedString = "ErrorDetail{code=500, target='Database', message='Connection error'}";

		assertEquals(expectedString, errorDetail.toString());
	}

}
