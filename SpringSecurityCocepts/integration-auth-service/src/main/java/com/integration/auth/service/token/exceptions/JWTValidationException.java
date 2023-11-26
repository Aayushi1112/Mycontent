package com.integration.auth.service.token.exceptions;

import com.integration.auth.common.errors.Errors;
import com.integration.auth.service.auth.exceptions.AuthServiceException;

public class JWTValidationException extends AuthServiceException {

	public JWTValidationException(Exception ex, Errors errors) {
		super(ex, errors);
	}

}
