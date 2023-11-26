package com.integration.auth.service.common;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.integration.auth.common.errors.ErrorCodesAndMessages;
import com.integration.auth.common.errors.ErrorFactory;
import com.integration.auth.common.errors.Errors;
import com.integration.auth.common.exceptions.InvalidInputDataValidationException;
import com.integration.auth.service.token.exceptions.JWTCreationException;
import com.integration.auth.common.util.Constants;
import com.integration.auth.common.util.TargetTypes;
import com.integration.auth.service.token.exceptions.JWTClaimParserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Slf4j
@Component
public class JWTHelper {

	@Value("${ias.jwt.secret.key}")
	private String jwtSecret;

	public String generateJwtToken(String subject, Map<String, String> claims, int hours) {
		if (claims == null || claims.isEmpty()) {
			throw new InvalidInputDataValidationException(
					new Errors(TargetTypes.EMPTY_JWT_CLAIMS.name(), ErrorCodesAndMessages.EMPTY_JWT_CLAIMS_ERROR_MSG,
							ErrorCodesAndMessages.EMPTY_JWT_CLAIMS_ERROR_CODE));
		}
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.HOUR_OF_DAY, hours);

			JWTCreator.Builder jwtBuilder = JWT.create().withSubject(subject);
			claims.forEach(jwtBuilder::withClaim);

			return jwtBuilder.withNotBefore(new Date()).withExpiresAt(calendar.getTime())
					.sign(Algorithm.HMAC256(jwtSecret));
		}
		catch (Exception ex) {
			throw new JWTCreationException(ex, ErrorFactory.getJWTCreationFailureError());
		}
	}

	public String getUserNameFromJwtToken(String token) {
		try {
			DecodedJWT decodedJWT = JWT.decode(token);
			return decodedJWT.getSubject();
		}
		catch (JWTDecodeException ex) {
			throw new JWTClaimParserException(ex, ErrorFactory.getJWTDecodingFailureError());
		}
	}

	public String getSourceIpFromToken(String token) {
		try {
			DecodedJWT decodedJWT = JWT.decode(token);
			return decodedJWT.getClaim(Constants.SOURCE_IP).asString();
		}
		catch (JWTDecodeException ex) {
			throw new JWTClaimParserException(ex, ErrorFactory.getJWTDecodingFailureError());
		}
	}

	public String getLoginTypeFromToken(String token) {
		try {
			DecodedJWT decodedJWT = JWT.decode(token);
			return decodedJWT.getClaim(Constants.LOGIN_TYPE).asString();
		}
		catch (JWTDecodeException ex) {
			throw new JWTClaimParserException(ex, ErrorFactory.getJWTDecodingFailureError());
		}
	}

	public String getDeviceFromToken(String token) {
		try {
			DecodedJWT decodedJWT = JWT.decode(token);
			return decodedJWT.getClaim(Constants.DEVICE).asString();
		}
		catch (JWTDecodeException ex) {
			throw new JWTClaimParserException(ex, ErrorFactory.getJWTDecodingFailureError());
		}
	}

	public String getTenantIdFromJwtToken(String token) {
		try {
			String payLoad = decodePayload(JWT.decode(token).getPayload());
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(payLoad);
			return jsonNode.get(Constants.TENANT_ID).asText();

		}
		catch (JWTDecodeException | JsonProcessingException ex) {
			throw new JWTClaimParserException(ex,
					new Errors(TargetTypes.JWT_PARSER.name(), ErrorCodesAndMessages.JWT_EXTRACT_CLAIMS_ERROR,
							ErrorCodesAndMessages.JWT_EXTRACT_CLAIMS_ERROR_CODE));
		}

	}

	public boolean validateJwtToken(String token) {
		try {
			JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtSecret)).build();
			verifier.verify(token);
			return true;
		}
		catch (JWTVerificationException ex) {
			log.error("Invalid JWT signature: {}", ex.getMessage());
		}
		return false;
	}

	public boolean isTokenExpired(String token) {
		try {
			DecodedJWT decodedJwt = JWT.decode(token);
			Date expirationTime = decodedJwt.getExpiresAt();
			return expirationTime.before(new Date());
		}
		catch (JWTDecodeException ex) {
			log.error("Invalid JWT signature: {}", ex.getMessage());
			return false;
		}
	}

	public String decodePayload(String payload) {
		byte[] decodedBytes = Base64.getDecoder().decode(payload);
		return new String(decodedBytes);
	}

}
