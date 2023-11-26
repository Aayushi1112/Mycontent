package com.integration.auth.service.auth.exceptions;

import com.integration.auth.common.errors.Errors;

public class UserAuthenticationException extends AuthServiceException {

	public UserAuthenticationException(Errors errors) {
		super(errors);
	}

}
