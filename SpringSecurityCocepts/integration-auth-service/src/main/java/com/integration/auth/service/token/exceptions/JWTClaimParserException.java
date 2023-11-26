package com.integration.auth.service.token.exceptions;

import com.integration.auth.common.errors.Errors;
import com.integration.auth.service.auth.exceptions.AuthServiceException;

public class JWTClaimParserException extends AuthServiceException {

	public JWTClaimParserException(Errors errors) {
		super(errors);
	}

	public JWTClaimParserException(Exception ex, Errors errors) {
		super(ex, errors);
	}

}
