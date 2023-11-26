package com.integration.auth.entrypoint.dto;

import lombok.Data;

@Data
public class DeleteUserInputRequest {

	private String username;

	private String tenantId;

}
