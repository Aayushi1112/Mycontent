package com.integration.auth.common.util;

import com.google.gson.Gson;
import com.integration.auth.service.auth.exceptions.AuthServiceException;
import com.integration.auth.common.responses.BaseDTO;
import com.integration.auth.common.responses.FailureResponseComposer;
import com.integration.auth.common.responses.RestResponse;

public class UtilFunctions {

	private UtilFunctions() {

	}

	/**
	 * Return the String JSON representation of the response object.
	 * @param response accepts a RestResponse object
	 * @return String
	 */
	public static <T extends BaseDTO> String createJSONStringForResponse(RestResponse<T> response) {
		Gson j = new Gson();
		return j.toJson(response);
	}

	public static void logError(AuthServiceException ex, Object object) {

		Throwable causeException = ex.getCause();
		if (causeException != null) {
			if (ex.getErrors() != null) {
				if (FailureResponseComposer.log.isErrorEnabled()) {
					FailureResponseComposer.log.error(
							" CauseMessage :{} Error : {} for object : {} for causeException: {} ",
							causeException.getMessage(), ex.getErrors(), object, causeException);
				}
			}
			else
				FailureResponseComposer.log.error("Error for object : {} for exception {} ", object,
						causeException.getMessage());

		}
		else {
			FailureResponseComposer.log.error("Error in execution ", ex);
		}
	}

	public static void logError(Exception ex, Object object) {
		Throwable causeException = ex.getCause();
		if (causeException != null) {
			FailureResponseComposer.log.error("Error for object : {} for exception {} ", object,
					causeException.getMessage());
		}
		else {
			FailureResponseComposer.log.error("Error in execution ", ex);
		}
	}

}
