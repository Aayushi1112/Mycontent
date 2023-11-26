package com.integration.auth.common.util;

public enum AccountStatus {

	ACTIVE("active"), INACTIVE("inactive");

	private final String status;

	AccountStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

}
