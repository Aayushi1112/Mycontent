package com.integration.auth.entrypoint.response;

import com.integration.auth.common.responses.ApiResponse;
import com.integration.auth.common.responses.AuthResponse;
import com.integration.auth.common.responses.ResponseComposer;
import com.integration.auth.common.responses.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

public class AuthResponseComposer {

	private AuthResponseComposer() {
	}

	public static ResponseEntity<String> composeAuthResponse(AuthResponse authResponse) {
		RestResponse<AuthResponse> response = ResponseComposer
				.createRestResponse(Collections.singletonList(authResponse));
		return ResponseEntity.ok(ResponseComposer.createJSONStringForResponse(response));
	}

	public static ResponseEntity<String> composeResponse(ApiResponse apiResponse) {
		RestResponse<ApiResponse> response = ResponseComposer
				.createRestResponse(Collections.singletonList(apiResponse));
		return ResponseEntity.ok(ResponseComposer.createJSONStringForResponse(response));
	}

	public static ResponseEntity<String> composeRegisterResponse(ApiResponse apiResponse) {
		RestResponse<ApiResponse> response = ResponseComposer
				.createRestResponse(Collections.singletonList(apiResponse));
		return ResponseEntity.status(HttpStatus.CREATED).body(ResponseComposer.createJSONStringForResponse(response));
	}

	public static ResponseEntity<String> composeActivationResponse(ApiResponse apiResponse) {
		RestResponse<ApiResponse> response = ResponseComposer
				.createRestResponse(Collections.singletonList(apiResponse));
		return ResponseEntity.status(HttpStatus.NO_CONTENT)
				.body(ResponseComposer.createJSONStringForResponse(response));
	}

}
