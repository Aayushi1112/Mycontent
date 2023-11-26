package com.integration.auth.service.token;

import com.integration.auth.common.responses.ApiResponse;
import com.integration.auth.common.responses.AuthResponse;

public interface TokenService {

	AuthResponse refreshToken(String refreshToken, String userId, String tenantId);

	ApiResponse validateToken(String token, String userId);

}
