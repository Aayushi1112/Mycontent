package com.integration.auth.service.auth;

import com.integration.auth.common.errors.ErrorCodesAndMessages;
import com.integration.auth.common.errors.Errors;
import com.integration.auth.common.util.Constants;
import com.integration.auth.common.util.DateTimeManager;
import com.integration.auth.service.auth.exceptions.InactiveUserException;
import com.integration.auth.service.auth.exceptions.UserNotFoundException;
import com.integration.auth.common.util.TargetTypes;
import com.integration.auth.service.auth.domain.dto.UserDetailsImpl;
import com.integration.auth.service.auth.provider.TenantUserPasswordAuthToken;
import com.integration.auth.service.auth.domain.User;
import com.integration.auth.service.auth.domain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		log.info("Checking user {} in database based on tenantId", username);
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UserNotFoundException(
					new Errors(TargetTypes.INVALID_USERNAME.name(), ErrorCodesAndMessages.USER_NOT_FOUND_ERROR_MESSAGE,
							ErrorCodesAndMessages.USER_NOT_FOUND_ERROR_CODE));

		}
		return UserDetailsImpl.build(user);
	}

	@Transactional
	public org.springframework.security.core.userdetails.UserDetails loadUserDetails(Authentication authentication)
			throws UsernameNotFoundException {
		String tenantId = ((TenantUserPasswordAuthToken) authentication).getTenantId();
		String username = authentication.getName();
		log.info("Checking user {} in database based on tenantId {}", username, tenantId);
		User user = userRepository.findByUsernameAndTenantId(username, tenantId);

		validateUserExists(user, username);
		if (StringUtils.equalsIgnoreCase(user.getIsDeleted(), Constants.Y)) {
			throw new InactiveUserException(new Errors(TargetTypes.INACTIVE_USER.name(),
					ErrorCodesAndMessages.INACTIVE_USER_ERROR_MESSAGE, ErrorCodesAndMessages.DELETED_USER_ERROR_CODE));
		}
		checkUserActiveStatus(user);
		user.setLastLoginDate(DateTimeManager.currentTimeStampDateInUTC());
		userRepository.save(user);
		return UserDetailsImpl.build(user);
	}

	private static void validateUserExists(User user, String username) {
		if (user == null) {
			log.error("Input user {} not found ", username);
			throw new UserNotFoundException(
					new Errors(TargetTypes.INVALID_USERNAME.name(), ErrorCodesAndMessages.USER_NOT_FOUND_ERROR_MESSAGE,
							ErrorCodesAndMessages.USER_NOT_FOUND_ERROR_CODE));
		}
	}

	private static void checkUserActiveStatus(User user) {
		if (!StringUtils.equalsIgnoreCase(user.getAccountStatus(), "ACTIVE")) {
			log.error("Input user {} is not in Active state ", user.getUsername());
			throw new InactiveUserException(new Errors(TargetTypes.INACTIVE_USER.name(),
					ErrorCodesAndMessages.INACTIVE_USER_ERROR_MESSAGE, ErrorCodesAndMessages.INACTIVE_USER_ERROR_CODE));
		}
	}

}