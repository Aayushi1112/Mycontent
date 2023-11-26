package com.integration.auth.common.errors;

import com.integration.auth.common.util.DateTimeManager;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * This class object represent the error message returned to client on failure.
 */
public class Errors implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * the error message
	 */
	private final String message;

	/**
	 * the error code for the module
	 */
	private final int code;

	/**
	 * the target module where the error occurred.
	 */
	private final String target;

	/**
	 * the time stamp at which the error is observed
	 */
	private final String timestamp;

	/**
	 * an array of {@link ErrorDetail} objects
	 */
	private List<ErrorDetail> details;

	/**
	 * Constructor for {@link Errors}
	 * @param message - the error message
	 * @param code - the error code
	 */
	public Errors(String target, String message, int code) {
		this.target = target;
		this.message = message;
		this.code = code;
		timestamp = DateTimeManager.currentTimeStampStringInUTC();
	}

	public String getMessage() {
		return message;
	}

	public int getCode() {
		return code;
	}

	public String getTarget() {
		return target;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public List<ErrorDetail> getDetails() {
		return Collections.unmodifiableList(details);
	}

	public void setDetails(List<ErrorDetail> details) {
		this.details = details;
	}

	@Override
	public boolean equals(Object o) {

		if (!(o instanceof Errors))
			return false;

		Errors other = (Errors) o;
		return Objects.equals(code, other.getCode());
	}

	@Override
	public int hashCode() {
		return Objects.hash(code);
	}

	@Override
	public String toString() {
		return "Errors{" + "message='" + message + '\'' + ", code=" + code + ", target='" + target + '\''
				+ ", timestamp='" + timestamp + '\'' + ", details=" + details + '}';
	}

}
