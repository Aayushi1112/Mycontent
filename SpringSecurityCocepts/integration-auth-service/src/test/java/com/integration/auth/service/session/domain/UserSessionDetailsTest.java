package com.integration.auth.service.session.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserSessionDetailsTest {

	private UserSessionDetails userSession;

	@BeforeEach
	void setUp() {
		// Initialize a UserSessionDetails object for testing
		userSession = new UserSessionDetails();
	}

	@Test
	void testSetterGetterMethods() {
		// Test the getter and setter methods for various fields
		userSession = new UserSessionDetails();
		userSession.setSessionId("session123");
		assertEquals("session123", userSession.getSessionId());

		userSession.setJti("jti123");
		assertEquals("jti123", userSession.getJti());

		userSession.setUsername("user123");
		assertEquals("user123", userSession.getUsername());

		// Similar tests for other fields...
	}

	@Test
	void testNoArgsConstructor() {
		// Ensure that the no-args constructor sets default values
		UserSessionDetails newUserSession = new UserSessionDetails();

		assertNull(newUserSession.getSessionId());
		assertNull(newUserSession.getJti());
		// Ensure other fields have their default values as well...
	}

	@Test
	void testAllArgsConstructor() {
		// Test the all-args constructor
		Date creationDate = new Date();

		UserSessionDetails newUserSession = new UserSessionDetails("session123", "jti123", "user123", "token123",
				"refresh123", "tenant123", creationDate, "system123", true, "reason123", new Date(), "system123",
				new Date(), "system123", "127.0.0.1", "Login", "Device", "expirationOff123");

		assertEquals("session123", newUserSession.getSessionId());
		assertEquals("jti123", newUserSession.getJti());
		// Ensure other fields have the expected values...
	}

	// Add more test methods as needed to cover specific functionality and validation
	// rules.

}
