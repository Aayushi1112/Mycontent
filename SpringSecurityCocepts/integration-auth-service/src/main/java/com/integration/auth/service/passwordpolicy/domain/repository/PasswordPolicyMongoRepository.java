package com.integration.auth.service.passwordpolicy.domain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.integration.auth.service.passwordpolicy.domain.PasswordPolicy;

@Repository
public interface PasswordPolicyMongoRepository extends MongoRepository<PasswordPolicy, String> {

	PasswordPolicy findByTenantId(String tenantId);

	PasswordPolicy save(PasswordPolicy passwordPolicy);

}
