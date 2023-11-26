package com.integration.auth.service.auth.provider;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.integration.auth.common.errors.Errors;
import com.integration.auth.service.auth.UserDetailsServiceImpl;
import com.integration.auth.service.auth.domain.dto.UserDetailsImpl;
import com.integration.auth.service.auth.exceptions.AuthenticationException;
import com.integration.auth.service.auth.exceptions.UserNotFoundException;

@ExtendWith(MockitoExtension.class)
class CustomDaoAuthenticationProviderTest {

	@InjectMocks
	CustomDaoAuthenticationProvider customDaoAuthenticationProvider;

	@Mock
	PasswordEncoder passwordEncoder;

	@Mock
	UserDetailsServiceImpl userDetailsService;

	@Mock
	private AuthenticationException authenticationException;

	// **************authenticate method***************
	@Test
	void testAuthenticate_SuccessfulAuthentication() {
		String username = "testUser";
		String password = "testPassword";
		String tenantId = "testTenantId";
		String id = "1";
		TenantUserPasswordAuthToken authenticationRequest = new TenantUserPasswordAuthToken(tenantId, username,
				password);
		UserDetails userDetails = new UserDetailsImpl(id, username, password, tenantId, null);
		when(userDetailsService.loadUserDetails(authenticationRequest)).thenReturn(userDetails);
		when(passwordEncoder.matches(password, userDetails.getPassword())).thenReturn(true);
		Authentication authentication = customDaoAuthenticationProvider.authenticate(authenticationRequest);
		assertTrue(authentication instanceof TenantUserPasswordAuthToken);
		assertEquals(username, authentication.getName());
		assertEquals(password, authentication.getCredentials().toString());
		assertEquals(tenantId, ((TenantUserPasswordAuthToken) authentication).getTenantId());
	}

	@Test
	void testAuthenticate_FailedAuthentication() {
		String username = "testUser";
		String password = "testPassword";
		String tenantId = "testTenantId";
		String id = "1";
		TenantUserPasswordAuthToken authenticationRequest = new TenantUserPasswordAuthToken(tenantId, username,
				password);
		UserDetails userDetails = new UserDetailsImpl(id, username, "different password", tenantId, null);
		when(userDetailsService.loadUserDetails(authenticationRequest)).thenReturn(userDetails);
		when(passwordEncoder.matches(password, userDetails.getPassword())).thenReturn(false);
		assertThrows(BadCredentialsException.class, () -> {
			customDaoAuthenticationProvider.authenticate(authenticationRequest);
		});

	}

	@Test
	void testAdditionalAuthenticationChecksValidCredentials() {
		String username = "user";
		String password = "password";
		List<GrantedAuthority> grantedAuthorities = List.of(new SimpleGrantedAuthority("ROLE_USER"),
				new SimpleGrantedAuthority("ROLE_ADMIN"));
		UserDetails userDetails = new User(username, password, grantedAuthorities);
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);

		when(passwordEncoder.matches(password, userDetails.getPassword())).thenReturn(true);

		// No exception should be thrown in this case
		assertDoesNotThrow(
				() -> customDaoAuthenticationProvider.additionalAuthenticationChecks(userDetails, authenticationToken));
	}

	@Test
	void testAdditionalAuthenticationChecksInvalidCredentials() {
		String username = "user";
		String password = "password";
		List<GrantedAuthority> grantedAuthorities = List.of(new SimpleGrantedAuthority("ROLE_USER"),
				new SimpleGrantedAuthority("ROLE_ADMIN"));
		UserDetails userDetails = new User(username, password, grantedAuthorities);
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				null);
		assertThrows(BadCredentialsException.class, () -> {
			customDaoAuthenticationProvider.additionalAuthenticationChecks(userDetails, authenticationToken);
		});

	}

	@Test
	void testAdditionalAuthenticationChecksValidCredentialsPasswordNotmatch() {
		String username = "user";
		String password = "password";
		List<GrantedAuthority> grantedAuthorities = List.of(new SimpleGrantedAuthority("ROLE_USER"),
				new SimpleGrantedAuthority("ROLE_ADMIN"));
		UserDetails userDetails = new User(username, password, grantedAuthorities);
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);

		when(passwordEncoder.matches(password, userDetails.getPassword())).thenReturn(false);

		assertThrows(BadCredentialsException.class, () -> {
			customDaoAuthenticationProvider.additionalAuthenticationChecks(userDetails, authenticationToken);
		});

	}

	@Test
	void testRetrieveUserWithValidUserDetails() {
		String username = "user";
		String password = "password";
		List<GrantedAuthority> grantedAuthorities = List.of(new SimpleGrantedAuthority("ROLE_USER"),
				new SimpleGrantedAuthority("ROLE_ADMIN"));
		UserDetails userDetails = new User(username, password, grantedAuthorities);
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);

		when(userDetailsService.loadUserDetails(authenticationToken)).thenReturn(userDetails);

		UserDetails loadedUser = customDaoAuthenticationProvider.retrieveUser(username, authenticationToken);

		assertEquals(userDetails, loadedUser);
	}

	@Test
	void testRetrieveUserWithNullUserDetails() {
		String username = "user";
		String password = "password";
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);

		when(userDetailsService.loadUserDetails(authenticationToken)).thenReturn(null);

		assertThrows(InternalAuthenticationServiceException.class, () -> {
			customDaoAuthenticationProvider.retrieveUser(username, authenticationToken);
		});
	}

	@Test
	void testRetrieveUserWithUsernameNotFoundException() {
		String username = "user";
		String password = "password";
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);

		when(userDetailsService.loadUserDetails(authenticationToken))
				.thenThrow(new UserNotFoundException(new Errors(password, password, 0)));

		assertThrows(UserNotFoundException.class, () -> {
			customDaoAuthenticationProvider.retrieveUser(username, authenticationToken);
		});
	}

	@Test
	void testRetrieveUserWithUsernameNotFoundException2() {
		String username = "user";
		String password = "password";
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);

		when(userDetailsService.loadUserDetails(authenticationToken))
				.thenThrow(new UsernameNotFoundException(password));

		assertThrows(UserNotFoundException.class, () -> {
			customDaoAuthenticationProvider.retrieveUser(username, authenticationToken);
		});
	}

}
