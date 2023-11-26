package com.integration.auth.service.token;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;

import com.integration.auth.common.exceptions.InvalidInputDataValidationException;
import com.integration.auth.common.responses.Token;
import com.integration.auth.common.util.CommonUtil;
import com.integration.auth.entrypoint.dto.LoginRequest;
import com.integration.auth.entrypoint.dto.SSOLoginRequest;
import com.integration.auth.service.common.JWTHelper;

class TokenGeneratorImplTest {

	@InjectMocks
	private TokenGeneratorImpl tokenGenerator;

	@Mock
	private JWTHelper jwtHelper;

	@Mock
	private CommonUtil commonUtil;

	@Value("${ias.jwt.expirationInHours.session}")
	private int sessionTokenExpirationInHours;

	@Value("${ias.jwt.expirationInHours.refresh}")
	private int refreshTokenExpirationInHours;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testCreateSessionToken() {
		String username = "testuser";
		Map<String, String> claims = createSampleClaims();

		when(jwtHelper.generateJwtToken(username, claims, sessionTokenExpirationInHours)).thenReturn("sessionToken");

		String sessionToken = tokenGenerator.createSessionToken(username, claims);

		assertEquals("sessionToken", sessionToken);
		verify(jwtHelper, times(1)).generateJwtToken(username, claims, sessionTokenExpirationInHours);
	}

	@Test
	void testCreateRefreshToken() {
		String username = "testuser";
		Map<String, String> claims = createSampleClaims();

		when(jwtHelper.generateJwtToken(username, claims, refreshTokenExpirationInHours)).thenReturn("refreshToken");

		String refreshToken = tokenGenerator.createRefreshToken(username, claims);

		assertEquals("refreshToken", refreshToken);
		verify(jwtHelper, times(1)).generateJwtToken(username, claims, refreshTokenExpirationInHours);
	}

	@Test
	void testFetchTokenDetailsWithLoginRequest() {
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUsername("testuser");

		when(jwtHelper.generateJwtToken(any(), any(), anyInt())).thenReturn("sampleToken");

		Token token = tokenGenerator.fetchTokenDetails(loginRequest, "paSystem", "loginType");

		assertEquals("sampleToken", token.getSessionToken());
		assertEquals("sampleToken", token.getRefreshToken());
		verify(jwtHelper, times(2)).generateJwtToken(any(), any(), anyInt());
	}

	@Test
	void testFetchTokenDetailsWithSSOLoginRequest() {
		SSOLoginRequest ssoLoginRequest = new SSOLoginRequest();
		ssoLoginRequest.setUsername("testuser");

		when(jwtHelper.generateJwtToken(any(), any(), anyInt())).thenReturn("sampleToken");

		Token token = tokenGenerator.fetchTokenDetails(ssoLoginRequest, "paSystem", "loginType");

		assertEquals("sampleToken", token.getSessionToken());
		assertEquals("sampleToken", token.getRefreshToken());
		verify(jwtHelper, times(2)).generateJwtToken(any(), any(), anyInt());
	}

	@Test
	void testFetchTokenDetailsWithInvalidRequest() {
		Object invalidRequest = new Object();

		assertThrows(InvalidInputDataValidationException.class, () -> {
			tokenGenerator.fetchTokenDetails(invalidRequest, "paSystem", "loginType");
		});

		verify(jwtHelper, never()).generateJwtToken(any(), any(), anyInt());
	}

	private Map<String, String> createSampleClaims() {
		Map<String, String> claims = new HashMap<>();
		claims.put("username", "testuser");
		claims.put("tenantId", "testTenant");
		return claims;
	}

}
