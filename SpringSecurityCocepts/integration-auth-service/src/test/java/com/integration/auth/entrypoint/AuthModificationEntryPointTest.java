package com.integration.auth.entrypoint;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
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

import com.integration.auth.common.errors.ErrorCodesAndMessages;
import com.integration.auth.common.errors.ErrorFactory;
import com.integration.auth.common.exceptions.InvalidInputDataValidationException;
import com.integration.auth.common.responses.ApiResponse;
import com.integration.auth.entrypoint.dto.ActivationRequest;
import com.integration.auth.entrypoint.dto.DeleteUserInputRequest;
import com.integration.auth.entrypoint.dto.PasswordChangeRequest;
import com.integration.auth.entrypoint.dto.PasswordResetRequest;
import com.integration.auth.entrypoint.validators.AuthServiceValidator;
import com.integration.auth.service.auth.AuthenticationService;
import com.integration.auth.service.usermanagement.UserManagementService;

class AuthModificationEntryPointTest {

	@InjectMocks
	private AuthModificationEntryPoint authModificationEntryPoint;

	@Mock
	private UserManagementService userManagementService;

	@Mock
	private AuthServiceValidator dataInputValidator;

	@Mock
	private AuthenticationService authenticationService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testChangePassword_Success() {
		PasswordChangeRequest request = new PasswordChangeRequest();
		request.setUsername("testUser");
		doNothing().when(dataInputValidator).validateChangePasswordRequest(request);

		when(userManagementService.changePassword(request)).thenReturn(any(ApiResponse.class));

		ResponseEntity<String> response = authModificationEntryPoint.changePassword(request, "clientId",
				"clientSecret");

		verify(dataInputValidator, times(1)).validateChangePasswordRequest(request);
		verify(userManagementService, times(1)).changePassword(request);
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	void testChangePassword_Exception() {
		PasswordChangeRequest request = new PasswordChangeRequest();
		request.setUsername("testUser");

		doThrow(new InvalidInputDataValidationException(
				ErrorFactory.createError(ErrorCodesAndMessages.MANDATORY_FIELD_ERROR_MSG))).when(dataInputValidator)
						.validateChangePasswordRequest(request);

		ResponseEntity<String> response = authModificationEntryPoint.changePassword(request, "clientId",
				"clientSecret");

		verify(dataInputValidator, times(1)).validateChangePasswordRequest(request);
		verify(userManagementService, never()).changePassword(any(PasswordChangeRequest.class));
		assertNotNull(response);
		assertTrue(response.getBody().contains(ErrorCodesAndMessages.MANDATORY_FIELD_ERROR_MSG));
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	void testToggleActivation_Success() {
		ActivationRequest request = new ActivationRequest();
		request.setUsername("testUser");
		request.setStatus("ACTIVE");
		request.setTenantId("testTenant");

		doNothing().when(dataInputValidator).validateUserActivationRequest("testUser", "ACTIVE", "testTenant");

		when(userManagementService.toggleActivation("testUser", "ACTIVE", "testTenant")).thenReturn(new ApiResponse());

		ResponseEntity<String> response = authModificationEntryPoint.toggleActivation(request);

		verify(dataInputValidator, times(1)).validateUserActivationRequest("testUser", "ACTIVE", "testTenant");
		verify(userManagementService, times(1)).toggleActivation("testUser", "ACTIVE", "testTenant");
		assertNotNull(response);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}

	@Test
	void testToggleActivation_Exception() {
		ActivationRequest request = new ActivationRequest();
		request.setUsername("testUser");
		request.setStatus("ACTIVE");
		request.setTenantId("testTenant");

		doThrow(new InvalidInputDataValidationException(
				ErrorFactory.createError(ErrorCodesAndMessages.MANDATORY_FIELD_ERROR_MSG))).when(dataInputValidator)
						.validateUserActivationRequest("testUser", "ACTIVE", "testTenant");

		ResponseEntity<String> response = authModificationEntryPoint.toggleActivation(request);

		verify(dataInputValidator, times(1)).validateUserActivationRequest("testUser", "ACTIVE", "testTenant");
		verify(userManagementService, never()).toggleActivation(anyString(), anyString(), anyString());
		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	void testResetPassword_Success() {
		PasswordResetRequest request = new PasswordResetRequest();
		request.setUsername("testUser");

		doNothing().when(dataInputValidator).validateResetPasswordRequest(request);

		when(userManagementService.resetPassword(request)).thenReturn(any(ApiResponse.class));

		ResponseEntity<String> response = authModificationEntryPoint.resetPassword(request, "clientId", "clientSecret");

		verify(dataInputValidator, times(1)).validateResetPasswordRequest(request);
		verify(userManagementService, times(1)).resetPassword(request);
		assertNotNull(response);
	}

	@Test
	void testResetPassword_Exception() {
		PasswordResetRequest request = new PasswordResetRequest();
		request.setUsername("testUser");

		doThrow(new InvalidInputDataValidationException(
				ErrorFactory.createError(ErrorCodesAndMessages.MANDATORY_FIELD_ERROR_MSG))).when(dataInputValidator)
						.validateResetPasswordRequest(request);

		ResponseEntity<String> response = authModificationEntryPoint.resetPassword(request, "clientId", "clientSecret");

		verify(dataInputValidator, times(1)).validateResetPasswordRequest(request);
		verify(userManagementService, never()).resetPassword(any(PasswordResetRequest.class));
		assertNotNull(response);
	}

	@Test
	void testDeleteUser_Success() {
		DeleteUserInputRequest request = new DeleteUserInputRequest();
		request.setUsername("testUser");

		doNothing().when(dataInputValidator).validateDeleteUserInputRequest(request);
		when(userManagementService.deleteUser(request)).thenReturn(any(ApiResponse.class));

		ResponseEntity<String> response = authModificationEntryPoint.deleteUser(request, "clientId", "clientSecret",
				"paSystem");

		verify(dataInputValidator, times(1)).validateDeleteUserInputRequest(request);
		verify(userManagementService, times(1)).deleteUser(request);
		assertNotNull(response);
	}

	@Test
	void testDeleteUser_Exception() {
		DeleteUserInputRequest request = new DeleteUserInputRequest();
		request.setUsername("testUser");

		doThrow(new InvalidInputDataValidationException(
				ErrorFactory.createError(ErrorCodesAndMessages.MANDATORY_FIELD_ERROR_MSG))).when(dataInputValidator)
						.validateDeleteUserInputRequest(request);

		ResponseEntity<String> response = authModificationEntryPoint.deleteUser(request, "clientId", "clientSecret",
				"paSystem");

		verify(dataInputValidator, times(1)).validateDeleteUserInputRequest(request);
		verify(userManagementService, never()).deleteUser(any(DeleteUserInputRequest.class));
		assertNotNull(response);
	}

}
