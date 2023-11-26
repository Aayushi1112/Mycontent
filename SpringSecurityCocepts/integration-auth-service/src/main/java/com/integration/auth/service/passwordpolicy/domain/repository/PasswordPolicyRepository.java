package com.integration.auth.service.passwordpolicy.domain.repository;

import org.springframework.stereotype.Repository;

import com.integration.auth.service.passwordpolicy.domain.PasswordPolicy;

@Repository
public interface PasswordPolicyRepository {

	PasswordPolicy findByTenantId(String tenantId);

	PasswordPolicy save(PasswordPolicy passwordPolicy);

}
