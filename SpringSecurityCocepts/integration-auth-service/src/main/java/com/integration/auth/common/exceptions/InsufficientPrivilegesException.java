package com.integration.auth.common.exceptions;

import com.integration.auth.common.errors.Errors;
import com.integration.auth.service.auth.exceptions.AuthServiceException;

public class InsufficientPrivilegesException extends AuthServiceException {

	private static final long serialVersionUID = 1L;

	public InsufficientPrivilegesException(Errors errors) {
		super(errors);
	}

	public InsufficientPrivilegesException(Exception ex, Errors errors) {
		super(ex, errors);
	}

}
