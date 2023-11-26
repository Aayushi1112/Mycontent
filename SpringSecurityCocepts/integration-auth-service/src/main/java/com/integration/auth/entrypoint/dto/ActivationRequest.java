package com.integration.auth.entrypoint.dto;

import lombok.Data;

@Data
public class ActivationRequest {

	private String username;

	private String status;

	private String tenantId;

}
