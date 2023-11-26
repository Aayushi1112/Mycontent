package com.integration.auth.service.token;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenObjects {

	private String userId;

	private String tenantId;

	private String sourceIp;

	private String loginType;

	private String device;

}
