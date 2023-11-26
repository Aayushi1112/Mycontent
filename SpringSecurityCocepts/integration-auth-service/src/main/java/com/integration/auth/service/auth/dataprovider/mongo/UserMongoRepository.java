package com.integration.auth.service.auth.dataprovider.mongo;

import com.integration.auth.service.auth.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserMongoRepository extends MongoRepository<User, String> {

	User findByUsername(String username);

	Boolean existsByUsernameAndTenantId(String username, String tenantId);

	User findByUsernameAndTenantId(String username, String tenandId);

	User save(User user);

}
