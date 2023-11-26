package com.integration.auth.entrypoint.dto;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

class DeleteUserInputRequestTest {

	@Test
	void testDeleteUserInputRequestGetterAndSetter() {
		// Sample data
		String username = "testUser";
		String tenantId = "testTenant";

		// Create DeleteUserInputRequest instance
		DeleteUserInputRequest deleteUserInputRequest = new DeleteUserInputRequest();
		deleteUserInputRequest.setUsername(username);
		deleteUserInputRequest.setTenantId(tenantId);

		// Test getter methods
		assertEquals(username, deleteUserInputRequest.getUsername());
		assertEquals(tenantId, deleteUserInputRequest.getTenantId());

		// Modify values using setter methods
		String newUsername = "newUser";
		String newTenantId = "newTenant";

		deleteUserInputRequest.setUsername(newUsername);
		deleteUserInputRequest.setTenantId(newTenantId);

		// Test updated values
		assertEquals(newUsername, deleteUserInputRequest.getUsername());
		assertEquals(newTenantId, deleteUserInputRequest.getTenantId());
	}

}
