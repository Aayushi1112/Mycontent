package com.myecommerce.exception;

public class BusinessException extends Exception {
	private String errorCode;
	private String errorMessage;
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public BusinessException(String messgae) {
		super();
	}

	public BusinessException() {
		super();// TODO Auto-generated constructor stub
	}

}
