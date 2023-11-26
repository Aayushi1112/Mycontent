package com.example.drool.PolicyMakingDroolExample.model;

import java.util.Date;
import lombok.Data;

@Data
public class PasswordPolicy {

	private String id;
	private String tenantId;
	private PasswordPolicyRules passwordPolicyRules;
	private Date createdOn;
	private Date updatedOn;
	private String paSystem;
}
