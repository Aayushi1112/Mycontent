package com.integration.auth.service.auth.exceptions;

import com.integration.auth.common.errors.Errors;

public class UserNotFoundException extends AuthServiceException {

	public UserNotFoundException(Errors errors) {
		super(errors);
	}

	public UserNotFoundException(Exception ex, Errors errors) {
		super(ex, errors);
	}

}
