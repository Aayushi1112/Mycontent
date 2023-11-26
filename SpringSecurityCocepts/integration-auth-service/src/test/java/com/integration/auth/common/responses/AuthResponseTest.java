package com.integration.auth.common.responses;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class AuthResponseTest {

	@Test
	void testNoArgsConstructor() {
		AuthResponse response = new AuthResponse();
		assertNotNull(response);
	}

	@Test
	void testParameterizedConstructor() {

		AuthResponse response = new AuthResponse();

		assertNotNull(response);

	}

}
