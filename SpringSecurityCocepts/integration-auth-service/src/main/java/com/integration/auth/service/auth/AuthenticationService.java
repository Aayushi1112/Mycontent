package com.integration.auth.service.auth;

import com.integration.auth.common.responses.ApiResponse;
import com.integration.auth.common.responses.AuthResponse;
import com.integration.auth.entrypoint.dto.LoginRequest;
import com.integration.auth.entrypoint.dto.SSOLoginRequest;
import com.integration.auth.entrypoint.dto.SignupRequest;

public interface AuthenticationService {

	ApiResponse register(SignupRequest signUpRequest, String paSystem);

	AuthResponse processLogin(LoginRequest loginRequest, String paSystem);

	ApiResponse terminateToken(String token, String userId);

	AuthResponse ssoProcessLogin(SSOLoginRequest ssoLoginRequest, String paSystem);

	ApiResponse forceTerminate(String username, String tenantId, String terminationReason);

}
