package com.integration.auth.common.errors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import com.integration.auth.common.util.TargetTypes;

class ErrorFactoryTest {

	@Test
	void testCreateGenericError() {
		Errors error = ErrorFactory.createGenericError();

		assertEquals(TargetTypes.GENERIC_ERROR.name(), error.getTarget());
		assertEquals(ErrorCodesAndMessages.GENERIC_ERROR, error.getMessage());
		assertEquals(ErrorCodesAndMessages.GENERIC_ERROR_CODE, error.getCode());
	}

	@Test
	void testCreateFilterGenericError() {
		Errors error = ErrorFactory.createFilterGenericError();

		assertEquals(TargetTypes.GENERIC_AUTH_FILTER_ERROR.name(), error.getTarget());
		assertEquals(ErrorCodesAndMessages.GENERIC_AUTH_FILTER_ERROR, error.getMessage());
		assertEquals(ErrorCodesAndMessages.GENERIC_AUTH_FILTER_ERROR_CODE, error.getCode());
	}

	@Test
	void testHandleUserNotFound() {
		Errors error = ErrorFactory.handleUserNotFound();

		assertEquals(TargetTypes.INVALID_USERNAME.name(), error.getTarget());
		assertEquals(ErrorCodesAndMessages.USER_NOT_FOUND_ERROR_MESSAGE, error.getMessage());
		assertEquals(ErrorCodesAndMessages.USER_NOT_FOUND_ERROR_CODE, error.getCode());
	}

	@Test
	void testCreateError() {
		String errorMessage = "Custom error message";
		Errors error = ErrorFactory.createError(errorMessage);

		assertEquals(TargetTypes.GENERIC_ERROR.name(), error.getTarget());
		assertEquals(errorMessage, error.getMessage());
		assertEquals(ErrorCodesAndMessages.GENERIC_ERROR_CODE, error.getCode());
	}

	@Test
	void testGetJWTExtractClaimsFailureError() {
		Errors error = ErrorFactory.getJWTExtractClaimsFailureError();

		assertEquals(TargetTypes.JWT_EXTRACT_CLAIMS.name(), error.getTarget());
		assertEquals(ErrorCodesAndMessages.JWT_EXTRACT_CLAIMS_ERROR, error.getMessage());
		assertEquals(ErrorCodesAndMessages.JWT_EXTRACT_CLAIMS_ERROR_CODE, error.getCode());
	}

	@Test
	void testGetJWTDecodingFailureError() {
		Errors error = ErrorFactory.getJWTDecodingFailureError();

		assertEquals(TargetTypes.JWT_DECODE_ERROR.name(), error.getTarget());
		assertEquals(ErrorCodesAndMessages.JWT_DECODING_ERROR_MESSAGE, error.getMessage());
		assertEquals(ErrorCodesAndMessages.JWT_DECODING_ERROR_CODE, error.getCode());
	}

	@Test
	void testGetJWTValidationFailureError() {
		Errors error = ErrorFactory.getJWTValidationFailureError();

		assertEquals(TargetTypes.JWT_VALIDATION_ERROR.name(), error.getTarget());
		assertEquals(ErrorCodesAndMessages.JWT_VALIDATION_ERROR_MESSAGE, error.getMessage());
		assertEquals(ErrorCodesAndMessages.JWT_VALIDATION_ERROR_CODE, error.getCode());
	}

	@Test
	void testInvalidInputToken() {
		Errors error = ErrorFactory.invalidInputToken();

		assertEquals(TargetTypes.VALIDATE_TOKEN.name(), error.getTarget());
		assertEquals(ErrorCodesAndMessages.INVALID_INPUT_USER_TOKEN_ERROR_MESSAGE, error.getMessage());
		assertEquals(ErrorCodesAndMessages.INVALID_INPUT_USER_TOKEN_ERROR_CODE, error.getCode());
	}

	@Test
	void testGetJWTCreationFailureError() {
		Errors error = ErrorFactory.getJWTCreationFailureError();

		assertEquals(TargetTypes.JWT_CREATION_ERROR.name(), error.getTarget());
		assertEquals(ErrorCodesAndMessages.JWT_CREATION_ERROR_MESSAGE, error.getMessage());
		assertEquals(ErrorCodesAndMessages.JWT_CREATION_ERROR_CODE, error.getCode());
	}

	@Test
	void testGetAuthServiceDatabaseFailureError() {
		Errors error = ErrorFactory.getAuthServiceDatabaseFailureError();

		assertEquals(TargetTypes.ENTITY_MAPPING.name(), error.getTarget());
		assertEquals(ErrorCodesAndMessages.DATABASE_FAILURE_USER_REGISTER_ERROR_MSG, error.getMessage());
		assertEquals(ErrorCodesAndMessages.DATABASE_FAILURE_USER_REGISTER_ERROR_CODE, error.getCode());
	}

	@Test
	void testGetSessionServiceDatabaseFailureError() {
		Errors error = ErrorFactory.getSessionServiceDatabaseFailureError();

		assertEquals(TargetTypes.SAVE_SESSION.name(), error.getTarget());
		assertEquals(ErrorCodesAndMessages.DATABASE_FAILURE_USER_SESSION_CREATE_UPDATE_ERROR_MSG, error.getMessage());
		assertEquals(ErrorCodesAndMessages.DATABASE_FAILURE_USER_SESSION_CREATE_UPDATE_ERROR_CODE, error.getCode());
	}

	@Test
	void testGetAuthServiceRetrievalDatabaseFailureError() {
		Errors error = ErrorFactory.getAuthServiceRetrievalDatabaseFailureError();

		assertEquals(TargetTypes.RETRIEVE_AUTH_USER.name(), error.getTarget());
		assertEquals(ErrorCodesAndMessages.DATABASE_FAILURE_USER_DETAILS_RETRIEVAL_ERROR_MSG, error.getMessage());
		assertEquals(ErrorCodesAndMessages.DATABASE_FAILURE_USER_DETAILS_RETRIEVAL_ERROR_CODE, error.getCode());
	}

	@Test
	void testGetClientSecretDetailsRetrievalDatabaseFailureError() {
		Errors error = ErrorFactory.getClientSecretDetailsRetrievalDatabaseFailureError();

		assertEquals(TargetTypes.RETRIEVE_SECRET_DETAILS.name(), error.getTarget());
		assertEquals(ErrorCodesAndMessages.DATABASE_FAILURE_CLIENT_SECRET_DETAILS_RETRIEVAL_ERROR_MSG,
				error.getMessage());
		assertEquals(ErrorCodesAndMessages.DATABASE_FAILURE_CLIENT_SECRET_DETAILS_RETRIEVAL_ERROR_CODE,
				error.getCode());
	}

	@Test
	void testGetSessionServiceRetrievalDatabaseFailureError() {
		Errors error = ErrorFactory.getSessionServiceRetrievalDatabaseFailureError();

		assertEquals(TargetTypes.SAVE_SESSION.name(), error.getTarget());
		assertEquals(ErrorCodesAndMessages.DATABASE_FAILURE_USER_SESSION_RETRIEVAL_ERROR_MSG, error.getMessage());
		assertEquals(ErrorCodesAndMessages.DATABASE_FAILURE_USER_SESSION_RETRIEVAL_ERROR_CODE, error.getCode());
	}

	@Test
	void testGetInvalidMachineId() {
		Errors error = ErrorFactory.getInvalidMachineId(TargetTypes.INVALID_REQUEST_BODY);
		assertNotNull(error);
		assertEquals("Failed to process machine id :: ", error.getMessage());

	}

	@Test
	void testInterruptionOccurredDuringThread() {
		Errors error = ErrorFactory.interruptionOccurredDuringThread(TargetTypes.INVALID_REQUEST_BODY);
		assertNotNull(error);

		assertEquals("Interruption occurred during thread sleep :: ", error.getMessage());

	}

	@Test
	void testErrorForInvalidUsername() {
		Object error = ErrorFactory.errorForInvalidUsername();
		assertNotNull(error);

		assertEquals("Invalid username provided", ((Errors) error).getMessage());

	}

	@Test
	void testGetInvalidDataInRequestError() {
		ErrorDetail errorDetail = new ErrorDetail("Some detail message", "abc", 0);
		Errors error = ErrorFactory.getInvalidDataInRequestError(TargetTypes.INVALID_REQUEST_BODY, errorDetail);
		assertNotNull(error);
		assertEquals("Input data is incorrect or missing", error.getMessage());

	}

	@Test
	void testGetInvalidBaseTime() {
		Errors error = ErrorFactory.getInvalidBaseTime(TargetTypes.INVALID_REQUEST_BODY, "SomeObject");
		assertNotNull(error);
		assertEquals("INVALID_REQUEST_BODY", error.getTarget());
		assertEquals("Base time should be after 1970-01-01T00:00:00Z, or before current time :: " + "SomeObject",
				error.getMessage());

	}

	@Test
	void testGetInvalidExceededTimeLimitQueryCriteriaError() {
		Errors error = ErrorFactory.getInvalidExceededTimeLimitQueryCriteriaError(TargetTypes.INVALID_REQUEST_BODY,
				"SomeObject");
		assertNotNull(error);
		assertEquals("INVALID_REQUEST_BODY", error.getTarget());
		assertEquals("Exceeded the time limit :: " + "SomeObject", error.getMessage());
	}

	@Test
	void testFailingSingleInputDataError() {
		ErrorDetail error = new ErrorDetail("Your error message", "Error in  validation of  Request Body", 0);
		String errorMsg = "Error in  validation of  Request Body";
		int errorCode = 123;

		Errors errors = ErrorFactory.failingSingleInputDataError(error, errorMsg, errorCode);
		assertNotNull(error);
		assertEquals(errorMsg, error.getMessage());

	}

	@Test
	void testGetInvalidSequenceId() {
		Errors error = ErrorFactory.getInvalidSequenceId(TargetTypes.INVALID_REQUEST_BODY, "SomeObject");
		assertNotNull(error);
		String expectedErrorMessage = ErrorCodesAndMessages.QUERY_CRITERIA_SEQUENCE_ID + "SomeObject";
		assertEquals(expectedErrorMessage, error.getMessage());
	}

}
