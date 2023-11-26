package com.integration.auth.service.session.domain.repository;

import com.integration.auth.service.session.domain.UserSessionDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserSessionRepository {

	public UserSessionDetails storeUserSession(UserSessionDetails userSessionDetails);

	public Optional<UserSessionDetails> fetchUserBySessionTokenAndUserName(String sessionToken, String userName);

	public void terminateUserSession(String sharedToken);

	public UserSessionDetails refreshUserSession(String generatedSessionToken, String generatedRefreshToken,
			String existingRefreshToken);

	List<UserSessionDetails> invalidateUserActiveSessions(String username, String tenantId);

}
