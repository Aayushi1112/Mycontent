package com.integration.auth.service.auth;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;

import com.integration.auth.common.responses.ApiResponse;
import com.integration.auth.common.responses.AuthResponse;
import com.integration.auth.common.responses.Token;
import com.integration.auth.common.util.Constants;
import com.integration.auth.common.util.TargetTypes;
import com.integration.auth.entrypoint.dto.LoginRequest;
import com.integration.auth.entrypoint.dto.SSOLoginRequest;
import com.integration.auth.entrypoint.dto.SignupRequest;
import com.integration.auth.service.auth.domain.User;
import com.integration.auth.service.auth.domain.repository.UserRepository;
import com.integration.auth.service.auth.encoder.CustomPasswordEncoder;
import com.integration.auth.service.auth.exceptions.UserAuthenticationException;
import com.integration.auth.service.auth.exceptions.UserNotFoundException;
import com.integration.auth.service.auth.exceptions.UsernameAlreadyExistsException;
import com.integration.auth.service.auth.provider.TenantUserPasswordAuthToken;
import com.integration.auth.service.session.SessionService;
import com.integration.auth.service.token.TokenGeneratorService;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

	@InjectMocks
	AuthenticationServiceImpl authenticationServiceImpl;

	@Mock
	private UserRepository userRepository;

	@Mock
	private CustomPasswordEncoder encoder;

	@Mock
	AuthenticationManager authenticationManager;

	@Mock
	SessionService sessionService;

	@Mock
	UserDetailsServiceImpl userDetailsService;

	@Mock
	TokenGeneratorService tokenGeneratorService;

	// ****************REGISTER USER*************
	@Test
	void testToRegisternewUsersuccess() {
		// Create a SignupRequest and other required data
		SignupRequest signUpRequest = new SignupRequest();
		signUpRequest.setNativeSystem("abc");
		signUpRequest.setPassword("abc");
		signUpRequest.setUsername("abc");
		signUpRequest.setTenantId("abc");
		String paSystem = "PaSystem";

		Mockito.when(
				userRepository.existsByUsernameAndTenantId(signUpRequest.getUsername(), signUpRequest.getTenantId()))
				.thenReturn(false);
		// Mock the behavior of the userRepository and passwordEncoder
		when(userRepository.save(Mockito.any(User.class))).thenReturn(new User()); // Mock
																					// saving
																					// the
																					// user
		when(encoder.encode(Mockito.anyString())).thenReturn("encodedPassword"); // Mock
																					// password
																					// encoding

		// Call the method under test
		ApiResponse result = authenticationServiceImpl.register(signUpRequest, paSystem);
		assertEquals("User created", result.getMessage());

	}

	@Test
	void testRegisternewUserfailAlreadyExist() {
		// Create a SignupRequest and other required data
		SignupRequest signUpRequest = new SignupRequest();
		signUpRequest.setNativeSystem("abc");
		signUpRequest.setPassword("abc");
		signUpRequest.setUsername("abc");
		signUpRequest.setTenantId("abc");
		String paSystem = "PaSystem";
		Mockito.when(
				userRepository.existsByUsernameAndTenantId(signUpRequest.getUsername(), signUpRequest.getTenantId()))
				.thenReturn(true);
		assertThrows(UsernameAlreadyExistsException.class,
				() -> authenticationServiceImpl.register(signUpRequest, paSystem));
	}

	// **************LOGIN*********************
	@Test
	void testProcessLogin_SuccessfulAuthentication() {
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setPassword("abc");
		loginRequest.setTenantId("123");
		loginRequest.setUsername("abc");
		Token token = new Token();
		token.setRefreshToken("session123");
		token.setRefreshToken("refresh123");

		TenantUserPasswordAuthToken tenantUserPasswordAuthToken = new TenantUserPasswordAuthToken(
				loginRequest.getTenantId(), loginRequest.getUsername(), loginRequest.getPassword(), null);
		when(authenticationManager.authenticate(any(TenantUserPasswordAuthToken.class)))
				.thenReturn(tenantUserPasswordAuthToken);
		when(userRepository.findByUsernameAndTenantId(loginRequest.getUsername(), loginRequest.getTenantId()))
				.thenReturn(new User()); // Simulate finding a user
		when(tokenGeneratorService.fetchTokenDetails(any(), any(), any())).thenReturn(token); // Simulate
																								// token
																								// generation
		Mockito.mock(Authentication.class);

		doNothing().when(sessionService).saveSessionDetails(Mockito.any(), Mockito.any());
		AuthResponse response = authenticationServiceImpl.processLogin(loginRequest, "paSystem");
		verify(authenticationManager, times(1)).authenticate(any());

	}

	@Test
	void testProcessLogin_FailedAuthentication_invalidUsernameException() {

		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setPassword("123");
		loginRequest.setTenantId("123");
		loginRequest.setUsername("abc");
		String paSystem = "PaSystem";
		TenantUserPasswordAuthToken tenantUserPasswordAuthToken = new TenantUserPasswordAuthToken(
				loginRequest.getTenantId(), loginRequest.getUsername(), loginRequest.getPassword(), null);
		when(authenticationManager.authenticate(any(TenantUserPasswordAuthToken.class)))
				.thenReturn(tenantUserPasswordAuthToken);

		when(tokenGeneratorService.fetchTokenDetails(any(), any(), any()))
				.thenThrow(new InternalAuthenticationServiceException("Authentication error"));

		assertThrows(UserNotFoundException.class, () -> authenticationServiceImpl.processLogin(loginRequest, paSystem));
	}

	@Test
	void testProcessLogin_FailedAuthentication_IssueInAuthenticating() {
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setPassword("123");
		loginRequest.setTenantId("123");
		loginRequest.setUsername("abc");
		String paSystem = "PaSystem";
		Authentication mockAuthentication = Mockito.mock(Authentication.class);
		mockAuthentication.setAuthenticated(false);
		when(authenticationManager.authenticate(any(TenantUserPasswordAuthToken.class))).thenReturn(mockAuthentication);
		assertThrows(UserAuthenticationException.class,
				() -> authenticationServiceImpl.processLogin(loginRequest, paSystem));

	}

	// ********SSOLOGIN*****************
	@Test
	void testSsoProcessLogin() {

		SSOLoginRequest ssoLoginRequest = new SSOLoginRequest();
		String paSystem = "PA System";

		Token mockToken = new Token();
		mockToken.setSessionToken("sessionToken");
		mockToken.setRefreshToken("refreshToken");

		Mockito.when(tokenGeneratorService.fetchTokenDetails(ssoLoginRequest, paSystem, TargetTypes.SSO_LOGIN.name()))
				.thenReturn(mockToken);

		AuthResponse result = authenticationServiceImpl.ssoProcessLogin(ssoLoginRequest, paSystem);

		assertEquals("sessionToken", result.getSessionToken());
		assertEquals("refreshToken", result.getRefreshToken());
	}

	// ************TERMINATE TOKEN******************88888

	@Test
	void testTerminateToken() {
		String token = "aa";
		String userId = "aa";
		ApiResponse result = authenticationServiceImpl.terminateToken(token, userId);
		assertEquals("Logout Successful", result.getMessage());
	}

	@Test
	void testForceTerminate() {
		String username = "testUser";
		String tenantId = "testTenant";
		String terminationReason = "Test Termination Reason";

		Mockito.doNothing().when(sessionService).invalidateSessions(username, tenantId, terminationReason);
		when(userRepository.findByUsernameAndTenantId(username, tenantId)).thenReturn(new User());
		ApiResponse result = authenticationServiceImpl.forceTerminate(username, tenantId, terminationReason);

		Mockito.verify(sessionService, Mockito.times(1)).invalidateSessions(username, tenantId, terminationReason);

		assertEquals(Constants.SESSION_INVALIDATED, result.getMessage());
	}

}
