package com.integration.auth.service.session;

public interface SessionService {

	void saveSessionDetails(String sharedSessionToken, String sharedRefreshToken);

	void refreshSessionDetails(String generatedSessionToken, String generatedRefreshToken, String existingRefreshToken);

	void terminateSession(String sharedSessionToken);

	void invalidateSessions(String username, String tenantId, String terminationReason);

}
