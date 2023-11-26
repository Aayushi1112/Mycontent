package com.integration.auth.service.passwordpolicy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.integration.auth.service.passwordpolicy.domain.PasswordPolicy;
import com.integration.auth.service.passwordpolicy.domain.repository.PasswordPolicyRepository;

@Service
public class PasswordPolicyServiceImpl implements PasswordPolicyService {

	private PasswordPolicyRepository passwordPolicyRepository;
	
	PasswordPolicyServiceImpl(PasswordPolicyRepository passwordPolicyRepository){
		this.passwordPolicyRepository=passwordPolicyRepository;
	}
	

	@Override
	public PasswordPolicy findByTenantId(String tenantId) {
		PasswordPolicy passwordPolicy = passwordPolicyRepository.findByTenantId(tenantId);
		return passwordPolicy;
	}

	@Override
	public PasswordPolicy save(PasswordPolicy passwordPolicy) {
		PasswordPolicy passwordPolicysaved = passwordPolicyRepository.save(passwordPolicy);
		return passwordPolicysaved;
	}

}
