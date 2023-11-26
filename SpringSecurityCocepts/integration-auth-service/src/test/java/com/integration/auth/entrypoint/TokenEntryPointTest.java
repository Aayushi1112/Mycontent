package com.integration.auth.entrypoint;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.integration.auth.common.errors.ErrorCodesAndMessages;
import com.integration.auth.common.errors.ErrorFactory;
import com.integration.auth.common.exceptions.InvalidInputDataValidationException;
import com.integration.auth.common.responses.AuthResponse;
import com.integration.auth.entrypoint.validators.AuthServiceValidator;
import com.integration.auth.service.token.TokenService;

class TokenEntryPointTest {

	private MockMvc mockMvc;

	@InjectMocks
	private TokenEntryPoint tokenEntryPoint;

	@Mock
	private AuthServiceValidator dataInputValidator;

	@Mock
	private TokenService tokenService;

	@BeforeEach
	void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(tokenEntryPoint).build();
	}

	@Test
	void testRefreshToken_Success() throws Exception {
		doNothing().when(dataInputValidator).validateInputToken(anyString(), anyString(), anyString());

		when(tokenService.refreshToken(anyString(), anyString(), anyString())).thenReturn(new AuthResponse());

		ResponseEntity<String> response = tokenEntryPoint.refreshToken("refreshToken", "userId", "tenantId");

		verify(dataInputValidator, times(1)).validateInputToken("refreshToken", "userId", "tenantId");

		verify(tokenService, times(1)).refreshToken("refreshToken", "userId", "tenantId");

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	void testRefreshToken_Failure() throws Exception {
		doThrow(new InvalidInputDataValidationException(
				ErrorFactory.createError(ErrorCodesAndMessages.MANDATORY_FIELD_ERROR_MSG))).when(dataInputValidator)
						.validateInputToken(anyString(), anyString(), anyString());

		ResponseEntity<String> response = tokenEntryPoint.refreshToken("refreshToken", "userId", "tenantId");

		verify(dataInputValidator, times(1)).validateInputToken("refreshToken", "userId", "tenantId");

		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertTrue(response.getBody().contains(ErrorCodesAndMessages.MANDATORY_FIELD_ERROR_MSG));

	}

	@Test
	void testValidateToken_Success() throws Exception {
		doNothing().when(dataInputValidator).validateInputToken(anyString(), anyString(), anyString());

		ResponseEntity<String> response = tokenEntryPoint.validateToken("sessionToken", "userId", "tenantId");

		verify(dataInputValidator, times(1)).validateInputToken("sessionToken", "userId", "tenantId");

		verify(tokenService, times(1)).validateToken("sessionToken", "userId");

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode()); // Assuming OK status code
																// for successful
																// validation

	}

	@Test
	void testValidateToken_Failure() throws Exception {
		doThrow(new InvalidInputDataValidationException(
				ErrorFactory.createError(ErrorCodesAndMessages.MANDATORY_FIELD_ERROR_MSG))).when(dataInputValidator)
						.validateInputToken(anyString(), anyString(), anyString());

		ResponseEntity<String> response = tokenEntryPoint.validateToken("sessionToken", "userId", "tenantId");

		verify(dataInputValidator, times(1)).validateInputToken("sessionToken", "userId", "tenantId");

		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode()); // Assuming
																		// UNAUTHORIZED
																		// status code for
																		// authentication
																		// failure

	}

}
