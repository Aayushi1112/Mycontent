package com.integration.auth.service.auth;

import com.integration.auth.common.errors.ErrorCodesAndMessages;
import com.integration.auth.common.errors.Errors;
import com.integration.auth.common.responses.ApiResponse;
import com.integration.auth.common.responses.AuthResponse;
import com.integration.auth.common.responses.Token;
import com.integration.auth.common.util.Constants;
import com.integration.auth.common.util.DateTimeManager;
import com.integration.auth.common.util.TargetTypes;
import com.integration.auth.entrypoint.dto.LoginRequest;
import com.integration.auth.entrypoint.dto.SSOLoginRequest;
import com.integration.auth.entrypoint.dto.SignupRequest;
import com.integration.auth.service.auth.domain.User;
import com.integration.auth.service.auth.domain.repository.UserRepository;
import com.integration.auth.service.auth.encoder.CustomPasswordEncoder;
import com.integration.auth.service.auth.exceptions.UserAuthenticationException;
import com.integration.auth.service.auth.exceptions.UserNotFoundException;
import com.integration.auth.service.auth.exceptions.UsernameAlreadyExistsException;
import com.integration.auth.service.auth.provider.TenantUserPasswordAuthToken;
import com.integration.auth.service.session.SessionService;
import com.integration.auth.service.token.TokenGeneratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static com.integration.auth.common.responses.ResponseBuilder.buildAPiResponse;
import static com.integration.auth.common.responses.ResponseBuilder.buildResponse;
import static com.integration.auth.common.util.Constants.*;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	private static final Logger log = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

	@Autowired
	UserRepository userRepository;

	@Autowired
	CustomPasswordEncoder encoder;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	SessionService sessionService;

	@Autowired
	TokenGeneratorService tokenGeneratorService;

	public AuthResponse processLogin(LoginRequest loginRequest, String paSystem) {
		log.info(MARKER + "Inside processLogin method for user {}", loginRequest.getUsername());
		try {
			authenticateUser(loginRequest);
			
			Token token = tokenGeneratorService.fetchTokenDetails(loginRequest, paSystem,
					TargetTypes.NORMAL_LOGIN.name());
			sessionService.saveSessionDetails(token.getSessionToken(), token.getRefreshToken());
			User user = userRepository.findByUsernameAndTenantId(loginRequest.getUsername(),
					loginRequest.getTenantId());
			return buildResponse(token.getSessionToken(), token.getRefreshToken(), user.getNativeSystem(),
					user.getPasswordResetDate());
		}
		catch (InternalAuthenticationServiceException ex) {
			throw new UserNotFoundException(ex,
					new Errors(TargetTypes.INVALID_USERNAME.name(), ErrorCodesAndMessages.USER_NOT_FOUND_ERROR_MESSAGE,
							ErrorCodesAndMessages.USER_NOT_FOUND_ERROR_CODE));
		}
	}

	private void authenticateUser(LoginRequest loginRequest) {
		log.info(MARKER + "Inside authenticateUser method for user {}", loginRequest.getUsername());
		Authentication authentication = authenticationManager.authenticate(new TenantUserPasswordAuthToken(
				loginRequest.getTenantId(), loginRequest.getUsername(), loginRequest.getPassword()));
		checkUserAuthentication(authentication);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	public AuthResponse ssoProcessLogin(SSOLoginRequest ssoLoginRequest, String paSystem) {
		log.info(MARKER + "Inside ssoTokenGeneration  method for user {}", ssoLoginRequest.getUsername());
		Token token = tokenGeneratorService.fetchTokenDetails(ssoLoginRequest, paSystem, TargetTypes.SSO_LOGIN.name());
		sessionService.saveSessionDetails(token.getSessionToken(), token.getRefreshToken());
		return buildResponse(token.getSessionToken(), token.getRefreshToken());

	}

	public ApiResponse terminateToken(String token, String userId) {
		log.info(MARKER + "Inside Terminate Tokens Method Starts ");
		sessionService.terminateSession(token);
		log.info(MARKER + "Inside Terminate Tokens Method Ends ");
		return buildAPiResponse(LOGOUT_SUCCESSFUL, userId);
	}

	@Override
	public ApiResponse forceTerminate(String username, String tenantId, String terminationReason) {
		log.info(MARKER + "Inside forceTerminate method starts");
		User user = userRepository.findByUsernameAndTenantId(username, tenantId);
		validateUserExists(user, username);
		sessionService.invalidateSessions(username, tenantId, terminationReason);
		log.info(MARKER + "All active sessions for user {} are terminated", username);
		return buildAPiResponse(SESSION_INVALIDATED, username);
	}

	public ApiResponse register(SignupRequest signUpRequest, String paSystem) {
		log.info(MARKER + "Inside createUser Method Starts to register user for tenantId {}",
				signUpRequest.getTenantId());
		checkUserExists(signUpRequest);

		User user = new User(signUpRequest.getUsername(), encoder.encode(signUpRequest.getPassword()),
				signUpRequest.getTenantId(), paSystem, ACTIVE, signUpRequest.getNativeSystem(),
				DateTimeManager.currentTimeStampDateInUTC());
		user.setPasswordResetDate(DateTimeManager.currentTimeStampDateInUTC());
		user.setIsDeleted(Constants.N);
		userRepository.save(user);
		log.info(MARKER + "Inside createUser Method Ends to register user {} Successfully for tenantId {}",
				signUpRequest.getUsername(), signUpRequest.getTenantId());
		return buildAPiResponse(USER_CREATED, user.getUsername());
	}

	private void checkUserExists(SignupRequest signUpRequest) {
		if (userRepository.existsByUsernameAndTenantId(signUpRequest.getUsername(), signUpRequest.getTenantId())) {
			log.error(MARKER + "Username {} for particular tenantId {} is already present", signUpRequest.getUsername(),
					signUpRequest.getTenantId());
			throw new UsernameAlreadyExistsException(new Errors(TargetTypes.USER_EXISTS.name(),
					ErrorCodesAndMessages.USER_EXISTS_ERROR_MESSAGE + signUpRequest.getUsername(),
					ErrorCodesAndMessages.USER_EXISTS_ERROR_CODE));
		}
	}

	private void validateUserExists(User user, String username) {
		if (user == null) {
			log.error("Input user {} not found ", username);
			throw new UserNotFoundException(
					new Errors(TargetTypes.INVALID_USERNAME.name(), ErrorCodesAndMessages.USER_NOT_FOUND_ERROR_MESSAGE,
							ErrorCodesAndMessages.USER_NOT_FOUND_ERROR_CODE));
		}
	}

	private void checkUserAuthentication(Authentication authentication) {
		if (!authentication.isAuthenticated()) {
			throw new UserAuthenticationException(new Errors(TargetTypes.USER_AUTHENTICATION_ERROR.name(),
					ErrorCodesAndMessages.USER_AUTHENTICATION_ERROR_MESSAGE,
					ErrorCodesAndMessages.USER_AUTHENTICATION_ERROR_CODE));
		}
	}

}
