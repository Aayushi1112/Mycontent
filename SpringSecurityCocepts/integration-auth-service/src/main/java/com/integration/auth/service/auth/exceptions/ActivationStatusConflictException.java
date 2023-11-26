package com.integration.auth.service.auth.exceptions;

import com.integration.auth.common.errors.Errors;

public class ActivationStatusConflictException extends AuthServiceException {

	public ActivationStatusConflictException(Errors errors) {
		super(errors);
	}

	public ActivationStatusConflictException(Exception ex, Errors errors) {
		super(ex, errors);
	}

}
