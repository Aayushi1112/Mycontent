package com.integration.auth.service.auth.exceptions;

import com.integration.auth.common.errors.Errors;

public class SaltKeyException extends AuthServiceException {

	public SaltKeyException(Exception ex, Errors errors) {
		super(ex, errors);
	}

}
