package com.integration.auth.service.token.exceptions;

import com.integration.auth.common.errors.Errors;
import com.integration.auth.service.auth.exceptions.AuthServiceException;

public class JWTCreationException extends AuthServiceException {

	public JWTCreationException(Exception ex, Errors errors) {
		super(ex, errors);
	}

}
