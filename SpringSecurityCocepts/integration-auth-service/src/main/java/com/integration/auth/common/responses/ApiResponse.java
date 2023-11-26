package com.integration.auth.common.responses;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ApiResponse extends BaseDTO {

	private String message;

	private String user;

}
