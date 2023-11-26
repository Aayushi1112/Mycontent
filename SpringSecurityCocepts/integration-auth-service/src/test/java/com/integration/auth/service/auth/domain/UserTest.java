package com.integration.auth.service.auth.domain;

import org.junit.jupiter.api.Test;
import java.util.Date;
import org.junit.Assert;
import org.mockito.InjectMocks;

class UserTest {

	@InjectMocks
	User user;

	@Test
	void testUserConstructor() {
		String username = "testuser";
		String password = "password123";
		String tenantId = "tenant123";
		String paSystem = "pa123";
		String accountStatus = "active";
		String nativeSystem = "system123";
		Date createdDate = new Date();

		User user = new User(username, password, tenantId, paSystem, accountStatus, nativeSystem, createdDate);

		Assert.assertEquals(username, user.getUsername());
		Assert.assertEquals(password, user.getPassword());
		Assert.assertEquals(tenantId, user.getTenantId());
		Assert.assertEquals(paSystem, user.getPaSystem());
		Assert.assertEquals(accountStatus, user.getAccountStatus());
		Assert.assertEquals(nativeSystem, user.getNativeSystem());
		Assert.assertEquals(createdDate, user.getCreatedDate());
	}

}
