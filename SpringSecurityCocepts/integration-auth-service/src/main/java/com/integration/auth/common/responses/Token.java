package com.integration.auth.common.responses;

import lombok.Data;

@Data
public class Token {

	private String sessionToken;

	private String refreshToken;

}
