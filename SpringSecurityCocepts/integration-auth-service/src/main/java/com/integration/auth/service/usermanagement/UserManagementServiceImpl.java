package com.integration.auth.service.usermanagement;

import com.integration.auth.common.errors.ErrorCodesAndMessages;
import com.integration.auth.common.errors.Errors;
import com.integration.auth.common.responses.ApiResponse;
import com.integration.auth.common.util.Constants;
import com.integration.auth.common.util.DateTimeManager;
import com.integration.auth.common.util.TargetTypes;
import com.integration.auth.entrypoint.dto.DeleteUserInputRequest;
import com.integration.auth.entrypoint.dto.PasswordChangeRequest;
import com.integration.auth.entrypoint.dto.PasswordResetRequest;
import com.integration.auth.service.auth.domain.User;
import com.integration.auth.service.auth.domain.repository.UserRepository;
import com.integration.auth.service.auth.encoder.CustomPasswordEncoder;
import com.integration.auth.service.auth.exceptions.ActivationStatusConflictException;
import com.integration.auth.service.auth.exceptions.InactiveUserException;
import com.integration.auth.service.auth.exceptions.UserNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.integration.auth.common.responses.ResponseBuilder.buildAPiResponse;
import static com.integration.auth.common.util.Constants.*;

@Service
public class UserManagementServiceImpl implements UserManagementService {

	private static final Logger log = LoggerFactory.getLogger(UserManagementServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	CustomPasswordEncoder encoder;

	public ApiResponse toggleActivation(String username, String status, String tenantId) {
		log.info(MARKER + "Inside toggleActivation method for {}", username);
		User user = userRepository.findByUsernameAndTenantId(username, tenantId);

		checkUserExists(user);
		if (StringUtils.equalsIgnoreCase(user.getAccountStatus(), status)) {
			throw new ActivationStatusConflictException(new Errors(TargetTypes.CONFLICT_ACTIVATION_STATUS.name(),
					ErrorCodesAndMessages.CONFLICT_USER_ACTIVATION_STATUS_ERROR,
					ErrorCodesAndMessages.CONFLICT_USER_ACTIVATION_STATUS_ERROR_CODE));
		}
		user.setAccountStatus(status.toLowerCase());
		user.setUpdatedDate(DateTimeManager.currentTimeStampDateInUTC());
		userRepository.save(user);
		return buildAPiResponse("User activation status updated", username);
	}

	@Override
	public ApiResponse changePassword(PasswordChangeRequest passwordChangeRequest) {
		log.info(MARKER + "Inside changePassword Method Starts for username :: {} and tenantId :: {}",
				passwordChangeRequest.getUsername(), passwordChangeRequest.getTenantId());
		User user = userRepository.findByUsernameAndTenantId(passwordChangeRequest.getUsername(),
				passwordChangeRequest.getTenantId());

		checkUserExists(user);
		checkUserActivation(user);

		encoder.matches(passwordChangeRequest.getOldPassword(), user.getPassword());
		user.setPassword(encoder.encode(passwordChangeRequest.getNewPassword()));
		user.setPasswordResetDate(DateTimeManager.currentTimeStampDateInUTC());
		userRepository.save(user);
		log.info(MARKER + "Inside changePassword Method Ends Successfully for username :: {} and tenantId :: {}",
				passwordChangeRequest.getUsername(), passwordChangeRequest.getTenantId());
		return buildAPiResponse(PASSWORD_CHANGE, passwordChangeRequest.getUsername());
	}

	private static void checkUserActivation(User user) {
		if (!StringUtils.equalsIgnoreCase(user.getAccountStatus(), ACTIVE_USER)) {
			throw new InactiveUserException(new Errors(TargetTypes.INACTIVE_USER.name(),
					ErrorCodesAndMessages.INACTIVE_USER_ERROR_MESSAGE, ErrorCodesAndMessages.INACTIVE_USER_ERROR_CODE));
		}
	}

	@Override
	public ApiResponse resetPassword(PasswordResetRequest pswdResetRequest) {
		log.info(MARKER, "this journey consists from " + pswdResetRequest.getJourneyType() + "for user ",
				pswdResetRequest.getUsername());
		User user = userRepository.findByUsernameAndTenantId(pswdResetRequest.getUsername(),
				pswdResetRequest.getTenantId());
		checkUserExists(user);
		checkUserActivation(user);
		user.setPassword(encoder.encode(pswdResetRequest.getNewPassword()));
		user.setPasswordResetDate(DateTimeManager.currentTimeStampDateInUTC());
		userRepository.save(user);
		log.info(MARKER + "resetPassword() method Ends Successfully for username :: {} and tenantId :: {}",
				pswdResetRequest.getUsername(), pswdResetRequest.getTenantId());
		return buildAPiResponse("Password successfully updated for user ", pswdResetRequest.getUsername());
	}

	private static void checkUserExists(User user) {
		if (user == null) {
			throw new UserNotFoundException(
					new Errors(TargetTypes.INVALID_USERNAME.name(), ErrorCodesAndMessages.USER_NOT_FOUND_ERROR_MESSAGE,
							ErrorCodesAndMessages.USER_NOT_FOUND_ERROR_CODE));
		}
	}

	@Override
	public ApiResponse deleteUser(DeleteUserInputRequest inputRequest) {
		log.info(MARKER + "Inside deleteUser method for user {}", inputRequest.getUsername());
		User user = userRepository.findByUsernameAndTenantId(inputRequest.getUsername(), inputRequest.getTenantId());
		if (user == null || StringUtils.equalsIgnoreCase(user.getIsDeleted(), Y)) {
			log.error("No user {} found for input username", inputRequest.getUsername());
			throw new UserNotFoundException(
					new Errors(TargetTypes.INVALID_USERNAME.name(), ErrorCodesAndMessages.USER_NOT_FOUND_ERROR_MESSAGE,
							ErrorCodesAndMessages.USER_NOT_FOUND_ERROR_CODE));
		}
		user.setIsDeleted(Constants.Y);
		userRepository.save(user);
		log.info(MARKER + "User {} got deleted successfully from Database", inputRequest.getUsername());
		return buildAPiResponse("User got deleted successfully from Database", inputRequest.getUsername());
	}

}
