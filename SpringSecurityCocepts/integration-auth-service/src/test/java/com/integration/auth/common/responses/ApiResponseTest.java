package com.integration.auth.common.responses;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class ApiResponseTest {

	@Test
	void testNoArgsConstructor() {
		ApiResponse response = new ApiResponse();
		assertNotNull(response);
	}

	@Test
	void testParameterizedConstructor() {
		String message = "Test Message";
		String user = "Test User";

		ApiResponse response = new ApiResponse();

		assertNotNull(response);
	}

}
