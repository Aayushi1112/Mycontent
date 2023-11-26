package com.integration.auth.service.auth.domain.repository;

import com.integration.auth.service.auth.domain.User;

public interface UserRepository {

	User findByUsername(String username);

	User findByUsernameAndTenantId(String username, String tenandId);

	boolean existsByUsernameAndTenantId(String username, String tenantId);

	User save(User user);

}
