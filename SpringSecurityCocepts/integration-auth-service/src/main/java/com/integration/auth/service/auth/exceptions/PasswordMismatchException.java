package com.integration.auth.service.auth.exceptions;

import com.integration.auth.common.errors.Errors;

public class PasswordMismatchException extends AuthServiceException {

	public PasswordMismatchException(Errors errors) {
		super(errors);
	}

}
