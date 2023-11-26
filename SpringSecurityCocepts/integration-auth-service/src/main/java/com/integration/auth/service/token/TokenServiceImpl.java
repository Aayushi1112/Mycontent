package com.integration.auth.service.token;

import com.integration.auth.common.errors.ErrorCodesAndMessages;
import com.integration.auth.common.errors.Errors;
import com.integration.auth.common.responses.ApiResponse;
import com.integration.auth.common.responses.AuthResponse;
import com.integration.auth.common.util.CommonUtil;
import com.integration.auth.common.util.DateTimeManager;
import com.integration.auth.common.util.TargetTypes;
import com.integration.auth.service.auth.exceptions.AuthenticationException;
import com.integration.auth.service.common.JWTHelper;
import com.integration.auth.service.session.SessionService;
import com.integration.auth.service.session.domain.UserSessionDetails;
import com.integration.auth.service.session.domain.repository.UserSessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.integration.auth.common.responses.ResponseBuilder.buildAPiResponse;
import static com.integration.auth.common.responses.ResponseBuilder.buildResponse;
import static com.integration.auth.common.util.Constants.*;

@Service
public class TokenServiceImpl implements TokenService {

	@Autowired
	JWTHelper jwtHelper;

	@Autowired
	TokenGeneratorService tokenGenerator;

	@Autowired
	SessionService sessionService;

	@Autowired
	CommonUtil commonUtil;

	@Autowired
	UserSessionRepository userSessionRepository;

	private static final Logger log = LoggerFactory.getLogger(TokenServiceImpl.class);

	public AuthResponse refreshToken(String existingRefreshToken, String userId, String tenantId) {
		log.info(MARKER + "Inside refreshToken Method Starts for user {}", userId);
		TokenObjects tokenObjects = getTokenObjects(userId, tenantId, existingRefreshToken);
		String username = jwtHelper.getUserNameFromJwtToken(existingRefreshToken);
		String generatedSessionToken = tokenGenerator.createSessionToken(username,
				getTokenObjects(username, tokenObjects));
		String generatedRefreshToken = tokenGenerator.createRefreshToken(username,
				getTokenObjects(username, tokenObjects));
		sessionService.refreshSessionDetails(generatedSessionToken, generatedRefreshToken, existingRefreshToken);
		log.info(MARKER + "Inside refreshToken Method ENDS for user {} ", userId);
		return buildResponse(generatedSessionToken, generatedRefreshToken);
	}

	private TokenObjects getTokenObjects(String userId, String tenantId, String existingRefreshToken) {
		String sourceIp = jwtHelper.getSourceIpFromToken(existingRefreshToken);
		String loginType = jwtHelper.getLoginTypeFromToken(existingRefreshToken);
		String userDevice = jwtHelper.getDeviceFromToken(existingRefreshToken);

		return new TokenObjects(userId, tenantId, sourceIp, loginType, userDevice);
	}

	private Map<String, String> getTokenObjects(String username, TokenObjects tokenObjects) {
		Map<String, String> claims = new HashMap<>();
		claims.put(USERNAME, username);
		claims.put(TENANT_ID, tokenObjects.getTenantId());
		claims.put(CREATION_DATE, DateTimeManager.currentTimeStampDateInUTC().toString());
		claims.put(JTI, UUID.randomUUID().toString());
		claims.put(SOURCE_IP, tokenObjects.getSourceIp());
		claims.put(LOGIN_TYPE, tokenObjects.getLoginType());
		claims.put(DEVICE, tokenObjects.getDevice());
		return claims;
	}

	public ApiResponse validateToken(String token, String userId) {
		log.info(MARKER + "Inside validateToken method Starts for user {}", userId);
		boolean isTokenValid = checkTokenValidation(token);

		if (!isTokenValid) {
			throw new AuthenticationException(new Errors(TargetTypes.VALIDATE_TOKEN.name(),
					ErrorCodesAndMessages.INVALID_TOKEN, ErrorCodesAndMessages.INVALID_AUTHORIZATION));
		}
		log.info(MARKER + "Inside validateToken method Ends for user {}", userId);
		return buildAPiResponse(USER_VALIDATED, userId);
	}

	private boolean checkTokenValidation(String token) {
		String userNameFromJwtToken = jwtHelper.getUserNameFromJwtToken(token);
		Optional<UserSessionDetails> sessionDetails = userSessionRepository.fetchUserBySessionTokenAndUserName(token,
				userNameFromJwtToken);
		UserSessionDetails userSession = sessionDetails.orElse(null);
		if (userSession != null && !userSession.isTerminated()) {
			userSession.setLastAccessed(DateTimeManager.currentTimeStampDateInUTC());
			userSession.setLastAccessedBySystem(commonUtil.getPaSystem());
			userSessionRepository.storeUserSession(userSession);
			return true;
		}
		else {
			return false;
		}
	}

}
