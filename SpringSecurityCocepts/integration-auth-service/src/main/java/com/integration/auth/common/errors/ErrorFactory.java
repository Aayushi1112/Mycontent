package com.integration.auth.common.errors;

import java.util.Arrays;
import java.util.Collections;
import com.integration.auth.common.util.TargetTypes;
import io.vavr.collection.Seq;

/**
 * Factory Class For Creating Error Objects.
 */
public class ErrorFactory {

	private ErrorFactory() {
	}

	public static Errors createGenericError() {
		return new Errors(TargetTypes.GENERIC_ERROR.name(), ErrorCodesAndMessages.GENERIC_ERROR,
				ErrorCodesAndMessages.GENERIC_ERROR_CODE);
	}

	public static Errors createFilterGenericError() {
		return new Errors(TargetTypes.GENERIC_AUTH_FILTER_ERROR.name(), ErrorCodesAndMessages.GENERIC_AUTH_FILTER_ERROR,
				ErrorCodesAndMessages.GENERIC_AUTH_FILTER_ERROR_CODE);
	}

	public static Errors handleUserNotFound() {
		return new Errors(TargetTypes.INVALID_USERNAME.name(), ErrorCodesAndMessages.USER_NOT_FOUND_ERROR_MESSAGE,
				ErrorCodesAndMessages.USER_NOT_FOUND_ERROR_CODE);
	}

	public static Errors createError(String message) {
		return new Errors(TargetTypes.GENERIC_ERROR.name(), message, ErrorCodesAndMessages.GENERIC_ERROR_CODE);
	}

	public static Errors getJWTExtractClaimsFailureError() {
		return new Errors(TargetTypes.JWT_EXTRACT_CLAIMS.name(), ErrorCodesAndMessages.JWT_EXTRACT_CLAIMS_ERROR,
				ErrorCodesAndMessages.JWT_EXTRACT_CLAIMS_ERROR_CODE);
	}

	public static Errors getJWTDecodingFailureError() {
		return new Errors(TargetTypes.JWT_DECODE_ERROR.name(), ErrorCodesAndMessages.JWT_DECODING_ERROR_MESSAGE,
				ErrorCodesAndMessages.JWT_DECODING_ERROR_CODE);
	}

	public static Errors getJWTValidationFailureError() {
		return new Errors(TargetTypes.JWT_VALIDATION_ERROR.name(), ErrorCodesAndMessages.JWT_VALIDATION_ERROR_MESSAGE,
				ErrorCodesAndMessages.JWT_VALIDATION_ERROR_CODE);
	}

	public static Errors invalidInputToken() {
		return new Errors(TargetTypes.VALIDATE_TOKEN.name(),
				ErrorCodesAndMessages.INVALID_INPUT_USER_TOKEN_ERROR_MESSAGE,
				ErrorCodesAndMessages.INVALID_INPUT_USER_TOKEN_ERROR_CODE);
	}

	public static Errors getJWTCreationFailureError() {
		return new Errors(TargetTypes.JWT_CREATION_ERROR.name(), ErrorCodesAndMessages.JWT_CREATION_ERROR_MESSAGE,
				ErrorCodesAndMessages.JWT_CREATION_ERROR_CODE);
	}

	public static Errors getAuthServiceDatabaseFailureError() {
		return new Errors(TargetTypes.ENTITY_MAPPING.name(),
				ErrorCodesAndMessages.DATABASE_FAILURE_USER_REGISTER_ERROR_MSG,
				ErrorCodesAndMessages.DATABASE_FAILURE_USER_REGISTER_ERROR_CODE);
	}

	public static Errors getSessionServiceDatabaseFailureError() {
		return new Errors(TargetTypes.SAVE_SESSION.name(),
				ErrorCodesAndMessages.DATABASE_FAILURE_USER_SESSION_CREATE_UPDATE_ERROR_MSG,
				ErrorCodesAndMessages.DATABASE_FAILURE_USER_SESSION_CREATE_UPDATE_ERROR_CODE);
	}

	public static Errors getAuthServiceRetrievalDatabaseFailureError() {
		return new Errors(TargetTypes.RETRIEVE_AUTH_USER.name(),
				ErrorCodesAndMessages.DATABASE_FAILURE_USER_DETAILS_RETRIEVAL_ERROR_MSG,
				ErrorCodesAndMessages.DATABASE_FAILURE_USER_DETAILS_RETRIEVAL_ERROR_CODE);
	}

	public static Errors getClientSecretDetailsRetrievalDatabaseFailureError() {
		return new Errors(TargetTypes.RETRIEVE_SECRET_DETAILS.name(),
				ErrorCodesAndMessages.DATABASE_FAILURE_CLIENT_SECRET_DETAILS_RETRIEVAL_ERROR_MSG,
				ErrorCodesAndMessages.DATABASE_FAILURE_CLIENT_SECRET_DETAILS_RETRIEVAL_ERROR_CODE);
	}

	public static Errors getSessionServiceRetrievalDatabaseFailureError() {
		return new Errors(TargetTypes.SAVE_SESSION.name(),
				ErrorCodesAndMessages.DATABASE_FAILURE_USER_SESSION_RETRIEVAL_ERROR_MSG,
				ErrorCodesAndMessages.DATABASE_FAILURE_USER_SESSION_RETRIEVAL_ERROR_CODE);
	}

	public static Errors failingMultipleInputDataError(Seq<ErrorDetail> errorSeq, String errorMsg, int errorCode) {
		Errors errors = new Errors(ErrorCodesAndMessages.ERROR_IN_VALIDATION_OF_REQUEST_BODY, errorMsg, errorCode);
		ErrorDetail[] errorDetail = new ErrorDetail[errorSeq.asJava().size()];
		errors.setDetails(Arrays.asList(errorSeq.asJava().toArray(errorDetail)));
		return errors;
	}

	public static Errors failingSingleInputDataError(ErrorDetail error, String errorMsg, int errorCode) {
		Errors errors = new Errors(ErrorCodesAndMessages.ERROR_IN_VALIDATION_OF_REQUEST_BODY, errorMsg, errorCode);
		ErrorDetail[] errorDetail = new ErrorDetail[1];
		errorDetail[0] = error;
		errors.setDetails(Arrays.asList(errorDetail));
		return errors;
	}

	public static Errors getInvalidMachineId(TargetTypes targetTypes) {
		return new Errors(targetTypes.name(), ErrorCodesAndMessages.QUERY_CRITERIA_MACHINE_ID,
				ErrorCodesAndMessages.QUERY_CRITERIA_MACHINE_ID_ERROR_CODE);
	}

	public static Errors getInvalidBaseTime(TargetTypes targetTypes, Object object) {
		return new Errors(targetTypes.name(), ErrorCodesAndMessages.QUERY_CRITERIA_BASE_TIME + object,
				ErrorCodesAndMessages.QUERY_CRITERIA_BASE_TIME_ERROR_CODE);
	}

	public static Errors getInvalidExceededTimeLimitQueryCriteriaError(TargetTypes targetTypes, Object object) {
		return new Errors(targetTypes.name(), ErrorCodesAndMessages.QUERY_CRITERIA_EXCEEDED_TIME_LIMIT + object,
				ErrorCodesAndMessages.QUERY_CRITERIA_EXCEEDED_TIME_LIMIT_ERROR_CODE);
	}

	public static Errors getInvalidSequenceId(TargetTypes targetTypes, Object object) {
		return new Errors(targetTypes.name(), ErrorCodesAndMessages.QUERY_CRITERIA_SEQUENCE_ID + object,
				ErrorCodesAndMessages.QUERY_CRITERIA_SEQUENCE_ID_ERROR_CODE);
	}

	public static Errors interruptionOccurredDuringThread(TargetTypes targetTypes) {
		return new Errors(targetTypes.name(), ErrorCodesAndMessages.QUERY_CRITERIA_INTERRUPTION_OCCURRED_DURING_THREAD,
				ErrorCodesAndMessages.QUERY_CRITERIA_INTERRUPTION_OCCURRED_DURING_THREAD_ERROR_CODE);
	}

	public static Object errorForInvalidUsername() {
		return new Errors(TargetTypes.INVALID_USERNAME.name(), ErrorCodesAndMessages.INVALID_USERNAME_ERROR_MESSAGE,
				ErrorCodesAndMessages.INVALID_USERNAME_ERROR_CODE);
	}

	public static Errors getInvalidDataInRequestError(TargetTypes targetTypes, ErrorDetail errorDetail) {

		Errors errors = new Errors(targetTypes.name(), ErrorCodesAndMessages.INPUT_DATA_IS_INCORRECT, 100);
		errors.setDetails(Collections.singletonList(errorDetail));
		return errors;
	}

}
