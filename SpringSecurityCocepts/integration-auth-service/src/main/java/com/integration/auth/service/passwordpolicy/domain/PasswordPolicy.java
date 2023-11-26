package com.integration.auth.service.passwordpolicy.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

class tenaantPwdPolic {

	String tenantId;

	PasswordPolicy passwordPolicy;

}


@Document("PasswordPolicy")
public class PasswordPolicy {

	@Id
	String tenantId;

	 // first login after register

	private Boolean forcePasswordChangeAtRegularIntervals; // reset, change, register

	private int daysToChangePassword;

	private boolean sendEmailPriorToChangePassword;

	private int daysBeforeEmailReminder;

	private int minPasswordLength; // reset, change, register

	private int maxPasswordLength; // reset, change, register

	private boolean allowCapitalAndSpecialCharacters; // reset, change, register

	private int minSpecialCharacters; // reset, change, register

	private int minCapitalCharacters; // reset, change, register

	private int minDigits; // reset, change, register

	private int noOfPreviousPasswords; // reset, change

	private boolean lockUserAfterInvalidAttempts; // login, change?

	private int maxInvalidLoginAttempts; // login

	private int lockTimePeriodMinutes; // login

	private boolean multiFactorAuthentication;

	private boolean forcePasswordChangeOnFirstLogin;
	public boolean getForcePasswordChangeOnFirstLogin() {
		return forcePasswordChangeOnFirstLogin;
	}


	public void setForcePasswordChangeOnFirstLogin(boolean forcePasswordChangeOnFirstLogin) {
		this.forcePasswordChangeOnFirstLogin = forcePasswordChangeOnFirstLogin;
	}

	public String getTenantId() {
		return tenantId;
	}


	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}


	


	public void setisForcePasswordChangeAtRegularIntervals(boolean forcePasswordChangeAtRegularIntervals) {
		this.forcePasswordChangeAtRegularIntervals=forcePasswordChangeAtRegularIntervals;
	}


	public void setForcePasswordChangeAtRegularIntervals(boolean forcePasswordChangeAtRegularIntervals) {
		this.forcePasswordChangeAtRegularIntervals = forcePasswordChangeAtRegularIntervals;
	}


	public int getDaysToChangePassword() {
		return daysToChangePassword;
	}


	public void setDaysToChangePassword(int daysToChangePassword) {
		this.daysToChangePassword = daysToChangePassword;
	}


	public boolean isSendEmailPriorToChangePassword() {
		return sendEmailPriorToChangePassword;
	}


	public void setSendEmailPriorToChangePassword(boolean sendEmailPriorToChangePassword) {
		this.sendEmailPriorToChangePassword = sendEmailPriorToChangePassword;
	}


	public int getDaysBeforeEmailReminder() {
		return daysBeforeEmailReminder;
	}


	public void setDaysBeforeEmailReminder(int daysBeforeEmailReminder) {
		this.daysBeforeEmailReminder = daysBeforeEmailReminder;
	}


	public int getMinPasswordLength() {
		return minPasswordLength;
	}


	public void setMinPasswordLength(int minPasswordLength) {
		this.minPasswordLength = minPasswordLength;
	}


	public int getMaxPasswordLength() {
		return maxPasswordLength;
	}


	public void setMaxPasswordLength(int maxPasswordLength) {
		this.maxPasswordLength = maxPasswordLength;
	}


	public boolean isAllowCapitalAndSpecialCharacters() {
		return allowCapitalAndSpecialCharacters;
	}


	public void setAllowCapitalAndSpecialCharacters(boolean allowCapitalAndSpecialCharacters) {
		this.allowCapitalAndSpecialCharacters = allowCapitalAndSpecialCharacters;
	}


	public int getMinSpecialCharacters() {
		return minSpecialCharacters;
	}


	public void setMinSpecialCharacters(int minSpecialCharacters) {
		this.minSpecialCharacters = minSpecialCharacters;
	}


	public int getMinCapitalCharacters() {
		return minCapitalCharacters;
	}


	public void setMinCapitalCharacters(int minCapitalCharacters) {
		this.minCapitalCharacters = minCapitalCharacters;
	}


	public int getMinDigits() {
		return minDigits;
	}


	public void setMinDigits(int minDigits) {
		this.minDigits = minDigits;
	}


	public int getNoOfPreviousPasswords() {
		return noOfPreviousPasswords;
	}


	public void setNoOfPreviousPasswords(int noOfPreviousPasswords) {
		this.noOfPreviousPasswords = noOfPreviousPasswords;
	}


	public boolean isLockUserAfterInvalidAttempts() {
		return lockUserAfterInvalidAttempts;
	}


	public void setLockUserAfterInvalidAttempts(boolean lockUserAfterInvalidAttempts) {
		this.lockUserAfterInvalidAttempts = lockUserAfterInvalidAttempts;
	}


	public int getMaxInvalidLoginAttempts() {
		return maxInvalidLoginAttempts;
	}


	public void setMaxInvalidLoginAttempts(int maxInvalidLoginAttempts) {
		this.maxInvalidLoginAttempts = maxInvalidLoginAttempts;
	}


	public int getLockTimePeriodMinutes() {
		return lockTimePeriodMinutes;
	}


	public void setLockTimePeriodMinutes(int lockTimePeriodMinutes) {
		this.lockTimePeriodMinutes = lockTimePeriodMinutes;
	}


	public boolean isMultiFactorAuthentication() {
		return multiFactorAuthentication;
	}


	public void setMultiFactorAuthentication(boolean multiFactorAuthentication) {
		this.multiFactorAuthentication = multiFactorAuthentication;
	}


	public PasswordPolicy() {
		super();
		
	}
	

//	public PasswordPolicy(String tenantId, boolean forcePasswordChangeOnFirstLogin,
//			boolean forcePasswordChangeAtRegularIntervals, int daysToChangePassword,
//			boolean sendEmailPriorToChangePassword, int daysBeforeEmailReminder, int minPasswordLength,
//			int maxPasswordLength, boolean allowCapitalAndSpecialCharacters, int minSpecialCharacters,
//			int minCapitalCharacters, int minDigits, int noOfPreviousPasswords, boolean lockUserAfterInvalidAttempts,
//			int maxInvalidLoginAttempts, int lockTimePeriodMinutes, boolean multiFactorAuthentication) {
//		super();
//		this.tenantId = tenantId;
//		this.forcePasswordChangeOnFirstLogin = forcePasswordChangeOnFirstLogin;
//		this.forcePasswordChangeAtRegularIntervals = forcePasswordChangeAtRegularIntervals;
//		this.daysToChangePassword = daysToChangePassword;
//		this.sendEmailPriorToChangePassword = sendEmailPriorToChangePassword;
//		this.daysBeforeEmailReminder = daysBeforeEmailReminder;
//		this.minPasswordLength = minPasswordLength;
//		this.maxPasswordLength = maxPasswordLength;
//		this.allowCapitalAndSpecialCharacters = allowCapitalAndSpecialCharacters;
//		this.minSpecialCharacters = minSpecialCharacters;
//		this.minCapitalCharacters = minCapitalCharacters;
//		this.minDigits = minDigits;
//		this.noOfPreviousPasswords = noOfPreviousPasswords;
//		this.lockUserAfterInvalidAttempts = lockUserAfterInvalidAttempts;
//		this.maxInvalidLoginAttempts = maxInvalidLoginAttempts;
//		this.lockTimePeriodMinutes = lockTimePeriodMinutes;
//		this.multiFactorAuthentication = multiFactorAuthentication;
//	}

	
	
}
