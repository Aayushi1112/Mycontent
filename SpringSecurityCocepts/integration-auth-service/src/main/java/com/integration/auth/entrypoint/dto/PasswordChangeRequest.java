package com.integration.auth.entrypoint.dto;

import lombok.Data;

@Data
public class PasswordChangeRequest {

	private String username;

	private String oldPassword;

	private String newPassword;

	private String confirmPassword;

	private String tenantId;

}
