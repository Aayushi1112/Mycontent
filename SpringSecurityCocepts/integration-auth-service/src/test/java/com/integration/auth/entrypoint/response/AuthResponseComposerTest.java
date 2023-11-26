package com.integration.auth.entrypoint.response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.integration.auth.common.responses.AuthResponse;

import com.integration.auth.common.responses.ApiResponse;

class AuthResponseComposerTest {

	@Test
	void composeAuthResponseTest() {
		AuthResponse authResponse = new AuthResponse();
		authResponse.setSessionToken("abc");
		authResponse.setNativeSystem("abc");
		authResponse.setRefreshToken("123");
		authResponse.setTokenType("new");
		ResponseEntity<String> response = AuthResponseComposer.composeAuthResponse(authResponse);
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());

	}

	@Test
	void composeResponseTest() {
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setUser("abc");
		apiResponse.setMessage("abc");
		ResponseEntity<String> response = AuthResponseComposer.composeResponse(apiResponse);
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());

	}

	@Test
	void composeRegisterResponse() {
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setUser("abc");
		apiResponse.setMessage("abc");
		ResponseEntity<String> response = AuthResponseComposer.composeRegisterResponse(apiResponse);
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	@Test
	void composeActivationResponseTest() {
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setUser("abc");
		apiResponse.setMessage("abc");
		ResponseEntity<String> response = AuthResponseComposer.composeActivationResponse(apiResponse);
		assertNotNull(response);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

	}

}
