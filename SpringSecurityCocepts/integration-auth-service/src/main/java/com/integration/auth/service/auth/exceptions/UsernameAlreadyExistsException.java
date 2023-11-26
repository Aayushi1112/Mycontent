package com.integration.auth.service.auth.exceptions;

import com.integration.auth.common.errors.Errors;

public class UsernameAlreadyExistsException extends AuthServiceException {

	public UsernameAlreadyExistsException(Errors errors) {
		super(errors);
	}

	public UsernameAlreadyExistsException(Exception ex, Errors errors) {
		super(ex, errors);
	}

}
