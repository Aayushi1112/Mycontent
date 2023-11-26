package com.integration.auth.service.usermanagement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.integration.auth.common.responses.ApiResponse;
import com.integration.auth.common.util.Constants;
import com.integration.auth.entrypoint.dto.DeleteUserInputRequest;
import com.integration.auth.entrypoint.dto.PasswordChangeRequest;
import com.integration.auth.entrypoint.dto.PasswordResetRequest;
import com.integration.auth.service.auth.domain.User;
import com.integration.auth.service.auth.domain.repository.UserRepository;
import com.integration.auth.service.auth.encoder.CustomPasswordEncoder;
import com.integration.auth.service.auth.exceptions.ActivationStatusConflictException;
import com.integration.auth.service.auth.exceptions.InactiveUserException;
import com.integration.auth.service.auth.exceptions.UserNotFoundException;

class UserManagementServiceImplTest {

	@InjectMocks
	private UserManagementServiceImpl userManagementService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private CustomPasswordEncoder encoder;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testToggleActivation_Success() {
		User user = new User();
		user.setUsername("testUser");
		user.setTenantId("testTenant");
		user.setAccountStatus(Constants.ACTIVE_USER);

		when(userRepository.findByUsernameAndTenantId("testUser", "testTenant")).thenReturn(user);

		ApiResponse response = userManagementService.toggleActivation("testUser", "inactive", "testTenant");

		verify(userRepository, times(1)).findByUsernameAndTenantId("testUser", "testTenant");
		verify(userRepository, times(1)).save(user);
		assertEquals("User activation status updated", response.getMessage());
		assertEquals("testUser", response.getUser());
	}

	@Test
	void testToggleActivation_StatusConflict() {
		User user = new User();
		user.setUsername("testUser");
		user.setTenantId("testTenant");
		user.setAccountStatus("inactive");

		when(userRepository.findByUsernameAndTenantId("testUser", "testTenant")).thenReturn(user);

		assertThrows(ActivationStatusConflictException.class, () -> {
			userManagementService.toggleActivation("testUser", "inactive", "testTenant");
		});

		verify(userRepository, times(1)).findByUsernameAndTenantId("testUser", "testTenant");
		verify(userRepository, never()).save(any(User.class));
	}

	@Test
	void testToggleActivation_InvalidUser() {
		when(userRepository.findByUsernameAndTenantId("testUser", "testTenant")).thenReturn(null);

		assertThrows(UserNotFoundException.class, () -> {
			userManagementService.toggleActivation("testUser", "inactive", "testTenant");
		});

		verify(userRepository, times(1)).findByUsernameAndTenantId("testUser", "testTenant");
		verify(userRepository, never()).save(any(User.class));
	}

	@Test
	void testChangePassword_Success() {
		User user = new User();
		user.setUsername("testUser");
		user.setTenantId("testTenant");
		user.setAccountStatus(Constants.ACTIVE_USER);

		when(userRepository.findByUsernameAndTenantId("testUser", "testTenant")).thenReturn(user);
		when(encoder.matches("oldPassword", user.getPassword())).thenReturn(true);

		PasswordChangeRequest request = new PasswordChangeRequest();
		request.setUsername("testUser");
		request.setTenantId("testTenant");
		request.setOldPassword("oldPassword");
		request.setNewPassword("newPassword");

		ApiResponse response = userManagementService.changePassword(request);

		verify(userRepository, times(1)).findByUsernameAndTenantId("testUser", "testTenant");
		verify(encoder, times(1)).matches("oldPassword", user.getPassword());
		verify(userRepository, times(1)).save(user);
		assertEquals(Constants.PASSWORD_CHANGE, response.getMessage());
		assertEquals("testUser", response.getUser());
	}

	@Test
	void testChangePassword_InactiveUser() {
		User user = new User();
		user.setUsername("testUser");
		user.setTenantId("testTenant");
		user.setAccountStatus("inactive");

		when(userRepository.findByUsernameAndTenantId("testUser", "testTenant")).thenReturn(user);

		PasswordChangeRequest request = new PasswordChangeRequest();
		request.setUsername("testUser");
		request.setTenantId("testTenant");
		request.setOldPassword("oldPassword");
		request.setNewPassword("newPassword");

		assertThrows(InactiveUserException.class, () -> {
			userManagementService.changePassword(request);
		});

		verify(userRepository, times(1)).findByUsernameAndTenantId("testUser", "testTenant");
		verify(encoder, never()).matches(anyString(), anyString());
		verify(userRepository, never()).save(any(User.class));
	}

	@Test
	void testChangePassword_InvalidUser() {
		PasswordChangeRequest request = new PasswordChangeRequest();
		request.setUsername("testUser");
		request.setTenantId("testTenant");

		when(userRepository.findByUsernameAndTenantId("testUser", "testTenant")).thenReturn(null);

		assertThrows(UserNotFoundException.class, () -> {
			userManagementService.changePassword(request);
		});

		verify(userRepository, times(1)).findByUsernameAndTenantId("testUser", "testTenant");
		verify(userRepository, never()).save(any(User.class));
	}

	@Test
	void testChangePassword_WrongOldPassword() {
		User user = new User();
		user.setUsername("testUser");
		user.setTenantId("testTenant");
		user.setAccountStatus(Constants.ACTIVE_USER);

		PasswordChangeRequest request = new PasswordChangeRequest();
		request.setUsername("testUser");
		request.setTenantId("testTenant");
		request.setOldPassword("wrongPassword");
		request.setNewPassword("newPassword");

		when(userRepository.findByUsernameAndTenantId("testUser", "testTenant")).thenReturn(user);
		when(encoder.matches(request.getOldPassword(), user.getPassword())).thenReturn(false);

		userManagementService.changePassword(request);

		verify(userRepository, times(1)).findByUsernameAndTenantId("testUser", "testTenant");
		verify(encoder, times(1)).matches("wrongPassword", user.getPassword());
		// verify(userRepository, times(1)).save(any(User.class)); // unexpected behaviour
	}

	@Test
	void testResetPassword_Success() {
		User user = new User();
		user.setUsername("testUser");
		user.setTenantId("testTenant");
		user.setAccountStatus(Constants.ACTIVE_USER);

		when(userRepository.findByUsernameAndTenantId("testUser", "testTenant")).thenReturn(user);

		PasswordResetRequest request = new PasswordResetRequest();
		request.setUsername("testUser");
		request.setTenantId("testTenant");
		request.setNewPassword("newPassword");

		ApiResponse response = userManagementService.resetPassword(request);

		verify(userRepository, times(1)).findByUsernameAndTenantId("testUser", "testTenant");
		verify(encoder, times(1)).encode("newPassword");
		verify(userRepository, times(1)).save(user);
		assertEquals("Password successfully updated for user ", response.getMessage());
		assertEquals("testUser", response.getUser());
	}

	@Test
	void testResetPassword_InactiveUser() {
		User user = new User();
		user.setUsername("testUser");
		user.setTenantId("testTenant");
		user.setAccountStatus("inactive");

		when(userRepository.findByUsernameAndTenantId("testUser", "testTenant")).thenReturn(user);

		PasswordResetRequest request = new PasswordResetRequest();
		request.setUsername("testUser");
		request.setTenantId("testTenant");
		request.setNewPassword("newPassword");

		assertThrows(InactiveUserException.class, () -> {
			userManagementService.resetPassword(request);
		});

		verify(userRepository, times(1)).findByUsernameAndTenantId("testUser", "testTenant");
		verify(encoder, never()).encode(anyString());
		verify(userRepository, never()).save(any(User.class));
	}

	@Test
	void testResetPassword_InvalidUser() {
		User user = new User();
		user.setUsername("testUser");
		user.setTenantId("testTenant");
		user.setAccountStatus("inactive");

		when(userRepository.findByUsernameAndTenantId("testUser", "testTenant")).thenReturn(null);

		PasswordResetRequest request = new PasswordResetRequest();
		request.setUsername("testUser");
		request.setTenantId("testTenant");
		request.setNewPassword("newPassword");

		assertThrows(UserNotFoundException.class, () -> {
			userManagementService.resetPassword(request);
		});

		verify(userRepository, times(1)).findByUsernameAndTenantId("testUser", "testTenant");
		verify(encoder, never()).encode(anyString());
		verify(userRepository, never()).save(any(User.class));
	}

	@Test
	void testDeleteUser_Success() {
		User user = new User();
		user.setUsername("testUser");
		user.setTenantId("testTenant");
		user.setIsDeleted("N");

		when(userRepository.findByUsernameAndTenantId("testUser", "testTenant")).thenReturn(user);

		DeleteUserInputRequest request = new DeleteUserInputRequest();
		request.setUsername("testUser");
		request.setTenantId("testTenant");

		ApiResponse response = userManagementService.deleteUser(request);

		verify(userRepository, times(1)).findByUsernameAndTenantId("testUser", "testTenant");
		verify(userRepository, times(1)).save(user);
		assertEquals("User got deleted successfully from Database", response.getMessage());
		assertEquals("testUser", response.getUser());
		assertEquals(Constants.Y, user.getIsDeleted());
	}

	@Test
	void testDeleteUser_UserAlreadyDeleted() {
		User user = new User();
		user.setUsername("testUser");
		user.setTenantId("testTenant");
		user.setIsDeleted("Y");

		when(userRepository.findByUsernameAndTenantId("testUser", "testTenant")).thenReturn(user);

		DeleteUserInputRequest request = new DeleteUserInputRequest();
		request.setUsername("testUser");
		request.setTenantId("testTenant");

		assertThrows(UserNotFoundException.class, () -> {
			userManagementService.deleteUser(request);
		});

		verify(userRepository, times(1)).findByUsernameAndTenantId("testUser", "testTenant");
		verify(userRepository, never()).save(any(User.class));
	}

	@Test
	void testDeleteUser_UserNotFound() {
		when(userRepository.findByUsernameAndTenantId("testUser", "testTenant")).thenReturn(null);

		DeleteUserInputRequest request = new DeleteUserInputRequest();
		request.setUsername("testUser");
		request.setTenantId("testTenant");

		assertThrows(UserNotFoundException.class, () -> {
			userManagementService.deleteUser(request);
		});

		verify(userRepository, times(1)).findByUsernameAndTenantId("testUser", "testTenant");
		verify(userRepository, never()).save(any(User.class));
	}

}
