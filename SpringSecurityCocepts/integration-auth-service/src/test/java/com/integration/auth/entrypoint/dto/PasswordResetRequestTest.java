package com.integration.auth.entrypoint.dto;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

class PasswordResetRequestTest {

	@Test
	void testPasswordResetRequestGetterAndSetter() {
		// Sample data
		String username = "testUser";
		String newPassword = "newPassword";
		String tenantId = "testTenant";
		String journeyType = "reset";

		// Create PasswordResetRequest instance
		PasswordResetRequest resetRequest = new PasswordResetRequest();
		resetRequest.setUsername(username);
		resetRequest.setNewPassword(newPassword);
		resetRequest.setTenantId(tenantId);
		resetRequest.setJourneyType(journeyType);

		// Test getter methods
		assertEquals(username, resetRequest.getUsername());
		assertEquals(newPassword, resetRequest.getNewPassword());
		assertEquals(tenantId, resetRequest.getTenantId());
		assertEquals(journeyType, resetRequest.getJourneyType());

		// Modify values using setter methods
		String newUsername = "newUser";
		String newNewPassword = "newNewPassword";
		String newTenantId = "newTenant";
		String newJourneyType = "change";

		resetRequest.setUsername(newUsername);
		resetRequest.setNewPassword(newNewPassword);
		resetRequest.setTenantId(newTenantId);
		resetRequest.setJourneyType(newJourneyType);

		// Test updated values
		assertEquals(newUsername, resetRequest.getUsername());
		assertEquals(newNewPassword, resetRequest.getNewPassword());
		assertEquals(newTenantId, resetRequest.getTenantId());
		assertEquals(newJourneyType, resetRequest.getJourneyType());
	}

}
