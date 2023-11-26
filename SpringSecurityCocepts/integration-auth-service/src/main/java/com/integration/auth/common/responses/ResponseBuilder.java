package com.integration.auth.common.responses;

import com.integration.auth.common.util.Constants;

import java.util.Date;

public class ResponseBuilder {

	private ResponseBuilder() {
	}

	public static AuthResponse buildResponse(String sessionToken, String refreshToken, String nativeSystem,
			Date lastPasswordResetDate) {
		AuthResponse authResponse = new AuthResponse();
		authResponse.setSessionToken(sessionToken);
		authResponse.setRefreshToken(refreshToken);
		authResponse.setTokenType(Constants.BEARER);
		authResponse.setNativeSystem(nativeSystem);
		authResponse.setLastPasswordResetDate(lastPasswordResetDate);
		return authResponse;
	}

	public static AuthResponse buildResponse(String sessionToken, String refreshToken) {
		AuthResponse authResponse = new AuthResponse();
		authResponse.setSessionToken(sessionToken);
		authResponse.setRefreshToken(refreshToken);
		authResponse.setTokenType(Constants.BEARER);
		return authResponse;
	}

	public static ApiResponse buildAPiResponse(String message, String user) {
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setMessage(message);
		apiResponse.setUser(user);
		return apiResponse;
	}

}
