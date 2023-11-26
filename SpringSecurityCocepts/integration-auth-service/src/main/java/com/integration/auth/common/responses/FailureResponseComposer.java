package com.integration.auth.common.responses;

import com.integration.auth.common.errors.ErrorFactory;
import com.integration.auth.common.exceptions.*;
import com.integration.auth.common.util.UtilFunctions;
import com.integration.auth.service.auth.exceptions.*;
import com.integration.auth.service.session.exceptions.SessionDatabaseFailureException;
import com.integration.auth.service.token.exceptions.JWTClaimParserException;
import com.integration.auth.service.token.exceptions.JWTCreationException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;

public class FailureResponseComposer {

	public static final Logger log = LoggerFactory.getLogger(FailureResponseComposer.class);

	private FailureResponseComposer() {
	}

	private static BodyBuilder handleFailure(AuthServiceException ex, Object object) {
		UtilFunctions.logError(ex, object);
		if (ex instanceof AuthDatabaseFailureException || ex instanceof SessionDatabaseFailureException) {
			return compose500FailureResponse();
		}
		else if (ex instanceof InsufficientPrivilegesException || ex instanceof InvalidTokenException
				|| ex instanceof InactiveUserException) {
			return compose403ForbiddenAccessResponse();
		}
		else if (ex instanceof InvalidInputDataValidationException || ex instanceof JWTClaimParserException
				|| ex instanceof UsernameAlreadyExistsException || ex instanceof JWTCreationException
				|| ex instanceof ActivationStatusConflictException) {
			return compose400FailureResponse();
		}
		else if (ex instanceof AuthenticationException || ex instanceof PasswordMismatchException
				|| ex instanceof UserAuthenticationException || ex instanceof UserNotFoundException) {
			return compose401UnAuthorizedResponse();
		}

		else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Create 500 Internal Server failure response.
	 * @return
	 */
	private static BodyBuilder compose500FailureResponse() {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Create 400 Bad Request failure response.
	 * @return
	 */
	private static BodyBuilder compose400FailureResponse() {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST);
	}

	public static ResponseEntity<String> composeFailureResponse(AuthServiceException ex, Object object) {
		BodyBuilder responseEntity = handleFailure(ex, object);
		RestResponse<? extends BaseDTO> response = new RestResponse<>();
		response.setErrors(ex.getErrors());
		String respStr = UtilFunctions.createJSONStringForResponse(response);
		return responseEntity.body(respStr);
	}

	public static ResponseEntity<String> composeFailureResponse(AuthServiceException ex) {
		BodyBuilder responseEntity = handleFailure(ex, null);
		RestResponse<? extends BaseDTO> response = new RestResponse<>();
		response.setErrors(ex.getErrors());
		String respStr = UtilFunctions.createJSONStringForResponse(response);
		return responseEntity.body(respStr);
	}

	public static String composeAuthFilterFailureResponse(AuthServiceException ex, Object object) {
		UtilFunctions.logError(ex, object);
		RestResponse<? extends BaseDTO> response = new RestResponse<>();
		if (ex.getErrors() != null && StringUtils.isNotEmpty(ex.getErrors().toString())) {
			response.setErrors(ex.getErrors());
		}
		return UtilFunctions.createJSONStringForResponse(response);

	}

	public static String composeAuthFilterGenericFailureResponse(Exception ex, Object object) {
		UtilFunctions.logError(ex, object);
		RestResponse<? extends BaseDTO> response = new RestResponse<>();
		response.setErrors(ErrorFactory.createFilterGenericError());
		return UtilFunctions.createJSONStringForResponse(response);

	}

	private static BodyBuilder compose403ForbiddenAccessResponse() {
		return ResponseEntity.status(403);
	}

	private static BodyBuilder compose401UnAuthorizedResponse() {
		return ResponseEntity.status(401);
	}

}
