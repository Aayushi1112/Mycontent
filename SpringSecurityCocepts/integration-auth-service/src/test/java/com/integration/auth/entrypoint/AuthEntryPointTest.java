package com.integration.auth.entrypoint;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.integration.auth.common.errors.Errors;
import com.integration.auth.common.responses.ApiResponse;
import com.integration.auth.common.responses.AuthResponse;
import com.integration.auth.common.util.Constants;
import com.integration.auth.entrypoint.dto.LoginRequest;
import com.integration.auth.entrypoint.dto.LogoutRequest;
import com.integration.auth.entrypoint.dto.SSOLoginRequest;
import com.integration.auth.entrypoint.dto.SignupRequest;
import com.integration.auth.entrypoint.validators.AuthServiceValidator;
import com.integration.auth.service.auth.AuthenticationService;
import com.integration.auth.service.auth.exceptions.AuthServiceException;

@WebMvcTest(controllers = AuthEntryPoint.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthEntryPointTest {

	@Autowired
	private MockMvc mockMvc;

	private static final String API = "/v1/user/";

	private static final String API_url = "/register";

	public static final String LOGIN = "/login";

	public static final String LOGOUT = "/logout";

	public static final String SSO_LOGIN = "/sso/login";

	public static final String SECRET = "clientSecret";

	public static final String ID = "clientId";

	public static final String clientId = "abc";

	public static final String clientSecret = "bdf";

	public static final String PASYSTEM = "paSystem";

	public static final String paSystem = "testPaSystem";

	@MockBean
	AuthServiceValidator authValidator;

	@MockBean
	AuthenticationService authenticationService;

	@Mock
	AuthEntryPoint authEntryPoint;

	@BeforeEach
	public void setupTestFixture() {
		MockitoAnnotations.initMocks(this);
	}

	// **************REGISTER ENDPOINT*************

	@Test
	void testRegisterUserSuccessCREATED() throws Exception {
		// AuthServiceValidator authValidator = Mockito.spy(new AuthServiceValidator());
		// AuthServiceValidator dataInputValidator=new AuthServiceValidator();
		// Create test data
		ObjectMapper objectMapper = new ObjectMapper();
		SignupRequest signUpRequest = new SignupRequest();
		signUpRequest.setUsername("testUser");
		signUpRequest.setTenantId("123");
		signUpRequest.setPassword("testPass");
		// signUpRequest.setNativeSystem(PASYSTEM);
		String requestStr = objectMapper.writeValueAsString(signUpRequest);
		doCallRealMethod().when(authValidator).validateRegisterUserEndpoint(signUpRequest);

		// Create a mock ApiResponse
		ApiResponse mockApiResponse = new ApiResponse();
		BDDMockito.given(authenticationService.register(signUpRequest, paSystem)).willReturn(mockApiResponse);

		mockMvc.perform(post(API + API_url).contentType("application/json").content(requestStr).header(ID, clientId)
				.header(SECRET, clientSecret).header(PASYSTEM, paSystem)).andExpect(status().isCreated());

	}

	@Test
	void testRegisterUserThrowException() throws Exception {
		SignupRequest signUpRequest = new SignupRequest();
		signUpRequest.setUsername("testUser");
		signUpRequest.setTenantId("123");
		signUpRequest.setPassword("testPass");
		String requestStr = (new Gson()).toJson(signUpRequest);
		BDDMockito.given(authenticationService.register(signUpRequest, paSystem)).willThrow(AuthServiceException.class);
		mockMvc.perform(post(API + API_url).contentType("application/json").content(requestStr).header(ID, clientId)
				.header(SECRET, clientSecret).header(PASYSTEM, paSystem)).andExpect(status().is5xxServerError());

	}

	// **************LOGIN ENDPOINT*********************

	@Test
	void testLoginSuccess() throws Exception {
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUsername("test12");
		loginRequest.setTenantId("123");
		loginRequest.setPassword("test123");
		String loginrequestStr = (new Gson()).toJson(loginRequest);
		AuthResponse mockApiResponse = new AuthResponse();
		BDDMockito.given(authenticationService.processLogin(loginRequest, paSystem)).willReturn(mockApiResponse);
		mockMvc.perform(post(API + LOGIN).contentType("application/json").content(loginrequestStr).header(ID, clientId)
				.header(SECRET, clientSecret).header(PASYSTEM, paSystem)).andExpect(status().isOk());
	}

	@Test
	void testLoginFailure() throws Exception {
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUsername("test12");
		loginRequest.setTenantId("123");
		loginRequest.setPassword("test123");
		String loginrequestStr = (new Gson()).toJson(loginRequest);
		BDDMockito.given(authenticationService.processLogin(loginRequest, paSystem))
				.willThrow(AuthServiceException.class);
		mockMvc.perform(post(API + LOGIN).contentType("application/json").content(loginrequestStr).header(ID, clientId)
				.header(SECRET, clientSecret).header(PASYSTEM, paSystem)).andExpect(status().is5xxServerError());
	}

	// ********************SSO LOGIN TEST*******************
	@Test
	void testssoLoginSuccess() throws Exception {
		SSOLoginRequest ssoLoginRequest = new SSOLoginRequest();
		String ssoLoginRequestStr = (new Gson()).toJson(ssoLoginRequest);
		AuthResponse mockApiResponse = new AuthResponse();
		BDDMockito.given(authenticationService.ssoProcessLogin(ssoLoginRequest, paSystem)).willReturn(mockApiResponse);
		mockMvc.perform(post(API + SSO_LOGIN).contentType("application/json").content(ssoLoginRequestStr)
				.header(ID, clientId).header(SECRET, clientSecret).header(PASYSTEM, paSystem))
				.andExpect(status().isOk());

	}

	@Test
	void testssoLoginFailure() throws Exception {
		SSOLoginRequest ssoLoginRequest = new SSOLoginRequest();
		String ssoLoginRequestStr = (new Gson()).toJson(ssoLoginRequest);
		BDDMockito.given(authenticationService.ssoProcessLogin(ssoLoginRequest, paSystem))
				.willThrow(AuthServiceException.class);
		mockMvc.perform(post(API + SSO_LOGIN).contentType("application/json").content(ssoLoginRequestStr)
				.header(ID, clientId).header(SECRET, clientSecret).header(PASYSTEM, paSystem))
				.andExpect(status().is5xxServerError());

	}

	@Test
	void testforceLogoutSucess() throws Exception {
		LogoutRequest logoutRequest = new LogoutRequest();
		logoutRequest.setUsername("abc");
		logoutRequest.setTerminationReason("logoff");
		logoutRequest.setTenantId("123");
		String logoutRequeststr = (new Gson()).toJson(logoutRequest);
		doNothing().when(authValidator).validateRequestInput(logoutRequest);
		ApiResponse mockApiResponse = new ApiResponse();
		BDDMockito.given(authenticationService.forceTerminate(logoutRequest.getUsername(), logoutRequest.getTenantId(),
				logoutRequest.getTerminationReason())).willReturn(mockApiResponse);
		mockMvc.perform(post(API + Constants.FORCE_LOGOUT).contentType("application/json").content(logoutRequeststr))
				.andExpect(status().isOk());
	}

	@Test
	void testforceLogoutFailure() throws Exception {
		LogoutRequest logoutRequest = new LogoutRequest();
		logoutRequest.setUsername("abc");
		logoutRequest.setTerminationReason("logoff");
		logoutRequest.setTenantId("123");
		String logoutRequeststr = (new Gson()).toJson(logoutRequest);
		BDDMockito
				.given(authenticationService.forceTerminate(logoutRequest.getUsername(), logoutRequest.getTenantId(),
						logoutRequest.getTerminationReason()))
				.willThrow(new AuthServiceException(new Errors("abc", "abc", 0)));
		mockMvc.perform(post(API + Constants.FORCE_LOGOUT).contentType("application/json").content(logoutRequeststr))
				.andExpect(status().is5xxServerError());
	}

	@Test
	void testLogout() throws Exception {
		String sessionToken = "validSessionToken";
		String userId = "testUser";
		doNothing().when(authValidator).validateRequestInput(sessionToken, userId);
		ApiResponse mockApiResponse = new ApiResponse();
		BDDMockito.given(authenticationService.terminateToken(sessionToken, userId)).willReturn(mockApiResponse);
		mockMvc.perform(post(API + LOGOUT).contentType("application/json").header("sessionToken", "sessionToken")
				.header("userId", "userId")).andExpect(status().isOk());
	}

}
