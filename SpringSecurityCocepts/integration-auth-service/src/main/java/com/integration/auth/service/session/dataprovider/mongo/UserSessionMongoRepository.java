package com.integration.auth.service.session.dataprovider.mongo;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.integration.auth.service.session.domain.UserSessionDetails;

@Repository
public interface UserSessionMongoRepository extends MongoRepository<UserSessionDetails, String> {

	void deleteById(String id);

	UserSessionDetails save(UserSessionDetails userSessionDetails);

	Optional<UserSessionDetails> findBySessionTokenAndUsername(String sessionToken, String username);

	List<UserSessionDetails> findByUsernameAndTenantIdAndIsTerminatedFalse(String username, String tenantId);

}
