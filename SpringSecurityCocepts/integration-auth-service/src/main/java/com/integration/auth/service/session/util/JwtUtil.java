package com.integration.auth.service.session.util;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.integration.auth.common.errors.ErrorFactory;
import com.integration.auth.common.generator.UniqueIdGenerator;
import com.integration.auth.common.util.CommonUtil;
import com.integration.auth.common.util.DateTimeManager;
import com.integration.auth.service.session.domain.UserSessionDetails;
import com.integration.auth.service.token.exceptions.JWTClaimParserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;

import static com.integration.auth.common.util.Constants.MARKER;

@Slf4j
@Service
public class JwtUtil {

	@Autowired
	CommonUtil commonUtil;

	public UserSessionDetails extractJwtClaims(String sharedToken, String refreshToken, String extendedTokenId) {
		try {
			log.info(MARKER + " ::Calling extractJwtClaims Methods Starts");
			String payLoad = decodePayloadData(JWT.decode(sharedToken).getPayload());
			ObjectMapper objectMapper = new ObjectMapper();
			UserSessionDetails sessionDetails = objectMapper.readValue(payLoad, UserSessionDetails.class);
			sessionDetails.setSessionId(UniqueIdGenerator.nextIdKey());
			sessionDetails.setRefreshToken(refreshToken);
			sessionDetails.setSessionToken(sharedToken);
			sessionDetails.setTerminated(false);
			sessionDetails.setLastAccessed(DateTimeManager.currentTimeStampDateInUTC());
			sessionDetails.setLastAccessedBySystem(commonUtil.getPaSystem());
			sessionDetails.setCreatedBySystem(commonUtil.getPaSystem());
			sessionDetails.setGeneratedByExpirationOff(extendedTokenId);
			log.info(MARKER + " ::Calling extractJwtClaims Methods Ends");
			return sessionDetails;
		}
		catch (Exception ex) {
			throw new JWTClaimParserException(ex, ErrorFactory.getJWTExtractClaimsFailureError());
		}
	}

	public String decodePayloadData(String payload) {
		byte[] decodedBytes = Base64.getDecoder().decode(payload);
		return new String(decodedBytes);
	}

}
