package com.integration.auth.service.session.exceptions;

import com.integration.auth.common.errors.Errors;
import com.integration.auth.service.auth.exceptions.AuthServiceException;

public class SessionDatabaseFailureException extends AuthServiceException {

	private static final long serialVersionUID = 1L;

	public SessionDatabaseFailureException(Exception ex) {
		super(ex, null);
	}

	public SessionDatabaseFailureException(Exception ex, Errors errors) {
		super(ex, errors);
	}

}
