package com.integration.auth.common.responses;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class AuthResponse extends BaseDTO {

	private String sessionToken;

	private String refreshToken;

	private String tokenType;

	private String expireDate;

	private Date lastPasswordResetDate;

	private String nativeSystem;

}
