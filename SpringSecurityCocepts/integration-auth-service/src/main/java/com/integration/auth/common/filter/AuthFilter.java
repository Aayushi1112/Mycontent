package com.integration.auth.common.filter;

import com.integration.auth.common.errors.ErrorCodesAndMessages;
import com.integration.auth.common.errors.ErrorDetail;
import com.integration.auth.common.errors.ErrorFactory;
import com.integration.auth.common.exceptions.InvalidInputDataValidationException;
import com.integration.auth.common.responses.FailureResponseComposer;
import com.integration.auth.common.util.Constants;
import com.integration.auth.common.util.PaSystem;
import com.integration.auth.common.util.TargetTypes;
import com.integration.auth.service.auth.exceptions.AuthServiceException;
import io.vavr.collection.Seq;
import io.vavr.control.Validation;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

import static com.integration.auth.common.util.Constants.*;

public class AuthFilter extends OncePerRequestFilter {

	private static final Logger log = LoggerFactory.getLogger(AuthFilter.class);

	@Value("${fc.credentials.clientId}")
	private String franconnectClientId;

	@Value("${fc.credentials.secret}")
	private String franconnectSecret;

	@Value("${wm.credentials.clientId}")
	private String worldManagerClientId;

	@Value("${wm.credentials.secret}")
	private String worldManagerSecret;

	@Override
	protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request,
			jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain)
			throws jakarta.servlet.ServletException, IOException {
		try {
			if (request.getRequestURL().toString().contains("/manage-auth/v1/user/")) {
				log.info(MARKER + "Inside doFilterInternal Method Starts");
				String clientId = request.getHeader(CLIENT_ID);
				String clientSecret = request.getHeader(CLIENT_SECRET);
				String paSystem = request.getHeader(PASYSTEM);
				validateMandatoryInputRequestParameter(clientId, clientSecret, paSystem);
				log.info(MARKER + "Inside doFilterInternal Method Ends Successfully");
			}
		}
		catch (AuthServiceException ex) {
			log.error(MARKER + AUTH_FILTER_ERROR, request.getRequestURL().toString(), ex);
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setContentType("application/json");
			response.getWriter().write(FailureResponseComposer.composeAuthFilterFailureResponse(ex,
					AUTH_FILTER_ERROR + request.getRequestURL().toString()));
			return;
		}
		catch (Exception ex) {
			log.error(MARKER + AUTH_FILTER_ERROR, request.getRequestURL().toString(), ex);
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setContentType("application/json");
			response.getWriter().write(FailureResponseComposer.composeAuthFilterGenericFailureResponse(ex,
					AUTH_FILTER_ERROR + request.getRequestURL().toString()));
			return;
		}
		filterChain.doFilter(request, response);
	}

	private boolean isValidEnumValue(String value) {
		try {
			PaSystem.valueOf(value);
			return true;
		}
		catch (IllegalArgumentException e) {
			return false;
		}
	}

	public void validateMandatoryInputRequestParameter(String clientId, String clientSecret, String paSystem) {
		Validation<ErrorDetail, String> validatePaSystem = validatePaSystem(paSystem);
		Validation<ErrorDetail, String> validateClientIdAndClientSecret = validateClientIdAndClientSecret(clientId,
				clientSecret, paSystem);
		Validation<Seq<ErrorDetail>, Boolean> validation = Validation
				.combine(validatePaSystem, validateClientIdAndClientSecret).ap((v1, v2) -> Boolean.TRUE);

		handleMandatoryMultipleFieldsValidationFailure(validation);
	}

	private Validation<ErrorDetail, String> validateClientIdAndClientSecret(String clientId, String clientSecret,
			String paSystem) {
		return (StringUtils.isBlank(clientId) || StringUtils.isBlank(clientSecret)
				|| !isValidClientIdAndSecret(clientId, clientSecret, paSystem))
						? Validation.invalid(new ErrorDetail(TargetTypes.INVALID_CLIENT_ID_AND_SECRET.name(),
								ErrorCodesAndMessages.INVALID_CLIENT_ID_AND_SECRET_ERROR_MESSAGE,
								ErrorCodesAndMessages.INVALID_CLIENT_ID_AND_SECRET_ERROR_MESSAGE_CODE))
						: Validation.valid(clientId + clientSecret);

	}

	private boolean isValidClientIdAndSecret(String clientId, String clientSecret, String paSystem) {
		return (franconnectClientId.equals(clientId) && franconnectSecret.equals(clientSecret)
				&& Constants.FC_SYSTEM.equals(paSystem))
				|| (worldManagerClientId.equals(clientId) && worldManagerSecret.equals(clientSecret)
						&& Constants.WM_SYSTEM.equals(paSystem));

	}

	private Validation<ErrorDetail, String> validatePaSystem(String paSystem) {
		return (StringUtils.isBlank(paSystem) || !isValidEnumValue(paSystem))
				? Validation.invalid(new ErrorDetail(TargetTypes.INVALID_PA_SYSTEM.name(),
						ErrorCodesAndMessages.INVALID_PA_SYSTEM_ERROR_MESSAGE,
						ErrorCodesAndMessages.INVALID_PA_SYSTEM_ERROR_CODE))
				: Validation.valid(paSystem);
	}

	protected void handleMandatoryMultipleFieldsValidationFailure(Validation<Seq<ErrorDetail>, Boolean> validations) {
		if (validations.isInvalid()) {
			throw new InvalidInputDataValidationException(new Exception(Constants.REQUIRED_DATA_NOT_FOUND_OR_INVALID),
					ErrorFactory.failingMultipleInputDataError(validations.getError(),
							ErrorCodesAndMessages.MANDATORY_FIELD_ERROR_MSG,
							ErrorCodesAndMessages.MANDATORY_FIELD_ERROR_CODE));

		}
	}

}