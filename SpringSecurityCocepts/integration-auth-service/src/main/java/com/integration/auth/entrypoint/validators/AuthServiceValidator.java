package com.integration.auth.entrypoint.validators;

import com.integration.auth.common.errors.ErrorCodesAndMessages;
import com.integration.auth.common.errors.ErrorDetail;
import com.integration.auth.common.errors.ErrorFactory;
import com.integration.auth.common.errors.Errors;
import com.integration.auth.common.exceptions.InvalidInputDataValidationException;
import com.integration.auth.common.exceptions.ValidationFailureException;
import com.integration.auth.common.util.AccountStatus;
import com.integration.auth.common.util.Constants;
import com.integration.auth.common.util.PaSystem;
import com.integration.auth.common.util.TargetTypes;
import com.integration.auth.entrypoint.dto.LoginRequest;
import com.integration.auth.entrypoint.dto.PasswordChangeRequest;
import com.integration.auth.entrypoint.dto.PasswordResetRequest;
import com.integration.auth.entrypoint.dto.DeleteUserInputRequest;
import com.integration.auth.entrypoint.dto.SSOLoginRequest;
import com.integration.auth.entrypoint.dto.SignupRequest;
import com.integration.auth.entrypoint.dto.LogoutRequest;
import io.vavr.collection.Seq;
import io.vavr.control.Validation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class AuthServiceValidator {

	public void validateRegisterEndpoint(SignupRequest signUpRequest) {
		List<ErrorDetail> validationErrors = validateCommonHeaders(signUpRequest);
		throwHeaderMissingErrorsIfPresent(validationErrors);
	}

	private static void throwHeaderMissingErrorsIfPresent(List<ErrorDetail> validationErrors) {
		if (!isNullOrEmpty(validationErrors)) {
			throw ValidationFailureException.getInstance(buildInvalidOrMissingHeaderError(validationErrors));
		}
	}

	private static Errors buildInvalidOrMissingHeaderError(List<ErrorDetail> validationErrors) {

		log.info("size" + validationErrors.size());
		return new Errors(TargetTypes.INTEGRATION_AUTH_TARGET.name(), ErrorCodesAndMessages.VALIDATION_ERROR_MSG,
				ErrorCodesAndMessages.GENERIC_ERROR_CODE);
	}

	private List<ErrorDetail> validateCommonHeaders(SignupRequest signUpRequest) {
		List<ErrorDetail> validationErrors = new ArrayList<>();
		addErrorIfPresent(validateInputFields(signUpRequest.getUsername(), ErrorCodesAndMessages.INVALID_USER_NAME,
				ErrorCodesAndMessages.GENERIC_ERROR_CODE), validationErrors);
		addErrorIfPresent(validateInputFields(signUpRequest.getPassword(), ErrorCodesAndMessages.INVALID_PASSWORD,
				ErrorCodesAndMessages.GENERIC_ERROR_CODE), validationErrors);
		addErrorIfPresent(validateInputFields(signUpRequest.getTenantId(), ErrorCodesAndMessages.INVALID_TENANT_ID,
				ErrorCodesAndMessages.GENERIC_ERROR_CODE), validationErrors);
		return validationErrors;
	}

	private static void addErrorIfPresent(Optional<ErrorDetail> optionalErrorDetail,
			List<ErrorDetail> validationErrors) {
		optionalErrorDetail.ifPresent(validationErrors::add);
	}

	private Optional<ErrorDetail> validateInputFields(String input, String errorMessge, int appErrorCode) {
		return validateEmpty(input, errorMessge, appErrorCode);
	}

	private Optional<ErrorDetail> validateEmpty(String input, String errorMessage, int appErrorCode) {
		if (isNullOrEmpty(input))
			return Optional
					.of(new ErrorDetail(errorMessage, ErrorCodesAndMessages.INTEGRATION_AUTH_TARGET, appErrorCode));
		return Optional.empty();
	}

	public static boolean isNullOrEmpty(String input) {
		return null == input || input.isEmpty();
	}

	private static boolean isNullOrEmpty(List<ErrorDetail> validationErrors) {
		return null == validationErrors || validationErrors.isEmpty();
	}

	public void validateLoginUserEndpoint(LoginRequest loginRequest) {
		Validation<ErrorDetail, String> validateUsername = validateUsername(loginRequest.getUsername());
		Validation<ErrorDetail, String> validatePassword = validatePassword(loginRequest.getPassword());
		Validation<ErrorDetail, String> validateTenantId = validateTenantId(loginRequest.getTenantId());

		Validation<Seq<ErrorDetail>, Boolean> validation = Validation
				.combine(validateUsername, validatePassword, validateTenantId).ap((v1, v2, v3) -> Boolean.TRUE);

		handleMultipleFieldsValidationFailure(validation);
	}

	public void validateSSOLoginUserEndpoint(SSOLoginRequest ssoLoginRequest) {

		Validation<ErrorDetail, String> validateUsername = validateUsername(ssoLoginRequest.getUsername());
		Validation<ErrorDetail, String> validateTenantId = validateTenantId(ssoLoginRequest.getTenantId());
		Validation<Seq<ErrorDetail>, Boolean> validation = Validation.combine(validateUsername, validateTenantId)
				.ap((v1, v2) -> Boolean.TRUE);

		handleMultipleFieldsValidationFailure(validation);
	}

	private Validation<ErrorDetail, String> validateNativeSystem(String nativeSystem) {
		return (StringUtils.isBlank(nativeSystem) || !isValidNativeEnumValue(nativeSystem))
				? Validation.invalid(new ErrorDetail(TargetTypes.INVALID_NATIVE_SYSTEM.name(),
						ErrorCodesAndMessages.INVALID_NATIVE_SYSTEM_ERROR_MESSAGE,
						ErrorCodesAndMessages.INVALID_NATIVE_SYSTEM_ERROR_CODE))
				: Validation.valid(nativeSystem);
	}

	private boolean isValidNativeEnumValue(String nativeSystem) {
		try {
			PaSystem.valueOf(nativeSystem);
			return true;
		}
		catch (IllegalArgumentException e) {
			return false;
		}
	}

	private Validation<ErrorDetail, String> validateTenantId(String tenantId) {
		return StringUtils.isBlank(tenantId) ? Validation.invalid(new ErrorDetail(TargetTypes.INVALID_TENANT_ID.name(),
				ErrorCodesAndMessages.INVALID_TENANT_ID_ERROR_MESSAGE,
				ErrorCodesAndMessages.INVALID_TENANT_ID_ERROR_CODE)) : Validation.valid(tenantId);
	}

	private Validation<ErrorDetail, String> validatePassword(String password) {
		return StringUtils.isBlank(password)
				? Validation.invalid(new ErrorDetail(TargetTypes.INVALID_PASSWORD.name(),
						ErrorCodesAndMessages.INVALID_PASSWORD, ErrorCodesAndMessages.INVALID_PASSWORD_ERROR_CODE))
				: Validation.valid(password);
	}

	private Validation<ErrorDetail, String> validateUsername(String username) {
		return StringUtils.isBlank(username) ? Validation.invalid(new ErrorDetail(TargetTypes.INVALID_USERNAME.name(),
				ErrorCodesAndMessages.INVALID_USERNAME_ERROR_MESSAGE,
				ErrorCodesAndMessages.INVALID_USERNAME_ERROR_CODE)) : Validation.valid(username);
	}

	private static Validation<ErrorDetail, String> validateActivationStatus(String status) {
		// AccountStatus is an enum
		return (!StringUtils.isBlank(status)
				&& (AccountStatus.ACTIVE.name().toUpperCase().equalsIgnoreCase(status.toUpperCase())
						|| AccountStatus.INACTIVE.name().toUpperCase().equalsIgnoreCase(status.toUpperCase())))
								? Validation.valid(status)
								: Validation.invalid(new ErrorDetail(TargetTypes.INVALID_INPUT_STATUS.name(),
										ErrorCodesAndMessages.INVALID_INPUT_ACTIVATION_STATUS_ERROR_MESSAGE,
										ErrorCodesAndMessages.INVALID_INPUT_ACTIVATION_STATUS_ERROR_CODE));
	}

	protected void handleMultipleFieldsValidationFailure(Validation<Seq<ErrorDetail>, Boolean> validations) {
		if (validations.isInvalid()) {
			throw new InvalidInputDataValidationException(new Exception(Constants.REQUIRED_DATA_NOT_FOUND_OR_INVALID),
					ErrorFactory.failingMultipleInputDataError(validations.getError(),
							ErrorCodesAndMessages.MANDATORY_FIELD_ERROR_MSG,
							ErrorCodesAndMessages.MANDATORY_FIELD_ERROR_CODE));

		}
	}

	public void validateInputToken(String refreshToken, String userId, String tenantId) {
		Validation<ErrorDetail, Boolean> validateRefreshToken = validateRefreshTOken(refreshToken);
		Validation<ErrorDetail, String> validateUsername = validateUsername(userId);
		Validation<ErrorDetail, String> validateTenantId = validateTenantId(tenantId);

		Validation<Seq<ErrorDetail>, Boolean> validation = Validation
				.combine(validateRefreshToken, validateUsername, validateTenantId).ap((v1, v2, v3) -> Boolean.TRUE);

		handleMultipleFieldsValidationFailure(validation);
	}

	private Validation<ErrorDetail, Boolean> validateRefreshTOken(String refreshToken) {
		return StringUtils.isBlank(refreshToken) ? Validation.invalid(new ErrorDetail(TargetTypes.REFRESH_TOKEN.name(),
				ErrorCodesAndMessages.INVALID_INPUT_TOKEN, ErrorCodesAndMessages.INVALID_INPUT_TOKEN_ERROR_CODE))
				: Validation.valid(Boolean.TRUE);
	}

	public void validateRegisterUserEndpoint(SignupRequest signupRequest) {
		Validation<ErrorDetail, String> validateUsername = validateUsername(signupRequest.getUsername());
		Validation<ErrorDetail, String> validatePassword = validatePassword(signupRequest.getPassword());
		Validation<ErrorDetail, String> validateTenantId = validateTenantId(signupRequest.getTenantId());
		Validation<ErrorDetail, String> validateNativeSystem = validateNativeSystem(signupRequest.getNativeSystem());

		Validation<Seq<ErrorDetail>, Boolean> validation = Validation
				.combine(validateUsername, validatePassword, validateTenantId, validateNativeSystem)
				.ap((v1, v2, v3, v4) -> Boolean.TRUE);
		handleMultipleFieldsValidationFailure(validation);
	}

	public void validateRequestInput(String sessionToken, String userId) {
		Validation<ErrorDetail, String> validateSessionToken = validateToken(sessionToken);
		Validation<ErrorDetail, String> validateUserId = validateUsername(userId);

		Validation<Seq<ErrorDetail>, Boolean> validation = Validation.combine(validateSessionToken, validateUserId)
				.ap((v1, v2) -> Boolean.TRUE);

		handleMultipleFieldsValidationFailure(validation);
	}

	public void validateRequestInput(LogoutRequest inputRequest) {
		Validation<ErrorDetail, String> validateUserId = validateUsername(inputRequest.getUsername());
		Validation<ErrorDetail, String> validateTenantId = validateTenantId(inputRequest.getTenantId());
		Validation<Seq<ErrorDetail>, Boolean> validation = Validation.combine(validateUserId, validateTenantId)
				.ap((v1, v2) -> Boolean.TRUE);

		handleMultipleFieldsValidationFailure(validation);
	}

	private Validation<ErrorDetail, String> validateToken(String sessionToken) {
		return StringUtils.isBlank(sessionToken) ? Validation.invalid(new ErrorDetail(TargetTypes.SESSION_TOKEN.name(),
				ErrorCodesAndMessages.INVALID_INPUT_TOKEN, ErrorCodesAndMessages.INVALID_INPUT_TOKEN_ERROR_CODE))
				: Validation.valid(sessionToken);
	}

	private Validation<ErrorDetail, Boolean> validateNewAndConfirmPasswordMatch(String newPassword,
			String confirmPassword) {
		return newPassword == null || !newPassword.equals(confirmPassword)
				? Validation.invalid(new ErrorDetail(TargetTypes.PASSWORD_MATCHING_ERROR.name(),
						ErrorCodesAndMessages.INVALID_PASSWORD_ERROR_MESSAGE,
						ErrorCodesAndMessages.INVALID_PASSWORD_ERROR_CODE))
				: Validation.valid(Boolean.TRUE);

	}

	public void validateChangePasswordRequest(PasswordChangeRequest passwordChangeRequest) {

		Validation<ErrorDetail, String> validateUsername = validateUsername(passwordChangeRequest.getUsername());
		Validation<ErrorDetail, String> validateOldPassword = validatePassword(passwordChangeRequest.getOldPassword());
		Validation<ErrorDetail, String> validateNewPassword = validatePassword(passwordChangeRequest.getNewPassword());
		Validation<ErrorDetail, String> validateConfirmPassword = validatePassword(
				passwordChangeRequest.getConfirmPassword());
		Validation<ErrorDetail, Boolean> validateSameOldAndNewPassword = validateSameOldAndNewPassword(
				passwordChangeRequest.getOldPassword(), passwordChangeRequest.getNewPassword());
		Validation<ErrorDetail, Boolean> validateNewAndConfirmPassword = validateNewAndConfirmPasswordMatch(
				passwordChangeRequest.getNewPassword(), passwordChangeRequest.getConfirmPassword());
		Validation<ErrorDetail, String> validateTenantId = validateTenantId(passwordChangeRequest.getTenantId());

		Validation<Seq<ErrorDetail>, Boolean> validation = Validation
				.combine(validateUsername, validateOldPassword, validateNewPassword, validateConfirmPassword,
						validateSameOldAndNewPassword, validateNewAndConfirmPassword, validateTenantId)
				.ap((v1, v2, v3, v4, v5, v6, v7) -> Boolean.TRUE);

		handleMultipleFieldsValidationFailure(validation);
	}

	private Validation<ErrorDetail, Boolean> validateSameOldAndNewPassword(String oldPassword, String newPassword) {
		return StringUtils.equals(oldPassword, newPassword)
				? Validation.invalid(new ErrorDetail(TargetTypes.PASSWORD_MATCHING_ERROR.name(),
						ErrorCodesAndMessages.SAME_OLD_AND_NEW_PASSWORD_ERROR_MESSAGE,
						ErrorCodesAndMessages.SAME_OLD_AND_NEW_PASSWORD_ERROR_CODE))
				: Validation.valid(Boolean.TRUE);
	}

	public void validateUserActivationRequest(String username, String status, String tenantId) {
		Validation<ErrorDetail, String> validateUsername = validateUsername(username);
		Validation<ErrorDetail, String> validateStatus = validateActivationStatus(status);
		Validation<ErrorDetail, String> validateTenantId = validateTenantId(tenantId);

		Validation<Seq<ErrorDetail>, Boolean> validation = Validation
				.combine(validateUsername, validateStatus, validateTenantId).ap((v1, v2, v3) -> Boolean.TRUE);

		handleMultipleFieldsValidationFailure(validation);
	}

	public void validateResetPasswordRequest(PasswordResetRequest passwordResetRequest) {

		Validation<ErrorDetail, String> validateUsername = validateUsername(passwordResetRequest.getUsername());
		Validation<ErrorDetail, String> validateTenantId = validateTenantId(passwordResetRequest.getTenantId());
		Validation<ErrorDetail, String> validateNewPassword = validatePassword(passwordResetRequest.getNewPassword());
		Validation<ErrorDetail, String> validateJourneyType = validateJourneyType(
				passwordResetRequest.getJourneyType());
		Validation<Seq<ErrorDetail>, Boolean> validation = Validation
				.combine(validateUsername, validateTenantId, validateNewPassword, validateJourneyType)
				.ap((v1, v2, v3, v4) -> Boolean.TRUE);

		handleMultipleFieldsValidationFailure(validation);
	}

	private Validation<ErrorDetail, String> validateJourneyType(String journeyType) {
		return StringUtils.isBlank(journeyType)
				? Validation.invalid(new ErrorDetail(TargetTypes.PASSWORD_JOURNEY_TYPE.name(),
						ErrorCodesAndMessages.INVALID_PASSWORD_JOURNEY_TYPE_ERROR_MESSAGE,
						ErrorCodesAndMessages.INVALID_PASSWORD_JOURNEY_TYPE_ERROR_CODE))
				: Validation.valid(journeyType);
	}

	public void validateDeleteUserInputRequest(DeleteUserInputRequest inputRequest) {
		Validation<ErrorDetail, String> validateUsername = validateUsername(inputRequest.getUsername());
		Validation<ErrorDetail, String> validateTenantId = validateTenantId(inputRequest.getTenantId());
		Validation<Seq<ErrorDetail>, Boolean> validation = Validation.combine(validateUsername, validateTenantId)
				.ap((v1, v2) -> Boolean.TRUE);

		handleMultipleFieldsValidationFailure(validation);
	}

}
