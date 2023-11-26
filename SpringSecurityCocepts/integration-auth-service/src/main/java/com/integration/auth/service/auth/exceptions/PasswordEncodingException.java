package com.integration.auth.service.auth.exceptions;

import com.integration.auth.common.errors.Errors;

public class PasswordEncodingException extends AuthServiceException {

	public PasswordEncodingException(Errors errors) {
		super(errors);
	}

	public PasswordEncodingException(Exception ex, Errors errors) {
		super(ex, errors);
	}

}
