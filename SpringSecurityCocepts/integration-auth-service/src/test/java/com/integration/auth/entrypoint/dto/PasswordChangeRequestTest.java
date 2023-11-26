package com.integration.auth.entrypoint.dto;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

class PasswordChangeRequestTest {

	@Test
	void testPasswordChangeRequestGetterAndSetter() {
		// Sample data
		String username = "testUser";
		String oldPassword = "oldPass";
		String newPassword = "newPass";
		String confirmPassword = "newPass"; // Usually, it should match the new password
		String tenantId = "testTenant";

		// Create PasswordChangeRequest instance
		PasswordChangeRequest request = new PasswordChangeRequest();
		request.setUsername(username);
		request.setOldPassword(oldPassword);
		request.setNewPassword(newPassword);
		request.setConfirmPassword(confirmPassword);
		request.setTenantId(tenantId);

		// Test getter methods
		assertEquals(username, request.getUsername());
		assertEquals(oldPassword, request.getOldPassword());
		assertEquals(newPassword, request.getNewPassword());
		assertEquals(confirmPassword, request.getConfirmPassword());
		assertEquals(tenantId, request.getTenantId());

		// Modify values using setter methods
		String newUsername = "newUser";
		String newOldPassword = "newOldPass";
		String newNewPassword = "newNewPass";
		String newConfirmPassword = "newNewPass"; // Usually, it should match the new
													// password
		String newTenantId = "newTenant";

		request.setUsername(newUsername);
		request.setOldPassword(newOldPassword);
		request.setNewPassword(newNewPassword);
		request.setConfirmPassword(newConfirmPassword);
		request.setTenantId(newTenantId);

		// Test updated values
		assertEquals(newUsername, request.getUsername());
		assertEquals(newOldPassword, request.getOldPassword());
		assertEquals(newNewPassword, request.getNewPassword());
		assertEquals(newConfirmPassword, request.getConfirmPassword());
		assertEquals(newTenantId, request.getTenantId());
	}

}
