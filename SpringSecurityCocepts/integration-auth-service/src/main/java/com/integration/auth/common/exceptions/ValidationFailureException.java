package com.integration.auth.common.exceptions;

import com.integration.auth.common.errors.Errors;
import lombok.Getter;

@Getter
public class ValidationFailureException extends RuntimeException {

	private static final long serialVersionUID = -1660220413417085391L;

	private final Errors errors;

	protected ValidationFailureException(String message, Errors error) {
		super(message);
		this.errors = error;
	}

	public static ValidationFailureException getInstance(Errors error) {
		return new ValidationFailureException("validation failure", error);
	}

}
