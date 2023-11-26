package com.integration.auth.service.usermanagement;

import com.integration.auth.common.responses.ApiResponse;
import com.integration.auth.entrypoint.dto.DeleteUserInputRequest;
import com.integration.auth.entrypoint.dto.PasswordChangeRequest;
import com.integration.auth.entrypoint.dto.PasswordResetRequest;

public interface UserManagementService {

	ApiResponse toggleActivation(String username, String status, String tenantId);

	ApiResponse changePassword(PasswordChangeRequest passwordChangeRequest);

	ApiResponse resetPassword(PasswordResetRequest pswdResetRequest);

	ApiResponse deleteUser(DeleteUserInputRequest inputRequest);

}
