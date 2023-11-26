package com.integration.auth.service.auth.exceptions;

import com.integration.auth.common.errors.Errors;

public class AuthenticationException extends AuthServiceException {

	public AuthenticationException(Errors errors) {
		super(errors);
	}

	public AuthenticationException(Exception ex, Errors errors) {
		super(ex, errors);
	}

}
