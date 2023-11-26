package com.integration.auth.entrypoint;

import com.integration.auth.common.responses.FailureResponseComposer;
import com.integration.auth.entrypoint.dto.ActivationRequest;
import com.integration.auth.entrypoint.dto.DeleteUserInputRequest;
import com.integration.auth.entrypoint.dto.PasswordChangeRequest;
import com.integration.auth.entrypoint.dto.PasswordResetRequest;
import com.integration.auth.entrypoint.response.AuthResponseComposer;
import com.integration.auth.entrypoint.validators.AuthServiceValidator;
import com.integration.auth.service.usermanagement.UserManagementService;
import com.integration.auth.service.auth.AuthenticationService;
import com.integration.auth.service.auth.exceptions.AuthServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.integration.auth.common.util.Constants.*;

@Slf4j
@RestController
@RequestMapping(AUTH_API_BASE_URI)
public class AuthModificationEntryPoint {

	@Autowired
	private UserManagementService userManagementService;

	@Autowired
	AuthServiceValidator dataInputValidator;

	@Autowired
	AuthenticationService authenticationService;

	@PostMapping(value = CHANGE_PASSWORD, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> changePassword(@RequestBody PasswordChangeRequest passwordChangeRequest,
			@RequestHeader(CLIENT_ID) String clientId, @RequestHeader(CLIENT_SECRET) String clientSecret) {
		try {
			dataInputValidator.validateChangePasswordRequest(passwordChangeRequest);
			return AuthResponseComposer.composeResponse(userManagementService.changePassword(passwordChangeRequest));
		}
		catch (AuthServiceException ex) {
			log.error(MARKER + CHANGE_PASSWORD_API_ERROR, ex);
			return FailureResponseComposer.composeFailureResponse(ex, passwordChangeRequest.getUsername());
		}
	}

	@PutMapping(value = MODIFY, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> toggleActivation(@RequestBody ActivationRequest activationRequest) {
		try {

			dataInputValidator.validateUserActivationRequest(activationRequest.getUsername(),
					activationRequest.getStatus(), activationRequest.getTenantId());
			return AuthResponseComposer.composeActivationResponse(userManagementService.toggleActivation(
					activationRequest.getUsername(), activationRequest.getStatus(), activationRequest.getTenantId()));

		}
		catch (AuthServiceException ex) {

			log.error(MARKER + USER_ACTIVATION_ERROR, ex);
			return FailureResponseComposer.composeFailureResponse(ex, activationRequest.getUsername());
		}
	}

	@PostMapping(value = RESET_PASSWORD, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> resetPassword(@RequestBody PasswordResetRequest passwordResetRequest,
			@RequestHeader(CLIENT_ID) String clientId, @RequestHeader(CLIENT_SECRET) String clientSecret) {
		try {
			dataInputValidator.validateResetPasswordRequest(passwordResetRequest);
			return AuthResponseComposer.composeResponse(userManagementService.resetPassword(passwordResetRequest));
		}
		catch (AuthServiceException ex) {
			return FailureResponseComposer.composeFailureResponse(ex, passwordResetRequest.getUsername());
		}
	}

	@DeleteMapping(value = DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteUser(@RequestBody DeleteUserInputRequest inputRequest,
			@RequestHeader(CLIENT_ID) String clientId, @RequestHeader(CLIENT_SECRET) String clientSecret,
			@RequestHeader(PASYSTEM) String paSystem) {
		try {
			dataInputValidator.validateDeleteUserInputRequest(inputRequest);
			return AuthResponseComposer.composeResponse(userManagementService.deleteUser(inputRequest));
		}
		catch (AuthServiceException ex) {
			return FailureResponseComposer.composeFailureResponse(ex, inputRequest.getUsername());
		}
	}

}
