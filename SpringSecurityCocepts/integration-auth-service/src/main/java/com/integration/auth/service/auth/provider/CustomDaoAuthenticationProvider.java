package com.integration.auth.service.auth.provider;

import com.integration.auth.common.errors.ErrorCodesAndMessages;
import com.integration.auth.common.errors.Errors;
import com.integration.auth.service.auth.exceptions.AuthenticationException;
import com.integration.auth.service.auth.exceptions.UserNotFoundException;
import com.integration.auth.common.util.TargetTypes;
import com.integration.auth.service.auth.UserDetailsServiceImpl;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

public class CustomDaoAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	private UserDetailsServiceImpl userDetailsService;

	private PasswordEncoder passwordEncoder;

	private volatile String userNotFoundEncodedPassword;

	public void setUserDetailsService(UserDetailsServiceImpl userDetailsService) {
		this.userDetailsService = userDetailsService;

	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		Assert.notNull(passwordEncoder, "passwordEncoder cannot be null");
		this.passwordEncoder = passwordEncoder;
		this.userNotFoundEncodedPassword = null;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		String tenantId = ((TenantUserPasswordAuthToken) authentication).getTenantId();
		UserDetails userDetails = userDetailsService.loadUserDetails(authentication);

		if (!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("Invalid username or password");
		}

		return new TenantUserPasswordAuthToken(tenantId, username, password, null);
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication)
			throws org.springframework.security.core.AuthenticationException {
		if (authentication.getCredentials() == null) {
			this.logger.debug("Failed to authenticate since no credentials provided");
			throw new BadCredentialsException(this.messages
					.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
		}
		String presentedPassword = authentication.getCredentials().toString();
		if (!this.passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
			this.logger.debug("Failed to authenticate since password does not match stored value");
			throw new BadCredentialsException(this.messages
					.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
		}
	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws org.springframework.security.core.AuthenticationException {
		try {
			UserDetails loadedUser = this.userDetailsService.loadUserDetails(authentication);
			if (loadedUser == null) {
				throw new InternalAuthenticationServiceException(
						"UserDetailsService returned null, which is an interface contract violation");
			}
			return loadedUser;
		}
		catch (UsernameNotFoundException ex) {
			mitigateAgainstTimingAttack(authentication);
			throw new UserNotFoundException(
					new Errors(TargetTypes.INVALID_USERNAME.name(), ErrorCodesAndMessages.USER_NOT_FOUND_ERROR_MESSAGE,
							ErrorCodesAndMessages.USER_NOT_FOUND_ERROR_CODE));
		}
	}

	private void mitigateAgainstTimingAttack(UsernamePasswordAuthenticationToken authentication) {
		if (authentication.getCredentials() != null) {
			String presentedPassword = authentication.getCredentials().toString();
			this.passwordEncoder.matches(presentedPassword, this.userNotFoundEncodedPassword);
		}
	}

}
