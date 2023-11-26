package com.integration.auth.service.session.dataprovider;

import com.integration.auth.common.errors.ErrorCodesAndMessages;
import com.integration.auth.common.errors.ErrorFactory;
import com.integration.auth.common.errors.Errors;
import com.integration.auth.common.exceptions.InvalidTokenException;
import com.integration.auth.service.session.exceptions.SessionDatabaseFailureException;
import com.integration.auth.common.util.TargetTypes;
import com.integration.auth.service.session.dataprovider.mongo.UserSessionCustomMongoRepository;
import com.integration.auth.service.session.dataprovider.mongo.UserSessionMongoRepository;
import com.integration.auth.service.session.domain.UserSessionDetails;
import com.integration.auth.service.session.domain.repository.UserSessionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserSessionRepositoryImpl implements UserSessionRepository {

	private UserSessionMongoRepository userSessionMongoRepository;

	private UserSessionCustomMongoRepository userSessionCustomMongoRepository;

	public UserSessionRepositoryImpl(UserSessionMongoRepository userSessionMongoRepository,
			UserSessionCustomMongoRepository userSessionCustomMongoRepository) {
		this.userSessionMongoRepository = userSessionMongoRepository;
		this.userSessionCustomMongoRepository = userSessionCustomMongoRepository;

	}

	@Override
	public UserSessionDetails storeUserSession(UserSessionDetails userSessionDetails) {
		try {
			return userSessionMongoRepository.save(userSessionDetails);
		}
		catch (Exception ex) {
			throw new SessionDatabaseFailureException(ex, ErrorFactory.getSessionServiceDatabaseFailureError());
		}

	}

	@Override
	public Optional<UserSessionDetails> fetchUserBySessionTokenAndUserName(String sessionToken, String userName) {
		try {
			return userSessionMongoRepository.findBySessionTokenAndUsername(sessionToken, userName);
		}
		catch (Exception ex) {
			throw new SessionDatabaseFailureException(ex, ErrorFactory.getSessionServiceDatabaseFailureError());
		}
	}

	@Override
	public void terminateUserSession(String sharedToken) {
		try {
			userSessionCustomMongoRepository.terminateSession(sharedToken);
		}
		catch (InvalidTokenException ex) {
			throw new InvalidTokenException(ex, new Errors(TargetTypes.TERMINATE_SESSION.name(),
					ErrorCodesAndMessages.INVALID_TOKEN, ErrorCodesAndMessages.INVALID_TOKEN_CODE));

		}
		catch (Exception ex) {
			throw new SessionDatabaseFailureException(ex,
					new Errors(TargetTypes.TERMINATE_SESSION.name(),
							ErrorCodesAndMessages.DATABASE_FAILURE_USER_SESSION_CREATE_UPDATE_ERROR_MSG,
							ErrorCodesAndMessages.DATABASE_FAILURE_USER_SESSION_CREATE_UPDATE_ERROR_CODE));
		}

	}

	@Override
	public UserSessionDetails refreshUserSession(String generatedSessionToken, String generatedRefreshToken,
			String existingRefreshToken) {

		try {
			return userSessionCustomMongoRepository.refreshSession(generatedSessionToken, generatedRefreshToken,
					existingRefreshToken);
		}
		catch (InvalidTokenException ex) {
			throw new InvalidTokenException(ex, new Errors(TargetTypes.REFRESH_TOKEN.name(),
					ErrorCodesAndMessages.INVALID_TOKEN, ErrorCodesAndMessages.INVALID_TOKEN_CODE));

		}
		catch (Exception ex) {
			throw new SessionDatabaseFailureException(ex,
					new Errors(TargetTypes.REFRESH_TOKEN.name(),
							ErrorCodesAndMessages.DATABASE_FAILURE_USER_SESSION_CREATE_UPDATE_ERROR_MSG,
							ErrorCodesAndMessages.DATABASE_FAILURE_USER_SESSION_CREATE_UPDATE_ERROR_CODE));
		}
	}

	@Override
	public List<UserSessionDetails> invalidateUserActiveSessions(String username, String tenantId) {
		try {
			return userSessionMongoRepository.findByUsernameAndTenantIdAndIsTerminatedFalse(username, tenantId);
		}
		catch (Exception ex) {
			throw new SessionDatabaseFailureException(ex,
					new Errors(TargetTypes.TERMINATE_SESSION.name(),
							ErrorCodesAndMessages.DATABASE_FAILURE_USER_SESSION_CREATE_UPDATE_ERROR_MSG,
							ErrorCodesAndMessages.DATABASE_FAILURE_USER_SESSION_CREATE_UPDATE_ERROR_CODE));
		}
	}

}
