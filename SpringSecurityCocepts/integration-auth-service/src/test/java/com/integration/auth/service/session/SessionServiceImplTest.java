package com.integration.auth.service.session;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.integration.auth.service.session.domain.UserSessionDetails;
import com.integration.auth.service.session.domain.repository.UserSessionRepository;
import com.integration.auth.service.session.util.JwtUtil;

class SessionServiceImplTest {

	@InjectMocks
	private SessionServiceImpl sessionService;

	@Mock
	private JwtUtil jwtUtil;

	@Mock
	private UserSessionRepository userSessionRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testSaveSessionDetails() {
		String sharedSessionToken = "sessionToken";
		String sharedRefreshToken = "refreshToken";
		String extendedTokenId = "extendedTokenId";

		UserSessionDetails userSessionDetails = new UserSessionDetails(); // Create a
																			// sample user
																			// session
																			// details

		when(jwtUtil.extractJwtClaims(sharedSessionToken, sharedRefreshToken, null)).thenReturn(userSessionDetails);

		sessionService.saveSessionDetails(sharedSessionToken, sharedRefreshToken);

		verify(jwtUtil, times(1)).extractJwtClaims(sharedSessionToken, sharedRefreshToken, null);
		verify(userSessionRepository, times(1)).storeUserSession(userSessionDetails);
	}

	@Test
	void testRefreshSessionDetails() {
		String generatedSessionToken = "newSessionToken";
		String generatedRefreshToken = "newRefreshToken";
		String existingRefreshToken = "existingRefreshToken";
		String extendedTokenId = "extendedTokenId";

		UserSessionDetails userSessionDetails = new UserSessionDetails();
		userSessionDetails.setJti(extendedTokenId);
		when(userSessionRepository.refreshUserSession(generatedSessionToken, generatedSessionToken,
				existingRefreshToken)).thenReturn(userSessionDetails);
		when(jwtUtil.extractJwtClaims(generatedSessionToken, generatedRefreshToken, extendedTokenId))
				.thenReturn(userSessionDetails);

		sessionService.refreshSessionDetails(generatedSessionToken, generatedRefreshToken, existingRefreshToken);

		verify(userSessionRepository, times(1)).refreshUserSession(generatedSessionToken, generatedSessionToken,
				existingRefreshToken);
		verify(jwtUtil, times(1)).extractJwtClaims(generatedSessionToken, generatedRefreshToken, extendedTokenId);
		verify(userSessionRepository, times(1)).storeUserSession(userSessionDetails);
	}

	@Test
	void testTerminateSession() {
		String sharedSessionToken = "sessionToken";

		sessionService.terminateSession(sharedSessionToken);

		verify(userSessionRepository, times(1)).terminateUserSession(sharedSessionToken);
	}

}
