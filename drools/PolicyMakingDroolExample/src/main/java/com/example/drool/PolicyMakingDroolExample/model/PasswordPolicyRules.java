package com.example.drool.PolicyMakingDroolExample.model;

import lombok.Data;

@Data
public class PasswordPolicyRules {

	private Boolean forcePasswordChangeOnFirstLogin;

	//private ForcePasswordChange forcePasswordChangeAtRegularIntervals;

	private Integer minPasswordLength;

	private Integer maxPasswordLength;

	private AllowCapitalAndSpecialCharacters allowCapitalAndSpecialCharacters;

	private Integer minDigits;

	private Integer noOfPreviousPasswords;

	//private LockUserAfterInvalidAttempts lockUserAfterInvalidAttempts;

}
