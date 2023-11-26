package com.integration.auth.common.exceptions;

import com.integration.auth.common.errors.Errors;
import com.integration.auth.service.auth.exceptions.AuthServiceException;

public class InvalidTokenException extends AuthServiceException {

	private static final long serialVersionUID = 1L;

	public InvalidTokenException(Exception ex) {
		super(ex, null);
	}

	public InvalidTokenException(Errors errors) {
		super(errors);
	}

	public InvalidTokenException(Exception ex, Errors errors) {
		super(ex, errors);
	}

}
