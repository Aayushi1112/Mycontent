package com.integration.auth.entrypoint.dto;

import lombok.Data;

@Data
public class PasswordResetRequest {

	private String username;

	private String newPassword;

	private String tenantId;

	private String journeyType;

}
