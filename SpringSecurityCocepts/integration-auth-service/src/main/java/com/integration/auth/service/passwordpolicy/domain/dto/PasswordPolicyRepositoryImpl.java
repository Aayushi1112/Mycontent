package com.integration.auth.service.passwordpolicy.domain.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.integration.auth.service.passwordpolicy.domain.PasswordPolicy;
import com.integration.auth.service.passwordpolicy.domain.repository.PasswordPolicyMongoRepository;
import com.integration.auth.service.passwordpolicy.domain.repository.PasswordPolicyRepository;
@Service
public class PasswordPolicyRepositoryImpl implements PasswordPolicyRepository {

	@Autowired
	private PasswordPolicyMongoRepository passwordPolicyMongoRepository;

	@Override
	public PasswordPolicy findByTenantId(String tenantId) {
		PasswordPolicy passwordPolicy = passwordPolicyMongoRepository.findByTenantId(tenantId);
		return passwordPolicy;
	}

	@Override
	public PasswordPolicy save(PasswordPolicy passwordPolicy) {
		PasswordPolicy passwordPolicySaved = passwordPolicyMongoRepository.save(passwordPolicy);
		return passwordPolicySaved;
	}

}
