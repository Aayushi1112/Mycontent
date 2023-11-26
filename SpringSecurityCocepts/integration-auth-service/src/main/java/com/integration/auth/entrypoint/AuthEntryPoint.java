package com.integration.auth.entrypoint;

import com.integration.auth.entrypoint.dto.LogoutRequest;
import com.integration.auth.entrypoint.dto.SSOLoginRequest;
import com.integration.auth.service.auth.exceptions.AuthServiceException;
import com.integration.auth.common.responses.FailureResponseComposer;
import com.integration.auth.entrypoint.dto.LoginRequest;
import com.integration.auth.entrypoint.dto.SignupRequest;
import com.integration.auth.entrypoint.response.AuthResponseComposer;
import com.integration.auth.entrypoint.validators.AuthServiceValidator;
import com.integration.auth.service.auth.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.integration.auth.common.util.Constants.*;

@Slf4j
@RestController
@RequestMapping(AUTH_API_BASE_URI)
public class AuthEntryPoint {

	@Autowired
	AuthServiceValidator dataInputValidator;

	@Autowired
	AuthenticationService authenticationService;

	@Operation(summary = "Authenticate a user and return an access token")
	@ApiResponses(
			value = { @ApiResponse(responseCode = "200", description = "Login successful and returns access token"),
					@ApiResponse(responseCode = "400", description = "Mandatory input request not present"),
					@ApiResponse(responseCode = "401", description = "Authentication failure"),
					@ApiResponse(responseCode = "500", description = "Server side issue") })
	@PostMapping(value = LOGIN, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest,
			@RequestHeader(CLIENT_ID) String clientId, @RequestHeader(CLIENT_SECRET) String clientSecret,
			@RequestHeader(PASYSTEM) String paSystem) {
		try {
			dataInputValidator.validateLoginUserEndpoint(loginRequest);
			return AuthResponseComposer.composeAuthResponse(authenticationService.processLogin(loginRequest, paSystem));
		}
		catch (AuthServiceException ex) {
			log.error(MARKER + LOGIN_API_ERROR, ex);
			return FailureResponseComposer.composeFailureResponse(ex, loginRequest.getUsername());
		}
	}

	@PostMapping(value = LOGOUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> logout(@RequestHeader(SESSION_TOKEN) String sessionToken,
			@RequestHeader(USER_ID) String userId) {
		try {
			dataInputValidator.validateRequestInput(sessionToken, userId);
			return AuthResponseComposer.composeResponse(authenticationService.terminateToken(sessionToken, userId));
		}
		catch (AuthServiceException ex) {
			log.error(MARKER + LOGOUT_API_ERROR, ex);
			return FailureResponseComposer.composeFailureResponse(ex);
		}
	}

	@Operation(summary = "Register user when new user is getting created")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Register successful and returns success message"),
			@ApiResponse(responseCode = "400",
					description = "Mandatory input request not present or same user exist with provided tenant details"),
			@ApiResponse(responseCode = "401", description = "Authentication failure"),
			@ApiResponse(responseCode = "500", description = "Server side issue") })
	@PostMapping(value = REGISTER, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> registerUser(@RequestBody SignupRequest signUpRequest,
			@RequestHeader(CLIENT_ID) String clientId, @RequestHeader(CLIENT_SECRET) String clientSecret,
			@RequestHeader(PASYSTEM) String paSystem) {
		try {
			dataInputValidator.validateRegisterUserEndpoint(signUpRequest);
			return AuthResponseComposer
					.composeRegisterResponse(authenticationService.register(signUpRequest, paSystem));
		}
		catch (AuthServiceException ex) {
			log.error(MARKER + REGISTER_API_ERROR, ex);
			return FailureResponseComposer.composeFailureResponse(ex, signUpRequest.getUsername());
		}
	}

	@PostMapping(value = SSO_LOGIN, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> ssoLogin(@RequestBody SSOLoginRequest ssoLoginRequest,
			@RequestHeader(CLIENT_ID) String clientId, @RequestHeader(CLIENT_SECRET) String clientSecret,
			@RequestHeader(PASYSTEM) String paSystem) {
		try {
			dataInputValidator.validateSSOLoginUserEndpoint(ssoLoginRequest);
			return AuthResponseComposer
					.composeAuthResponse(authenticationService.ssoProcessLogin(ssoLoginRequest, paSystem));
		}
		catch (AuthServiceException ex) {
			log.error(MARKER + SSO_LOGIN_API_ERROR, ex);
			return FailureResponseComposer.composeFailureResponse(ex, ssoLoginRequest.getUsername());
		}
	}

	@PostMapping(value = FORCE_LOGOUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> forceLogout(@RequestBody LogoutRequest inputRequest) {
		try {
			dataInputValidator.validateRequestInput(inputRequest);
			return AuthResponseComposer.composeResponse(authenticationService.forceTerminate(inputRequest.getUsername(),
					inputRequest.getTenantId(), inputRequest.getTerminationReason()));
		}
		catch (AuthServiceException ex) {
			log.error(MARKER + LOGOUT_API_ERROR, ex);
			return FailureResponseComposer.composeFailureResponse(ex);
		}
	}

}
