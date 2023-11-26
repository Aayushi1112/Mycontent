package com.integration.auth.common.responses;

import com.integration.auth.common.errors.Errors;

/**
 * The class which all the Response Entities will extend. Contains basic fields for the
 * response.
 *
 */
public abstract class BaseDTO {

	private Errors errors;

	private Boolean hasErrors;

	public void setErrors(Errors errors) {
		hasErrors = true;
		this.errors = errors;
	}

	public Errors getErrors() {
		return errors;
	}

	public Boolean getHasErrors() {
		return hasErrors;
	}

}
