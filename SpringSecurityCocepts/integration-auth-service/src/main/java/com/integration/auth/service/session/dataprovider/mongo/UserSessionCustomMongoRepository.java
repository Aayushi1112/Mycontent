package com.integration.auth.service.session.dataprovider.mongo;

import com.integration.auth.service.session.domain.UserSessionDetails;

public interface UserSessionCustomMongoRepository {

	public void terminateSession(String sharedToken);

	public UserSessionDetails refreshSession(String generatedSessionToken, String generatedRefreshToken,
			String existingRefreshToken);

}
