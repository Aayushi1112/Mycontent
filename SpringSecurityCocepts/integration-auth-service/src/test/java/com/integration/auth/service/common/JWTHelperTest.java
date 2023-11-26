package com.integration.auth.service.common;

import com.auth0.jwt.JWTCreator;
import com.integration.auth.common.errors.ErrorCodesAndMessages;
import com.integration.auth.common.exceptions.InvalidInputDataValidationException;
import com.integration.auth.common.util.Constants;
import com.integration.auth.common.util.TargetTypes;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JWTHelperTest {

	private JWTHelper jwtHelper;

	private String jwtSecret = "yourSecretKey";

	@BeforeEach
	void setUp() {
		jwtHelper = new JWTHelper();
		ReflectionTestUtils.setField(jwtHelper, "jwtSecret", jwtSecret);
	}

	@Test
	void testGenerateJwtToken() {
		String subject = "testUser";
		Map<String, String> claims = new HashMap<>();
		claims.put("sourceIp", "127.0.0.1");
		claims.put("loginType", "web");
		int hours = 1;

		String jwtToken = jwtHelper.generateJwtToken(subject, claims, hours);
		assertNotNull(jwtToken);
	}

	@Test
	void testGenerateJwtTokenWithInvalidClaims() {
		String subject = "testUser";
		Map<String, String> claims = new HashMap<>(); // Empty claims
		int hours = 1;

		InvalidInputDataValidationException exception = assertThrows(InvalidInputDataValidationException.class, () -> {
			jwtHelper.generateJwtToken(subject, claims, hours);
		});

		assertEquals(TargetTypes.EMPTY_JWT_CLAIMS.name(), exception.getErrors().getTarget());
		assertEquals(ErrorCodesAndMessages.EMPTY_JWT_CLAIMS_ERROR_MSG, exception.getErrors().getMessage());
		assertEquals(ErrorCodesAndMessages.EMPTY_JWT_CLAIMS_ERROR_CODE, exception.getErrors().getCode());
	}

	@Test
	void testGetUserNameFromJwtToken() {
		String token = createValidJwtToken();

		String userName = jwtHelper.getUserNameFromJwtToken(token);

		assertEquals("testUser", userName);
	}

	@Test
	void testGetLoginTypeFromToken() {
		String token = createValidJwtToken();

		String loginType = jwtHelper.getLoginTypeFromToken(token);

		assertEquals("web", loginType);
	}

	@Test
	void testGetDeviceFromToken() {
		String token = createValidJwtToken();

		String device = jwtHelper.getDeviceFromToken(token);

		assertNull(device); // Not present in the token, so it should be null
	}

	@Test
	void testGetTenantIdFromJwtToken() {
		String token = createValidJwtTokenWithTenantId();

		String tenantId = jwtHelper.getTenantIdFromJwtToken(token);

		assertEquals("testTenantId", tenantId);
	}

	@Test
	void testValidateJwtToken() {
		String token = createValidJwtToken();

		boolean isValid = jwtHelper.validateJwtToken(token);

		assertTrue(isValid);
	}

	@Test
	void testValidateJwtTokenWithInvalidToken() {
		String invalidToken = "invalidToken";

		boolean isValid = jwtHelper.validateJwtToken(invalidToken);

		assertFalse(isValid);
	}

	@Test
	void testIsTokenExpired() {
		String token = createValidJwtToken();

		boolean isExpired = jwtHelper.isTokenExpired(token);

		assertFalse(isExpired);
	}

	@Test
	void testIsTokenExpiredWithExpiredToken() {
		String expiredToken = createExpiredJwtToken();

		boolean isExpired = jwtHelper.isTokenExpired(expiredToken);

		assertTrue(isExpired);
	}

	@Test
	void testDecodePayload() {
		String payload = "eyJzdWIiOiJ0ZXN0VXNlciIsImxvZ2luVHlwZSI6IndlYiIsInNvdXJjZUlwIjoiMTI3LjAuMC4xIiwiZXhwIjoxNjUzMTQ1ODQwLCJuYmYiOjE2NTMxNDEyNDB9";
		String decodedPayload = jwtHelper.decodePayload(payload);
		String expectedPayload = "{\"sub\":\"testUser\",\"loginType\":\"web\",\"sourceIp\":\"127.0.0.1\",\"exp\":1653145840,\"nbf\":1653141240}";
		assertEquals(expectedPayload, decodedPayload);
	}

	private String createValidJwtToken() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR_OF_DAY, 1);

		JWTCreator.Builder jwtBuilder = JWT.create().withSubject("testUser");
		jwtBuilder.withClaim("sourceIp", "127.0.0.1");
		jwtBuilder.withClaim("loginType", "web");

		return jwtBuilder.withNotBefore(new Date()).withExpiresAt(calendar.getTime())
				.sign(Algorithm.HMAC256(jwtSecret));
	}

	private String createValidJwtTokenWithTenantId() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR_OF_DAY, 1);

		JWTCreator.Builder jwtBuilder = JWT.create().withSubject("testUser");
		jwtBuilder.withClaim("sourceIp", "127.0.0.1");
		jwtBuilder.withClaim("loginType", "web");
		jwtBuilder.withClaim(Constants.TENANT_ID, "testTenantId");

		return jwtBuilder.withNotBefore(new Date()).withExpiresAt(calendar.getTime())
				.sign(Algorithm.HMAC256(jwtSecret));
	}

	private String createExpiredJwtToken() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR_OF_DAY, -1);

		JWTCreator.Builder jwtBuilder = JWT.create().withSubject("testUser");
		jwtBuilder.withClaim("sourceIp", "127.0.0.1");
		jwtBuilder.withClaim("loginType", "web");

		return jwtBuilder.withNotBefore(new Date()).withExpiresAt(calendar.getTime())
				.sign(Algorithm.HMAC256(jwtSecret));
	}

}
