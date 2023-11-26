package com.integration.auth.common.generator;

import com.integration.auth.common.errors.ErrorFactory;
import com.integration.auth.common.errors.Errors;

/**
 * Runtime exception of {@link UniqueIdGenerator}.
 */
public class UniqueIdGeneratorException extends RuntimeException {

	/**
	 * the error code for the exception
	 */
	private final Errors errors;

	public UniqueIdGeneratorException(Errors errors) {
		super(errors.getMessage());
		this.errors = errors;
	}

	public UniqueIdGeneratorException(Exception ex, Errors errors) {
		super(ex);
		if (errors == null) {
			errors = ErrorFactory.createGenericError();
		}
		this.errors = errors;
	}

}
