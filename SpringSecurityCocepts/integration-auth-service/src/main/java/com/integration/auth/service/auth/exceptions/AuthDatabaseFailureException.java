package com.integration.auth.service.auth.exceptions;

import com.integration.auth.common.errors.Errors;

/**
 * Exception to be thrown while executing operations on databases.
 */
public class AuthDatabaseFailureException extends AuthServiceException {

	private static final long serialVersionUID = 1L;

	public AuthDatabaseFailureException(Exception ex) {
		super(ex, null);
	}

	public AuthDatabaseFailureException(Exception ex, Errors errors) {
		super(ex, errors);
	}

}
