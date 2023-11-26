package com.integration.auth.service.auth;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import com.integration.auth.service.auth.domain.User;
import com.integration.auth.service.auth.domain.repository.UserRepository;
import com.integration.auth.service.auth.exceptions.InactiveUserException;
import com.integration.auth.service.auth.exceptions.UserNotFoundException;
import com.integration.auth.service.auth.provider.TenantUserPasswordAuthToken;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

	@InjectMocks
	UserDetailsServiceImpl userDetailsService;

	@Mock
	UserRepository userRepository;

	// *************load User by username method*********************
	@Test
	void testLoadUserByUsername_UserFound() {
		String username = "testUser";
		User user = new User();
		user.setUsername(username);
		when(userRepository.findByUsername(username)).thenReturn(user);
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		assertNotNull(userDetails);
	}

	@Test
	void testLoadUserByUsername_UserNotFound() {
		String username = "nonExistentUser";
		when(userRepository.findByUsername(username)).thenReturn(null);
		assertThrows(UserNotFoundException.class, () -> {
			userDetailsService.loadUserByUsername(username);
		});
	}

	// *************** load user details method*************************
	@Test
	void testLoadUserDetails_Successful() {
		String tenantId = "testTenant";
		String username = "testUser";
		TenantUserPasswordAuthToken authentication = new TenantUserPasswordAuthToken(tenantId, username, "password");
		User user = new User();
		user.setUsername(username);
		user.setTenantId(tenantId);
		user.setIsDeleted("N"); // User is not deleted
		user.setAccountStatus("ACTIVE"); // User is active
		when(userRepository.findByUsernameAndTenantId(username, tenantId)).thenReturn(user);
		UserDetails userDetails = userDetailsService.loadUserDetails(authentication);
		assertNotNull(userDetails);
	}

	@Test
	void testLoadUserDetails_UserNotFound() {
		String tenantId = "testTenant";
		String username = "nonExistentUser";
		TenantUserPasswordAuthToken authentication = new TenantUserPasswordAuthToken(tenantId, username, "password");
		when(userRepository.findByUsernameAndTenantId(username, tenantId)).thenReturn(null);
		assertThrows(UserNotFoundException.class, () -> {
			userDetailsService.loadUserDetails(authentication);
		});
	}

	@Test
	void testLoadUserDetails_InactiveUser() {
		String tenantId = "testTenant";
		String username = "testUser";
		TenantUserPasswordAuthToken authentication = new TenantUserPasswordAuthToken(tenantId, username, "password");
		User user = new User();
		user.setUsername(username);
		user.setTenantId(tenantId);
		user.setIsDeleted("N"); // User is not deleted
		user.setAccountStatus("INACTIVE"); // User is inactive
		when(userRepository.findByUsernameAndTenantId(username, tenantId)).thenReturn(user);
		assertThrows(InactiveUserException.class, () -> {
			userDetailsService.loadUserDetails(authentication);
		});
	}

	@Test
	void testLoadUserDetails_DeletedUser() {
		String tenantId = "testTenant";
		String username = "testUser";
		TenantUserPasswordAuthToken authentication = new TenantUserPasswordAuthToken(tenantId, username, "password");
		User user = new User();
		user.setUsername(username);
		user.setTenantId(tenantId);
		user.setIsDeleted("Y"); // User is not deleted
		user.setAccountStatus("ACTIVE"); // User is inactive
		when(userRepository.findByUsernameAndTenantId(username, tenantId)).thenReturn(user);
		assertThrows(InactiveUserException.class, () -> {
			userDetailsService.loadUserDetails(authentication);
		});
	}

}
