package com.integration.auth.common.errors;

import java.io.Serializable;

/**
 *
 * Class for creating the details of the {@link Errors} send in the responses.
 * {@link Errors} contain an array of the {@link ErrorDetail} objects representing various
 * errors.
 */
public class ErrorDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * the error code for specific error.
	 */
	private final int code;

	/*
	 * the sub module or specific section of the module at which the error occurred
	 */
	private final String target;

	/**
	 * specific error message.
	 */
	private final String message;

	public ErrorDetail(String target, String message, int code) {
		this.target = target;
		this.message = message;
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public String getTarget() {
		return target;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "ErrorDetail{" + "code=" + code + ", target='" + target + '\'' + ", message='" + message + '\'' + '}';
	}

}
