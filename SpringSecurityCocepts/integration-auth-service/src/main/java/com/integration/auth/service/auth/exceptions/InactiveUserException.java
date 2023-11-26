package com.integration.auth.service.auth.exceptions;

import com.integration.auth.common.errors.Errors;

public class InactiveUserException extends AuthServiceException {

	public InactiveUserException(Errors errors) {
		super(errors);
	}

}
