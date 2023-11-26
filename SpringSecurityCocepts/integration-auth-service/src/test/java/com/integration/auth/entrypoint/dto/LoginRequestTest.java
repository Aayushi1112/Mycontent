package com.integration.auth.entrypoint.dto;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

class LoginRequestTest {

	@Test
	void testLoginRequestGetterAndSetter() {
		// Sample data
		String username = "testUser";
		String password = "testPassword";
		String tenantId = "testTenant";

		// Create LoginRequest instance
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUsername(username);
		loginRequest.setPassword(password);
		loginRequest.setTenantId(tenantId);

		// Test getter methods
		assertEquals(username, loginRequest.getUsername());
		assertEquals(password, loginRequest.getPassword());
		assertEquals(tenantId, loginRequest.getTenantId());

		// Modify values using setter methods
		String newUsername = "newUser";
		String newPassword = "newPassword";
		String newTenantId = "newTenant";

		loginRequest.setUsername(newUsername);
		loginRequest.setPassword(newPassword);
		loginRequest.setTenantId(newTenantId);

		// Test updated values
		assertEquals(newUsername, loginRequest.getUsername());
		assertEquals(newPassword, loginRequest.getPassword());
		assertEquals(newTenantId, loginRequest.getTenantId());
	}

}
