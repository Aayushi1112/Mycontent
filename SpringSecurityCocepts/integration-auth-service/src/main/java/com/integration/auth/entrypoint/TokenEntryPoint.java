package com.integration.auth.entrypoint;

import com.integration.auth.service.auth.exceptions.AuthServiceException;
import com.integration.auth.common.responses.FailureResponseComposer;
import com.integration.auth.entrypoint.response.AuthResponseComposer;
import com.integration.auth.entrypoint.validators.AuthServiceValidator;
import com.integration.auth.service.token.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.integration.auth.common.util.Constants.*;
import static com.integration.auth.common.util.Constants.REFRESH_API_ERROR;

@Slf4j
@RestController
@RequestMapping(AUTH_API_BASE_URI)
public class TokenEntryPoint {

	@Autowired
	AuthServiceValidator dataInputValidator;

	@Autowired
	TokenService tokenService;

	@Operation(summary = "This api refresh token once session token is expired")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Returns new sessions and refresh token"),
			@ApiResponse(responseCode = "400", description = "Mandatory input request not present"),
			@ApiResponse(responseCode = "401", description = "Authentication failure"),
			@ApiResponse(responseCode = "500", description = "Server side failure") })
	@PostMapping(value = REFRESH, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> refreshToken(@RequestHeader(REFRESH_TOKEN) String refreshToken,
			@RequestHeader(USER_ID) String userId, @RequestHeader(TENANT_ID) String tenantId) {
		try {
			dataInputValidator.validateInputToken(refreshToken, userId, tenantId);
			return AuthResponseComposer.composeAuthResponse(tokenService.refreshToken(refreshToken, userId, tenantId));
		}
		catch (AuthServiceException ex) {
			log.error(MARKER + REFRESH_API_ERROR, ex);
			return FailureResponseComposer.composeFailureResponse(ex);
		}
	}

	@PostMapping(value = VALIDATE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> validateToken(@RequestHeader(SESSION_TOKEN) String sessionToken,
			@RequestHeader(USER_ID) String userId, @RequestHeader(TENANT_ID) String tenantId) {
		try {
			dataInputValidator.validateInputToken(sessionToken, userId, tenantId);
			return AuthResponseComposer.composeResponse(tokenService.validateToken(sessionToken, userId));
		}
		catch (AuthServiceException ex) {
			log.error(MARKER + VALIDATE_API_ERROR, ex);
			return FailureResponseComposer.composeFailureResponse(ex, userId);
		}
	}

}
