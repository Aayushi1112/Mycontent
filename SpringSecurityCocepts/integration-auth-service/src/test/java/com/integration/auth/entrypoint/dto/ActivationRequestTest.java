package com.integration.auth.entrypoint.dto;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

class ActivationRequestTest {

	@Test
	void testActivationRequestGetterAndSetter() {
		// Sample data
		String username = "testUser";
		String status = "active";
		String tenantId = "testTenant";

		// Create ActivationRequest instance
		ActivationRequest activationRequest = new ActivationRequest();
		activationRequest.setUsername(username);
		activationRequest.setStatus(status);
		activationRequest.setTenantId(tenantId);

		// Test getter methods
		assertEquals(username, activationRequest.getUsername());
		assertEquals(status, activationRequest.getStatus());
		assertEquals(tenantId, activationRequest.getTenantId());

		// Modify values using setter methods
		String newUsername = "newUser";
		String newStatus = "inactive";
		String newTenantId = "newTenant";

		activationRequest.setUsername(newUsername);
		activationRequest.setStatus(newStatus);
		activationRequest.setTenantId(newTenantId);

		// Test updated values
		assertEquals(newUsername, activationRequest.getUsername());
		assertEquals(newStatus, activationRequest.getStatus());
		assertEquals(newTenantId, activationRequest.getTenantId());
	}

}
