package com.integration.auth.common.errors;

/**
 * This clas contains validation and error code messages.
 */
public class ErrorCodesAndMessages {

	private ErrorCodesAndMessages() {
	}

	// generic error
	public static final String INVALID_USERNAME_ERROR_MESSAGE = "Invalid username provided";

	public static final int INVALID_USERNAME_ERROR_CODE = 18001;

	public static final String USER_EXISTS_ERROR_MESSAGE = "User provided is already present in database ";

	public static final int USER_EXISTS_ERROR_CODE = 18002;

	public static final String PASSWORD_ENCODING_ERROR_MESSAGE = "Error while encoding password";

	public static final int PASSWORD_ENCODING_ERROR_CODE = 18003;

	public static final String PASSWORD_MISMATCH_ERROR_MESSAGE = "Input password does not match with the database ";

	public static final int PASSWORD_MISMATCH_ERROR_CODE = 18004;

	public static final String PASSWORD_MATCHING_ERROR_MESSAGE = "Error while matching password with encoded password ";

	public static final int PASSWORD_MATCHING_ERROR_CODE = 18005;

	public static final String PASSWORD_HASHING_ERROR_MESSAGE = "Error while hashing password with encoded password ";

	public static final int PASSWORD_HASHING_ERROR_CODE = 18006;

	public static final String USER_AUTHENTICATION_ERROR_MESSAGE = "Issue while authenticating user ";

	public static final int USER_AUTHENTICATION_ERROR_CODE = 18007;

	public static final String SALT_KEY_GENERATION_ERROR_MESSAGE = "Error while generating salt key ";

	public static final int SALT_KEY_GENERATION_ERROR_CODE = 18008;

	public static final String USER_NOT_FOUND_ERROR_MESSAGE = "No user found at Database";

	public static final int USER_NOT_FOUND_ERROR_CODE = 18009;

	public static final String INACTIVE_USER_ERROR_MESSAGE = "User account is deleted or deactivated";

	public static final int INACTIVE_USER_ERROR_CODE = 18077;

	public static final int DELETED_USER_ERROR_CODE = 18078;

	public static final String INVALID_PASSWORD_ERROR_MESSAGE = "Invalid input password";

	public static final int INVALID_PASSWORD_ERROR_CODE = 18010;

	public static final String SAME_OLD_AND_NEW_PASSWORD_ERROR_MESSAGE = "New password can not be same as Old password.";

	public static final int SAME_OLD_AND_NEW_PASSWORD_ERROR_CODE = 18022;

	public static final String USER_REGISTRATION_ERROR_MESSAGE = "Error while registering user ";

	public static final int USER_REGISTRATION_ERROR_CODE = 18011;

	public static final String EMPTY_JWT_CLAIMS_ERROR_MSG = "Empty claims received";

	public static final int EMPTY_JWT_CLAIMS_ERROR_CODE = 18012;

	public static final String JWT_EXTRACT_CLAIMS_ERROR = "Faced some error while extracting JWT Claims.";

	public static final int JWT_EXTRACT_CLAIMS_ERROR_CODE = 18013;

	public static final String JWT_CREATION_ERROR_MESSAGE = "Faced some error while creating JWT token.";

	public static final int JWT_CREATION_ERROR_CODE = 18014;

	public static final String JWT_DECODING_ERROR_MESSAGE = "Faced some error while decoding username from JWT token";

	public static final int JWT_DECODING_ERROR_CODE = 18015;

	public static final String JWT_VALIDATION_ERROR_MESSAGE = "Faced issue while validating JWT token ";

	public static final int JWT_VALIDATION_ERROR_CODE = 18016;

	public static final String INVALID_TOKEN = "invalid_token";

	public static final int INVALID_TOKEN_CODE = 18017;

	public static final String INVALID_INPUT_USER_TOKEN_ERROR_MESSAGE = "The userId is not matching with the extracted token  ";

	public static final int INVALID_INPUT_USER_TOKEN_ERROR_CODE = 18018;

	public static final String INVALID_REQUEST_BODY_ERROR_MESSAGE = "Request Body Input is Not Valid. ";

	public static final int INVALID_REQUEST_BODY_ERROR_CODE = 18019;

	public static final String ERROR_IN_VALIDATION_OF_REQUEST_BODY = "Error in  validation of  Request Body";

	public static final String INVALID_PA_SYSTEM_ERROR_MESSAGE = "Invalid PA system input provided in headers";

	public static final int INVALID_PA_SYSTEM_ERROR_CODE = 3001;

	public static final String INVALID_NATIVE_SYSTEM_ERROR_MESSAGE = "Invalid Native system input provided in request";

	public static final int INVALID_NATIVE_SYSTEM_ERROR_CODE = 3011;

	public static final String INVALID_CLIENT_ID_AND_SECRET_ERROR_MESSAGE = "Invalid Client ID and Secret provided";

	public static final int INVALID_CLIENT_ID_AND_SECRET_ERROR_MESSAGE_CODE = 3002;

	public static final String INVALID_TENANT_ID_ERROR_MESSAGE = "Invalid Tenant Id input";

	public static final int INVALID_TENANT_ID_ERROR_CODE = 3003;

	public static final String INVALID_PASSWORD_JOURNEY_TYPE_ERROR_MESSAGE = "Password reset journey type is not present";

	public static final int INVALID_PASSWORD_JOURNEY_TYPE_ERROR_CODE = 3077;

	public static final String MANDATORY_FIELD_ERROR_MSG = "Mandatory Fields are invalid or not present.";

	public static final int MANDATORY_FIELD_ERROR_CODE = 3004;

	public static final String GENERIC_ERROR = "Faced some error while executing request";

	public static final int GENERIC_ERROR_CODE = 3005;

	public static final String INVALID_PA_SYSTEM_TYPE_ERROR_MSG = "Invalid PA system type.";

	public static final int INVALID_PA_SYSTEM_TYPE_ERROR_CODE = 3006;

	public static final String GENERIC_AUTH_FILTER_ERROR = "Faced some error while executing request at Auth Filter. ";

	public static final int GENERIC_AUTH_FILTER_ERROR_CODE = 3007;

	public static final String INPUT_DATA_IS_INCORRECT = "Input data is incorrect or missing";

	public static final String DATABASE_FAILURE_CLIENT_SECRET_DETAILS_RETRIEVAL_ERROR_MSG = "Error Occured while retreving client secret details from database";

	public static final int DATABASE_FAILURE_CLIENT_SECRET_DETAILS_RETRIEVAL_ERROR_CODE = 46001;

	public static final String DATABASE_FAILURE_USER_SESSION_CREATE_UPDATE_ERROR_MSG = "Error occured while saving/modifying USER Session data into database.";

	public static final int DATABASE_FAILURE_USER_SESSION_CREATE_UPDATE_ERROR_CODE = 46002;

	public static final String DATABASE_FAILURE_USER_SESSION_RETRIEVAL_ERROR_MSG = "Error occured while retrieving user session  data from database.";

	public static final int DATABASE_FAILURE_USER_SESSION_RETRIEVAL_ERROR_CODE = 46003;

	public static final String INTEGRATION_AUTH_TARGET = "INTEGRATION_AUTH_TARGET";

	public static final String INVALID_TENANT_ID = "INVALID_TENANT_ID";

	public static final String INVALID_PA_SYSTEM = "INVALID_PA_SYSTEM";

	public static final String DATABASE_FAILURE_USER_REGISTER_ERROR_MSG = "Error occured while saving USER Register data into database.";

	public static final int DATABASE_FAILURE_USER_REGISTER_ERROR_CODE = 46004;

	public static final String DATABASE_FAILURE_USER_DETAILS_RETRIEVAL_ERROR_MSG = "Error occured while retrieving user details  data from database.";

	public static final int DATABASE_FAILURE_USER_DETAILS_RETRIEVAL_ERROR_CODE = 46005;

	public static final int INVALID_AUTHORIZATION = 18020;

	public static final String INVALID_INPUT_TOKEN = "Invalid refresh token";

	public static final int INVALID_INPUT_TOKEN_ERROR_CODE = 18021;

	public static final String INVALID_USER_NAME = "Invalid user name";

	public static final String INVALID_PASSWORD = "Invalid password";

	public static final String VALIDATION_ERROR_MSG = "Validation failure";

	public static final String QUERY_CRITERIA_MACHINE_ID = "Failed to process machine id :: ";

	public static final int QUERY_CRITERIA_MACHINE_ID_ERROR_CODE = 46006;

	public static final String QUERY_CRITERIA_BASE_TIME = "Base time should be after 1970-01-01T00:00:00Z, or before current time :: ";

	public static final int QUERY_CRITERIA_BASE_TIME_ERROR_CODE = 46007;

	public static final String QUERY_CRITERIA_EXCEEDED_TIME_LIMIT = "Exceeded the time limit :: ";

	public static final int QUERY_CRITERIA_EXCEEDED_TIME_LIMIT_ERROR_CODE = 46008;

	public static final String QUERY_CRITERIA_SEQUENCE_ID = "Failed to issue sequence id :: ";

	public static final int QUERY_CRITERIA_SEQUENCE_ID_ERROR_CODE = 46009;

	public static final String QUERY_CRITERIA_INTERRUPTION_OCCURRED_DURING_THREAD = "Interruption occurred during thread sleep :: ";

	public static final int QUERY_CRITERIA_INTERRUPTION_OCCURRED_DURING_THREAD_ERROR_CODE = 46010;

	public static final String INVALID_INPUT_ACTIVATION_STATUS_ERROR_MESSAGE = "Invalid input status from request body :: ";

	public static final int INVALID_INPUT_ACTIVATION_STATUS_ERROR_CODE = 8001;

	public static final String CONFLICT_USER_ACTIVATION_STATUS_ERROR = "User activation status is already in same state";

	public static final int CONFLICT_USER_ACTIVATION_STATUS_ERROR_CODE = 8002;

}
