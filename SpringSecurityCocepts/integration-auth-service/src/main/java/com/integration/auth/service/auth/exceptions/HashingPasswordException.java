package com.integration.auth.service.auth.exceptions;

import com.integration.auth.common.errors.Errors;

public class HashingPasswordException extends AuthServiceException {

	public HashingPasswordException(Exception ex, Errors errors) {
		super(ex, errors);
	}

}
