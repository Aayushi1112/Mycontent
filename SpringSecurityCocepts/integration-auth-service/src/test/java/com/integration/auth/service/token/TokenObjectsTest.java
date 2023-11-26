package com.integration.auth.service.token;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TokenObjectsTest {

	@Test
	void testTokenObjectsConstructor() {
		String userId = "testUserId";
		String tenantId = "testTenantId";
		String sourceIp = "127.0.0.1";
		String loginType = "web";
		String device = "desktop";

		TokenObjects tokenObjects = new TokenObjects(userId, tenantId, sourceIp, loginType, device);

		assertEquals(userId, tokenObjects.getUserId());
		assertEquals(tenantId, tokenObjects.getTenantId());
		assertEquals(sourceIp, tokenObjects.getSourceIp());
		assertEquals(loginType, tokenObjects.getLoginType());
		assertEquals(device, tokenObjects.getDevice());
	}

	@Test
	void testTokenObjectsGetterSetter() {
		TokenObjects tokenObjects = new TokenObjects("", "", "", "", "");

		tokenObjects.setUserId("testUserId");
		tokenObjects.setTenantId("testTenantId");
		tokenObjects.setSourceIp("127.0.0.1");
		tokenObjects.setLoginType("web");
		tokenObjects.setDevice("desktop");

		assertEquals("testUserId", tokenObjects.getUserId());
		assertEquals("testTenantId", tokenObjects.getTenantId());
		assertEquals("127.0.0.1", tokenObjects.getSourceIp());
		assertEquals("web", tokenObjects.getLoginType());
		assertEquals("desktop", tokenObjects.getDevice());
	}

}
