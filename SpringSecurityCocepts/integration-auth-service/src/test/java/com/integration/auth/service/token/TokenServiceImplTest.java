package com.integration.auth.service.token;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.integration.auth.common.util.CommonUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.integration.auth.common.errors.ErrorCodesAndMessages;
import com.integration.auth.common.responses.ApiResponse;
import com.integration.auth.common.responses.AuthResponse;
import com.integration.auth.service.auth.exceptions.AuthenticationException;
import com.integration.auth.service.common.JWTHelper;
import com.integration.auth.service.session.SessionService;
import com.integration.auth.service.session.domain.UserSessionDetails;
import com.integration.auth.service.session.domain.repository.UserSessionRepository;

class TokenServiceImplTest {

	@InjectMocks
	private TokenServiceImpl tokenService;

	@Mock
	private JWTHelper jwtHelper;

	@Mock
	private TokenGeneratorService tokenGenerator;

	@Mock
	private SessionService sessionService;

	@Mock
	private UserSessionRepository userSessionRepository;

	@Mock
	CommonUtil commonUtil;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testRefreshToken() {
		when(jwtHelper.getUserNameFromJwtToken(anyString())).thenReturn("testUser");

		AuthResponse response = tokenService.refreshToken("refreshToken", "userId", "tenantId");

		assertNotNull(response);

	}

	@Test
	void testValidateToken_ValidToken() {

		String validToken = "validToken";
		String userId = "testUser";

		when(jwtHelper.getUserNameFromJwtToken(validToken)).thenReturn("testUser");
		when(userSessionRepository.fetchUserBySessionTokenAndUserName(validToken, userId))
				.thenReturn(Optional.of(new UserSessionDetails()));

		ApiResponse response = tokenService.validateToken("validToken", "userId");

		verify(jwtHelper, times(1)).getUserNameFromJwtToken("validToken");
		verify(userSessionRepository, times(1)).fetchUserBySessionTokenAndUserName("validToken", "testUser");

		assertNotNull(response);
	}

	@Test
	void testValidateToken_InvalidToken() {
		when(jwtHelper.getUserNameFromJwtToken(anyString())).thenReturn("testUser");
		when(userSessionRepository.fetchUserBySessionTokenAndUserName(anyString(), anyString()))
				.thenReturn(Optional.empty());

		AuthenticationException exception = assertThrows(AuthenticationException.class,
				() -> tokenService.validateToken("invalidToken", "userId"));

		verify(jwtHelper, times(1)).getUserNameFromJwtToken("invalidToken");
		verify(userSessionRepository, times(1)).fetchUserBySessionTokenAndUserName("invalidToken", "testUser");
		verifyNoMoreInteractions(jwtHelper, userSessionRepository);

		assertEquals(ErrorCodesAndMessages.INVALID_AUTHORIZATION, exception.getErrors().getCode());
		assertEquals(ErrorCodesAndMessages.INVALID_TOKEN, exception.getErrors().getMessage());
	}

}
