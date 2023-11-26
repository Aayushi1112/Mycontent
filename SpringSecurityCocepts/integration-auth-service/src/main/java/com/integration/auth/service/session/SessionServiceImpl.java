package com.integration.auth.service.session;

import com.integration.auth.common.util.CommonUtil;
import com.integration.auth.common.util.DateTimeManager;
import com.integration.auth.service.session.util.JwtUtil;
import com.integration.auth.service.session.domain.UserSessionDetails;
import com.integration.auth.service.session.domain.repository.UserSessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.integration.auth.common.util.Constants.MARKER;

@Service
public class SessionServiceImpl implements SessionService {

	private static final Logger log = LoggerFactory.getLogger(SessionServiceImpl.class);

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	CommonUtil commonUtil;

	@Autowired
	private UserSessionRepository userSessionRepository;

	public void saveSessionDetails(String sharedSessionToken, String sharedRefreshToken) {
		log.info(MARKER + " ::Calling saveSessionDetails Methods Starts");
		UserSessionDetails sessionDetails = jwtUtil.extractJwtClaims(sharedSessionToken, sharedRefreshToken, null);
		userSessionRepository.storeUserSession(sessionDetails);
		log.info(MARKER + " ::Calling saveSessionDetails Methods Ends");

	}

	public void refreshSessionDetails(String generatedSessionToken, String generatedRefreshToken,
			String existingRefreshToken) {

		log.info(MARKER + "::Calling refreshSessionDetails Methods Starts");
		UserSessionDetails existingUserSessionDetails = userSessionRepository.refreshUserSession(generatedSessionToken,
				generatedSessionToken, existingRefreshToken);
		UserSessionDetails userSessionDetails = jwtUtil.extractJwtClaims(generatedSessionToken, generatedRefreshToken,
				existingUserSessionDetails.getJti());
		userSessionRepository.storeUserSession(userSessionDetails);

		log.info(MARKER + "::Calling refreshSessionDetails Methods Ends");

	}

	public void terminateSession(String sharedSessionToken) {

		log.info(MARKER + "::Calling terminateSession Methods Starts");

		userSessionRepository.terminateUserSession(sharedSessionToken);

		log.info(MARKER + "::Calling terminateSession Methods Ends");
	}

	@Override
	public void invalidateSessions(String username, String tenantId, String terminationReason) {
		log.info(MARKER + "Inside invalidateSession method for user {}", username);

		List<UserSessionDetails> activeSessions = userSessionRepository.invalidateUserActiveSessions(username,
				tenantId);
		activeSessions.forEach(session -> {
			session.setTerminated(true);
			session.setTerminatedBySystem(commonUtil.getPaSystem());
			session.setTerminationDate(DateTimeManager.currentTimeStampDateInUTC());
			session.setLastAccessed(DateTimeManager.currentTimeStampDateInUTC());
			session.setTerminationReason(terminationReason);
			userSessionRepository.storeUserSession(session);
		});
		log.info(MARKER + "All active sessions are terminated for user {}", username);
	}

}
