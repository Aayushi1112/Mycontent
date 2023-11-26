package com.integration.auth.common.exceptions;

import com.integration.auth.common.errors.Errors;
import com.integration.auth.service.auth.exceptions.AuthServiceException;

public class InvalidInputDataValidationException extends AuthServiceException {

	private static final long serialVersionUID = 1L;

	public InvalidInputDataValidationException(Errors errors) {
		super(errors);
	}

	public InvalidInputDataValidationException(Exception ex, Errors validationError) {
		super(ex, validationError);
	}

}