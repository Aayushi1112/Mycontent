package com.integration.auth.service.auth.encoder;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.integration.auth.service.auth.exceptions.PasswordMismatchException;

@ExtendWith(MockitoExtension.class)
class CustomPasswordEncoderTest {

	@Mock
	private CustomPasswordEncoder passwordEncoder;

	private final static String salt = "salt";

	@Test
	void testEncode_Successful() {
		String salt = "123";
		int iterations = 1000;
		passwordEncoder = new CustomPasswordEncoder(salt);
		String rawPassword = "testPassword";
		String encodedPassword = passwordEncoder.encode(rawPassword);
		assertNotNull(encodedPassword);
		assertTrue(encodedPassword.length() > 0);
	}

	@Test
	void testMatchesForMismatchedPasswords() {
		String rawPassword = "myPassword123";
		String encodedPassword = passwordEncoder.encode("differentPassword");

		assertFalse(passwordEncoder.matches(rawPassword, encodedPassword));
	}

	@Test
	void testMatchesPositiveScenario() {
		CustomPasswordEncoder encoder = new CustomPasswordEncoder(salt);
		String rawPassword = "myPassword123";
		String encodedPassword = encoder.encode(rawPassword);

		boolean result = encoder.matches(rawPassword, encodedPassword);
		assertTrue(result);
	}

	@Test
	void testMatchesNegativeScenario() {
		CustomPasswordEncoder encoder = new CustomPasswordEncoder(salt);
		String rawPassword = "myPassword123";
		String encodedPassword = encoder.encode("differentPassword");

		assertThrows(PasswordMismatchException.class, () -> encoder.matches(rawPassword, encodedPassword));
	}

}
