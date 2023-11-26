package com.integration.auth.entrypoint.validators;

import com.integration.auth.common.exceptions.InvalidInputDataValidationException;
import com.integration.auth.common.exceptions.ValidationFailureException;
import com.integration.auth.entrypoint.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class AuthServiceValidatorTest {

	@InjectMocks
	private AuthServiceValidator authServiceValidator;

	@Mock
	private SignupRequest signupRequest;

	@Mock
	private LoginRequest loginRequest;

	@Mock
	private SSOLoginRequest ssoLoginRequest;

	@Mock
	private PasswordChangeRequest passwordChangeRequest;

	@Mock
	private PasswordResetRequest passwordResetRequest;

	@Mock
	private DeleteUserInputRequest deleteUserInputRequest;

	@BeforeEach
	void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testValidateRegisterEndpointWithValidRequest() {
		when(signupRequest.getUsername()).thenReturn("testUsername");
		when(signupRequest.getPassword()).thenReturn("testPassword");
		when(signupRequest.getTenantId()).thenReturn("testTenantId");

		authServiceValidator.validateRegisterEndpoint(signupRequest);
	}

	@Test
	void testValidateRegisterEndpointWithMissingUsername() {
		when(signupRequest.getUsername()).thenReturn(null);
		when(signupRequest.getPassword()).thenReturn("testPassword");
		when(signupRequest.getTenantId()).thenReturn("testTenantId");

		assertThrows(ValidationFailureException.class, () -> {
			authServiceValidator.validateRegisterEndpoint(signupRequest);
		});
	}

	@Test
	void testValidateRegisterEndpointWithMissingPassword() {
		when(signupRequest.getUsername()).thenReturn("testUsername");
		when(signupRequest.getPassword()).thenReturn(null);
		when(signupRequest.getTenantId()).thenReturn("testTenantId");

		assertThrows(ValidationFailureException.class, () -> {
			authServiceValidator.validateRegisterEndpoint(signupRequest);
		});
	}

	@Test
	void testValidateChangePasswordRequestWithSameOldAndNewPassword() {
		when(passwordChangeRequest.getUsername()).thenReturn("testUsername");
		when(passwordChangeRequest.getOldPassword()).thenReturn("testPassword");
		when(passwordChangeRequest.getNewPassword()).thenReturn("testPassword");
		when(passwordChangeRequest.getConfirmPassword()).thenReturn("testPassword");
		when(passwordChangeRequest.getTenantId()).thenReturn("testTenantId");

		assertThrows(InvalidInputDataValidationException.class, () -> {
			authServiceValidator.validateChangePasswordRequest(passwordChangeRequest);
		});
	}

	@Test
	void testValidateLoginUserEndpointWithValidRequest() {
		when(loginRequest.getUsername()).thenReturn("testUsername");
		when(loginRequest.getPassword()).thenReturn("testPassword");
		when(loginRequest.getTenantId()).thenReturn("testTenantId");

		assertDoesNotThrow(() -> {
			authServiceValidator.validateLoginUserEndpoint(loginRequest);
		});
	}

	@Test
	void testValidateLoginUserEndpointWithMissingUsername() {
		when(loginRequest.getUsername()).thenReturn(null);
		when(loginRequest.getPassword()).thenReturn("testPassword");
		when(loginRequest.getTenantId()).thenReturn("testTenantId");

		assertThrows(InvalidInputDataValidationException.class,
				() -> authServiceValidator.validateLoginUserEndpoint(loginRequest));
	}

	@Test
	void testValidateLoginUserEndpointWithMissingPassword() {
		when(loginRequest.getUsername()).thenReturn("testUsername");
		when(loginRequest.getPassword()).thenReturn(null);
		when(loginRequest.getTenantId()).thenReturn("testTenantId");

		assertThrows(InvalidInputDataValidationException.class, () -> {
			authServiceValidator.validateLoginUserEndpoint(loginRequest);
		});
	}

	@Test
	void testValidateLoginUserEndpointWithMissingTenantId() {
		when(loginRequest.getUsername()).thenReturn("testUsername");
		when(loginRequest.getPassword()).thenReturn("testPassword");
		when(loginRequest.getTenantId()).thenReturn(null);

		assertThrows(InvalidInputDataValidationException.class, () -> {
			authServiceValidator.validateLoginUserEndpoint(loginRequest);
		});
	}

	@Test
	void testValidateSSOLoginUserEndpointWithValidRequest() {
		when(ssoLoginRequest.getUsername()).thenReturn("testUsername");
		when(ssoLoginRequest.getTenantId()).thenReturn("testTenantId");

		assertDoesNotThrow(() -> {
			authServiceValidator.validateSSOLoginUserEndpoint(ssoLoginRequest);
		});
	}

	@Test
	void testValidateSSOLoginUserEndpointWithMissingUsername() {
		when(ssoLoginRequest.getUsername()).thenReturn(null);
		when(ssoLoginRequest.getTenantId()).thenReturn("testTenantId");

		assertThrows(InvalidInputDataValidationException.class, () -> {
			authServiceValidator.validateSSOLoginUserEndpoint(ssoLoginRequest);
		});
	}

	@Test
	void testValidateSSOLoginUserEndpointWithMissingTenantId() {
		when(ssoLoginRequest.getUsername()).thenReturn("testUsername");
		when(ssoLoginRequest.getTenantId()).thenReturn(null);

		assertThrows(InvalidInputDataValidationException.class, () -> {
			authServiceValidator.validateSSOLoginUserEndpoint(ssoLoginRequest);
		});
	}

	@Test
	void testValidateRegisterUserEndpointHappyScenario() {
		// Mocking valid input data
		when(signupRequest.getUsername()).thenReturn("validUsername");
		when(signupRequest.getPassword()).thenReturn("Valid@Password123");
		when(signupRequest.getTenantId()).thenReturn("validTenantId");
		when(signupRequest.getNativeSystem()).thenReturn("FC");

		assertDoesNotThrow(() -> {
			authServiceValidator.validateRegisterUserEndpoint(signupRequest);
		});
	}

	@Test
	void testValidateRegisterUserEndpointWithMissingUsername() {
		when(signupRequest.getUsername()).thenReturn(null);
		when(signupRequest.getPassword()).thenReturn("testPassword");
		when(signupRequest.getTenantId()).thenReturn("testTenantId");
		when(signupRequest.getNativeSystem()).thenReturn("testNativeSystem");

		assertThrows(InvalidInputDataValidationException.class, () -> {
			authServiceValidator.validateRegisterUserEndpoint(signupRequest);
		});
	}

	@Test
	void testValidateRegisterUserEndpointWithMissingPassword() {
		when(signupRequest.getUsername()).thenReturn("testUsername");
		when(signupRequest.getPassword()).thenReturn(null);
		when(signupRequest.getTenantId()).thenReturn("testTenantId");
		when(signupRequest.getNativeSystem()).thenReturn("testNativeSystem");

		assertThrows(InvalidInputDataValidationException.class, () -> {
			authServiceValidator.validateRegisterUserEndpoint(signupRequest);
		});
	}

	@Test
	void testValidateRegisterUserEndpointWithMissingTenantId() {
		when(signupRequest.getUsername()).thenReturn("testUsername");
		when(signupRequest.getPassword()).thenReturn("testPassword");
		when(signupRequest.getTenantId()).thenReturn(null);
		when(signupRequest.getNativeSystem()).thenReturn("testNativeSystem");

		assertThrows(InvalidInputDataValidationException.class, () -> {
			authServiceValidator.validateRegisterUserEndpoint(signupRequest);
		});
	}

	@Test
	void testValidateRegisterUserEndpointWithMissingNativeSystem() {
		when(signupRequest.getUsername()).thenReturn("testUsername");
		when(signupRequest.getPassword()).thenReturn("testPassword");
		when(signupRequest.getTenantId()).thenReturn("testTenantId");
		when(signupRequest.getNativeSystem()).thenReturn(null);

		assertThrows(InvalidInputDataValidationException.class, () -> {
			authServiceValidator.validateRegisterUserEndpoint(signupRequest);
		});
	}

	@Test
	void testValidateRequestInputWithValidRequest() {
		when(passwordResetRequest.getUsername()).thenReturn("testUsername");

		assertDoesNotThrow(() -> {
			authServiceValidator.validateRequestInput("testSessionToken", passwordResetRequest.getUsername());
		});
	}

	@Test
	void testValidateRequestInputWithMissingSessionToken() {
		when(passwordResetRequest.getUsername()).thenReturn("testUsername");

		Executable executable = () -> authServiceValidator.validateRequestInput(null,
				passwordResetRequest.getUsername());
		assertThrows(InvalidInputDataValidationException.class, executable);
	}

	@Test
	void testValidateRequestInputWithMissingUsername() {
		when(passwordResetRequest.getUsername()).thenReturn(null);

		Executable executable = () -> authServiceValidator.validateRequestInput("testSessionToken",
				passwordResetRequest.getUsername());
		assertThrows(InvalidInputDataValidationException.class, executable);
	}

	@Test
	void testValidateInputTokenWithValidRequest() {
		when(passwordResetRequest.getUsername()).thenReturn("testUsername");
		when(passwordResetRequest.getTenantId()).thenReturn("testTenantId");

		assertDoesNotThrow(() -> {
			authServiceValidator.validateInputToken("testSessionToken", passwordResetRequest.getUsername(),
					passwordResetRequest.getTenantId());
		});
	}

	@Test
	void testValidateInputTokenWithMissingSessionToken() {
		when(passwordResetRequest.getUsername()).thenReturn("testUsername");
		when(passwordResetRequest.getTenantId()).thenReturn("testTenantId");

		Executable executable = () -> authServiceValidator.validateInputToken(null, passwordResetRequest.getUsername(),
				passwordResetRequest.getTenantId());
		assertThrows(InvalidInputDataValidationException.class, executable);
	}

	@Test
	void testValidateUserActivationRequestWithValidRequest() {
		when(passwordResetRequest.getUsername()).thenReturn("testUsername");
		when(passwordResetRequest.getTenantId()).thenReturn("testTenantId");

		assertDoesNotThrow(() -> {
			authServiceValidator.validateUserActivationRequest(passwordResetRequest.getUsername(), "ACTIVE",
					passwordResetRequest.getTenantId());
		});
	}

	@Test
	void testValidateUserActivationRequestWithMissingUsername() {
		when(passwordResetRequest.getUsername()).thenReturn(null);
		when(passwordResetRequest.getTenantId()).thenReturn("testTenantId");
		Executable executable = () -> authServiceValidator.validateUserActivationRequest(
				passwordResetRequest.getUsername(), "ACTIVE", passwordResetRequest.getTenantId());
		assertThrows(InvalidInputDataValidationException.class, executable);

	}

	@Test
	void testValidateUserActivationRequestWithInvalidStatus() {
		when(passwordResetRequest.getUsername()).thenReturn("testUsername");
		when(passwordResetRequest.getTenantId()).thenReturn("testTenantId");
		Executable executable = () -> authServiceValidator.validateUserActivationRequest(
				passwordResetRequest.getUsername(), "INVALID_STATUS", passwordResetRequest.getTenantId());
		assertThrows(InvalidInputDataValidationException.class, executable);
	}

	@Test
	void testValidateResetPasswordRequestWithValidRequest() {
		when(passwordResetRequest.getUsername()).thenReturn("testUsername");
		when(passwordResetRequest.getTenantId()).thenReturn("testTenantId");
		when(passwordResetRequest.getNewPassword()).thenReturn("newPassword");
		when(passwordResetRequest.getJourneyType()).thenReturn("RESET");

		assertDoesNotThrow(() -> {
			authServiceValidator.validateResetPasswordRequest(passwordResetRequest);
		});
	}

	@Test
	void testValidateResetPasswordRequestWithMissingUsername() {
		when(passwordResetRequest.getUsername()).thenReturn(null);
		when(passwordResetRequest.getTenantId()).thenReturn("testTenantId");
		when(passwordResetRequest.getNewPassword()).thenReturn("newPassword");
		when(passwordResetRequest.getJourneyType()).thenReturn("RESET");

		assertThrows(InvalidInputDataValidationException.class, () -> {
			authServiceValidator.validateResetPasswordRequest(passwordResetRequest);
		});
	}

	@Test
	void testValidateResetPasswordRequestWithInvalidJourneyType() {
		when(passwordResetRequest.getUsername()).thenReturn("testUsername");
		when(passwordResetRequest.getTenantId()).thenReturn("testTenantId");
		when(passwordResetRequest.getNewPassword()).thenReturn("newPassword");
		when(passwordResetRequest.getJourneyType()).thenReturn("");

		assertThrows(InvalidInputDataValidationException.class, () -> {
			authServiceValidator.validateResetPasswordRequest(passwordResetRequest);
		});
	}

	@Test
	void testValidateDeleteUserInputRequestWithValidRequest() {
		when(deleteUserInputRequest.getUsername()).thenReturn("testUsername");
		when(deleteUserInputRequest.getTenantId()).thenReturn("testTenantId");

		assertDoesNotThrow(() -> {
			authServiceValidator.validateDeleteUserInputRequest(deleteUserInputRequest);
		});
	}

	@Test
	void testValidateDeleteUserInputRequestWithMissingUsername() {
		when(deleteUserInputRequest.getUsername()).thenReturn(null);
		when(deleteUserInputRequest.getTenantId()).thenReturn("testTenantId");

		assertThrows(InvalidInputDataValidationException.class, () -> {
			authServiceValidator.validateDeleteUserInputRequest(deleteUserInputRequest);
		});
	}

	@Test
	void testValidateDeleteUserInputRequestWithMissingTenantId() {
		when(deleteUserInputRequest.getUsername()).thenReturn("testUsername");
		when(deleteUserInputRequest.getTenantId()).thenReturn(null);

		assertThrows(InvalidInputDataValidationException.class, () -> {
			authServiceValidator.validateDeleteUserInputRequest(deleteUserInputRequest);
		});
	}

}
