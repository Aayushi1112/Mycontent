package com.integration.auth.entrypoint.dto;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

class SSOLoginRequestTest {

	@Test
	void testSSOLoginRequestGettersAndSetters() {
		SSOLoginRequest ssoLoginRequest = new SSOLoginRequest();
		ssoLoginRequest.setUsername("testUser");
		ssoLoginRequest.setTenantId("testTenant");

		assertEquals("testUser", ssoLoginRequest.getUsername());
		assertEquals("testTenant", ssoLoginRequest.getTenantId());
	}

}
