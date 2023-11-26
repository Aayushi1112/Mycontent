package com.integration.auth.entrypoint.dto;

import lombok.Data;

@Data
public class SSOLoginRequest {

	private String username;

	private String tenantId;

}
