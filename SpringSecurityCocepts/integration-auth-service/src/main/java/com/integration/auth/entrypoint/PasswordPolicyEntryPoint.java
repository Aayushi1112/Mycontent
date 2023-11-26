package com.integration.auth.entrypoint;

import static com.integration.auth.common.util.Constants.LOGIN_API_ERROR;
import static com.integration.auth.common.util.Constants.MARKER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.integration.auth.common.responses.FailureResponseComposer;
import com.integration.auth.service.auth.exceptions.AuthServiceException;
import com.integration.auth.service.passwordpolicy.PasswordPolicyService;
import com.integration.auth.service.passwordpolicy.domain.PasswordPolicy;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController

public class PasswordPolicyEntryPoint {

	@Autowired
	PasswordPolicyService passwordPolicyService;

	@PostMapping(value = "v1/tenant/{tenantId}/passwordPolicy", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PasswordPolicy> addPasswordPolicy(@PathVariable String tenantId,
			@RequestBody PasswordPolicy passwordPolicy) {
		try {
			PasswordPolicy passwordPolicySaved = passwordPolicyService.save(passwordPolicy);
			return new ResponseEntity<PasswordPolicy>(passwordPolicySaved, HttpStatus.CREATED);
		}
		catch (AuthServiceException ex) {
			log.error(MARKER + LOGIN_API_ERROR, ex);
			return new ResponseEntity<PasswordPolicy>(new PasswordPolicy(), HttpStatus.BAD_REQUEST);

		}
	}

	@GetMapping(value = "v1/tenant/{tenantId}/passwordPolicy", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PasswordPolicy> getPasswordPolicy(@PathVariable String tenantId) {
		try {
			PasswordPolicy passwordPolicy = passwordPolicyService.findByTenantId(tenantId);
			return new ResponseEntity<PasswordPolicy>(passwordPolicy, HttpStatus.OK);
		}
		catch (AuthServiceException ex) {
			log.error(MARKER + LOGIN_API_ERROR, ex);
			return new ResponseEntity<PasswordPolicy>(new PasswordPolicy(), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping(value = "v1/tenant/{tenantId}/passwordPolicy", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updatePasswordPolicy(@PathVariable String tenantId,
			@RequestBody PasswordPolicy passwordPolicy) {
		try {
			passwordPolicy.setForcePasswordChangeOnFirstLogin(false);
			return new ResponseEntity<String>(HttpStatus.OK);
		}
		catch (AuthServiceException ex) {
			log.error(MARKER + LOGIN_API_ERROR, ex);
			return FailureResponseComposer.composeFailureResponse(ex);
		}
	}

}
