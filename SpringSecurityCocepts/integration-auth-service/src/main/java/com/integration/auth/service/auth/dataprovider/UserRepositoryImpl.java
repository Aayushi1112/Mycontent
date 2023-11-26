package com.integration.auth.service.auth.dataprovider;

import com.integration.auth.common.errors.ErrorFactory;
import com.integration.auth.service.auth.exceptions.AuthDatabaseFailureException;
import com.integration.auth.service.auth.dataprovider.mongo.UserMongoRepository;
import com.integration.auth.service.auth.domain.User;
import com.integration.auth.service.auth.domain.repository.UserRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

	private UserMongoRepository userMongoRepository;

	public UserRepositoryImpl(UserMongoRepository userMongoRepository) {
		this.userMongoRepository = userMongoRepository;

	}

	@Override
	public User findByUsername(String username) {

		try {
			return userMongoRepository.findByUsername(username);
		}
		catch (Exception ex) {
			throw new AuthDatabaseFailureException(ex, ErrorFactory.getAuthServiceRetrievalDatabaseFailureError());
		}

	}

	@Override
	public User findByUsernameAndTenantId(String username, String tenandId) {
		try {
			return userMongoRepository.findByUsernameAndTenantId(username, tenandId);
		}
		catch (Exception ex) {
			throw new AuthDatabaseFailureException(ex, ErrorFactory.getAuthServiceRetrievalDatabaseFailureError());
		}
	}

	@Override
	public boolean existsByUsernameAndTenantId(String username, String tenantId) {

		try {
			return userMongoRepository.existsByUsernameAndTenantId(username, tenantId);
		}
		catch (Exception ex) {
			throw new AuthDatabaseFailureException(ex, ErrorFactory.getAuthServiceRetrievalDatabaseFailureError());
		}
	}

	@Override
	public User save(User user) {

		try {
			return userMongoRepository.save(user);
		}
		catch (Exception ex) {
			throw new AuthDatabaseFailureException(ex, ErrorFactory.getAuthServiceRetrievalDatabaseFailureError());
		}

	}

}
