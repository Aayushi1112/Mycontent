package com.integration.auth.service.passwordpolicy;

import org.springframework.stereotype.Service;

import com.integration.auth.service.passwordpolicy.domain.PasswordPolicy;

@Service
public interface PasswordPolicyService {

	PasswordPolicy findByTenantId(String tenantId);

	PasswordPolicy save(PasswordPolicy passwordPolicy);

}
