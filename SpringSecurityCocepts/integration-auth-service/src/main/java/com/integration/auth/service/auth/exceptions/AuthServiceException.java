package com.integration.auth.service.auth.exceptions;

import com.integration.auth.common.errors.ErrorFactory;
import com.integration.auth.common.errors.Errors;

public class AuthServiceException extends RuntimeException {

	private static final long serialVersionUID = 2273450922383949775L;

	/**
	 * the error code for the exception
	 */
	private final Errors errors;

	/**
	 * Constructs an instance of <code>RMSException</code> with the specified detail *
	 * message
	 */
	public AuthServiceException(Errors errors) {
		super(errors.getMessage());
		this.errors = errors;
	}

	public AuthServiceException(Exception ex, Errors errors) {
		super(ex);
		if (errors == null) {
			errors = ErrorFactory.createGenericError();
		}
		this.errors = errors;
	}

	public Errors getErrors() {
		return errors;
	}

}
