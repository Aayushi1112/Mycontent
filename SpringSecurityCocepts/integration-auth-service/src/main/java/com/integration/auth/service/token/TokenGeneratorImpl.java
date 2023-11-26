package com.integration.auth.service.token;

import com.integration.auth.common.errors.ErrorCodesAndMessages;
import com.integration.auth.common.errors.Errors;
import com.integration.auth.common.exceptions.InvalidInputDataValidationException;
import com.integration.auth.common.util.CommonUtil;
import com.integration.auth.common.util.Constants;
import com.integration.auth.common.util.DateTimeManager;
import com.integration.auth.common.responses.Token;
import com.integration.auth.common.util.TargetTypes;
import com.integration.auth.entrypoint.dto.LoginRequest;
import com.integration.auth.entrypoint.dto.SSOLoginRequest;
import com.integration.auth.service.common.JWTHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.integration.auth.common.util.Constants.*;

@Slf4j
@Service
public class TokenGeneratorImpl implements TokenGeneratorService {

	@Autowired
	JWTHelper jwtHelper;

	@Autowired
	CommonUtil commonUtil;

	@Value("${ias.jwt.expirationInHours.session}")
	private int sessionTokenExpirationInHours;

	@Value("${ias.jwt.expirationInHours.refresh}")
	private int refreshTokenExpirationInHours;

	@Override
	public String createSessionToken(String username, Map<String, String> claims) {
		log.info(MARKER + "Inside method to create refresh token for user {}", username);
		return jwtHelper.generateJwtToken(username, claims, sessionTokenExpirationInHours);
	}

	@Override
	public String createRefreshToken(String username, Map<String, String> claims) {
		log.info(MARKER + "Inside method to create refresh token for user {}", username);
		return jwtHelper.generateJwtToken(username, claims, refreshTokenExpirationInHours);
	}

	@Override
	public Token fetchTokenDetails(Object request, String paSystem, String loginType) {
		Token token = new Token();
		if (request instanceof LoginRequest loginRequest) {
			Map<String, String> tokenObjects = getTokenObjects(loginRequest, paSystem, loginType);
			String sessionToken = createSessionToken(loginRequest.getUsername(), tokenObjects);
			String refreshToken = createRefreshToken(loginRequest.getUsername(), tokenObjects);
			token.setSessionToken(sessionToken);
			token.setRefreshToken(refreshToken);
			return token;
		}
		else if (request instanceof SSOLoginRequest ssoLoginRequest) {
			Map<String, String> tokenObjects = getTokenObjects(ssoLoginRequest, paSystem, loginType);
			String sessionToken = createSessionToken(ssoLoginRequest.getUsername(), tokenObjects);
			String refreshToken = createRefreshToken(ssoLoginRequest.getUsername(), tokenObjects);
			token.setSessionToken(sessionToken);
			token.setRefreshToken(refreshToken);
			return token;
		}
		else {
			throw new InvalidInputDataValidationException(new Errors(TargetTypes.INVALID_REQUEST_BODY.name(),
					ErrorCodesAndMessages.INVALID_REQUEST_BODY_ERROR_MESSAGE,
					ErrorCodesAndMessages.INVALID_REQUEST_BODY_ERROR_CODE));
		}
	}

	private Map<String, String> getTokenObjects(Object request, String paSystem, String loginType) {
		Map<String, String> claims = new HashMap<>();
		if (request instanceof LoginRequest loginRequest) {
			claims.put(USERNAME, loginRequest.getUsername());
			claims.put(TENANT_ID, loginRequest.getTenantId());
			if (StringUtils.isNotEmpty(commonUtil.getDevice())) {
				claims.put(DEVICE, commonUtil.getDevice());
			}
			else {
				claims.put(DEVICE, null);
			}
		}
		else if (request instanceof SSOLoginRequest ssoLoginRequest) {
			claims.put(USERNAME, ssoLoginRequest.getUsername());
			claims.put(TENANT_ID, ssoLoginRequest.getTenantId());
			if (StringUtils.isNotEmpty(commonUtil.getDevice())) {
				claims.put(DEVICE, commonUtil.getDevice());
			}
			else {
				claims.put(DEVICE, null);
			}

		}
		claims.put(CREATION_DATE, DateTimeManager.currentTimeStampDateInUTC().toString());
		claims.put(JTI, UUID.randomUUID().toString());
		claims.put(PASYSTEM, paSystem);
		claims.put(SOURCE_IP, commonUtil.getRemoteAddress());
		claims.put(LOGIN_TYPE, loginType);
		return claims;
	}

}
