package com.integration.auth.entrypoint.dto;

import lombok.Data;

@Data
public class LogoutRequest {

	private String username;

	private String tenantId;

	private String terminationReason;

}
