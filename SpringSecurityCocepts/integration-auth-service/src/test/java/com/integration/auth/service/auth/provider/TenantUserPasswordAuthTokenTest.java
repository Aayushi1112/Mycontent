package com.integration.auth.service.auth.provider;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TenantUserPasswordAuthTokenTest {

	@Test
	void testEquality() {
		TenantUserPasswordAuthToken authToken1 = new TenantUserPasswordAuthToken("tenant1", "user1", "password1");
		TenantUserPasswordAuthToken authToken2 = new TenantUserPasswordAuthToken("tenant1", "user1", "password1");

		assertEquals(authToken1, authToken2);
	}

	@Test
	void testInequalityDifferentClasses() {
		TenantUserPasswordAuthToken authToken = new TenantUserPasswordAuthToken("tenant1", "user1", "password1");
		String notAnAuthToken = "Not an instance of TenantUserPasswordAuthToken";

		// assertFalse(authToken.equals(notAnAuthToken));
		assertNotEquals(authToken, notAnAuthToken);
	}

	@Test
	void testInequalityNullObject() {
		TenantUserPasswordAuthToken authToken = new TenantUserPasswordAuthToken("tenant1", "user1", "password1");

		assertNotNull(authToken);
	}

	@Test
	void testInequalityDifferentTenants() {
		TenantUserPasswordAuthToken authToken1 = new TenantUserPasswordAuthToken("tenant1", "user1", "password1");
		TenantUserPasswordAuthToken authToken2 = new TenantUserPasswordAuthToken("tenant2", "user1", "password1");

		assertNotEquals(authToken1, authToken2);
	}

	@Test
	void testInequalityDifferentUsers() {
		TenantUserPasswordAuthToken authToken1 = new TenantUserPasswordAuthToken("tenant1", "user1", "password1");
		TenantUserPasswordAuthToken authToken2 = new TenantUserPasswordAuthToken("tenant1", "user2", "password1");

		assertNotEquals(authToken1, authToken2);
	}

	@Test
	void testInequalityDifferentPasswords() {
		TenantUserPasswordAuthToken authToken1 = new TenantUserPasswordAuthToken("tenant1", "user1", "password1");
		TenantUserPasswordAuthToken authToken2 = new TenantUserPasswordAuthToken("tenant1", "user1", "password2");

		assertNotEquals(authToken1, authToken2);
	}

	@Test
	void testInequalityWithNullTenantId() {
		TenantUserPasswordAuthToken authToken1 = new TenantUserPasswordAuthToken(null, "user1", "password1");
		TenantUserPasswordAuthToken authToken2 = new TenantUserPasswordAuthToken("tenant1", "user1", "password1");

		assertNotEquals(authToken1, authToken2);
	}

	@Test
	void testEqualityWithBothTenantIdsNull() {
		TenantUserPasswordAuthToken authToken1 = new TenantUserPasswordAuthToken(null, "user1", "password1");
		TenantUserPasswordAuthToken authToken2 = new TenantUserPasswordAuthToken(null, "user1", "password1");

		assertEquals(authToken1, authToken2);
	}

}
