package com.integration.auth.service.session.dataprovider;

import com.integration.auth.common.errors.Errors;
import com.integration.auth.common.exceptions.InvalidTokenException;
import com.integration.auth.service.session.dataprovider.mongo.UserSessionCustomMongoRepository;
import com.integration.auth.service.session.dataprovider.mongo.UserSessionMongoRepository;
import com.integration.auth.service.session.domain.UserSessionDetails;
import com.integration.auth.service.session.exceptions.SessionDatabaseFailureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessResourceFailureException;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserSessionRepositoryImplTest {

	@Mock
	UserSessionMongoRepository userSessionMongoRepository;

	@Mock
	UserSessionCustomMongoRepository userSessionCustomMongoRepository;

	@InjectMocks
	UserSessionRepositoryImpl userSessionRepositoryImpl;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testStoreUserSessionSuccess() {
		// Arrange
		UserSessionDetails userSessionDetails = new UserSessionDetails(); // Create your
		// user
		// session
		// details
		// object

		// Mock the save method of the repository to return the same object
		Mockito.when(userSessionMongoRepository.save(userSessionDetails)).thenReturn(userSessionDetails);

		// Act
		UserSessionDetails savedUserSession = userSessionRepositoryImpl.storeUserSession(userSessionDetails);

		// Assert
		assertEquals(userSessionDetails, savedUserSession);
	}

	@Test
	void testStoreUserSessionException() {
		// Arrange
		UserSessionDetails userSessionDetails = new UserSessionDetails(); // Create your
		// user
		// session
		// details
		// object

		// Mock the save method of the repository to throw an exception
		when(userSessionMongoRepository.save(userSessionDetails))
				.thenThrow(new DataAccessResourceFailureException("Simulated database exception"));

		// Act and Assert
		// Here, we expect a SessionDatabaseFailureException to be thrown with the
		// simulated exception.
		assertThrows(SessionDatabaseFailureException.class, () -> {
			userSessionRepositoryImpl.storeUserSession(userSessionDetails);
		});
	}

	@Test
	void testFetchUserBySessionTokenAndUserNameSuccess() {
		// Arrange
		String sessionToken = "token123";
		String userName = "user123";

		// Create an example UserSessionDetails object
		UserSessionDetails userSessionDetails = new UserSessionDetails();
		userSessionDetails.setSessionToken(sessionToken);
		userSessionDetails.setUsername(userName);

		// Mock the repository method to return an Optional with the user session details
		when(userSessionMongoRepository.findBySessionTokenAndUsername(sessionToken, userName))
				.thenReturn(Optional.of(userSessionDetails));

		// Act
		Optional<UserSessionDetails> fetchedUserSession = userSessionRepositoryImpl
				.fetchUserBySessionTokenAndUserName(sessionToken, userName);

		// Assert
		assertTrue(fetchedUserSession.isPresent());
		assertEquals(userSessionDetails, fetchedUserSession.get());
	}

	@Test
	void testFetchUserBySessionTokenAndUserNameException() {
		// Arrange
		String sessionToken = "token123";
		String userName = "user123";

		// Mock the repository method to throw an exception
		when(userSessionMongoRepository.findBySessionTokenAndUsername(sessionToken, userName))
				.thenThrow(new RuntimeException("Simulated database exception") {
				});

		// Act and Assert
		// Here, we expect a SessionDatabaseFailureException to be thrown with the
		// simulated exception.
		assertThrows(SessionDatabaseFailureException.class, () -> {
			userSessionRepositoryImpl.fetchUserBySessionTokenAndUserName(sessionToken, userName);
		});
	}

	@Test
	void testTerminateUserSessionSuccess() {
		String sharedToken = "token123";

		userSessionRepositoryImpl.terminateUserSession(sharedToken);
		verify(userSessionCustomMongoRepository, times(1)).terminateSession(sharedToken);
	}

	@Test
	void testTerminateUserSessionInvalidTokenException() {
		String sharedToken = "invalid_token123";

		doThrow(new InvalidTokenException(new Exception(), new Errors("a", "a", 33)))
				.when(userSessionCustomMongoRepository).terminateSession(sharedToken);

		assertThrows(InvalidTokenException.class, () -> {
			userSessionRepositoryImpl.terminateUserSession(sharedToken);
		});
	}

	@Test
	void testTerminateUserSessionDatabaseFailureException() {
		// Arrange
		String sharedToken = "token123";

		// Mock the repository method to throw a generic exception
		doThrow(new RuntimeException("Simulated database exception")).when(userSessionCustomMongoRepository)
				.terminateSession(sharedToken);

		// Act and Assert
		// Here, we expect a SessionDatabaseFailureException to be thrown.
		assertThrows(SessionDatabaseFailureException.class, () -> {
			userSessionRepositoryImpl.terminateUserSession(sharedToken);
		});
	}

	@Test
	void testRefreshUserSessionSuccess() {
		String generatedSessionToken = "sessionToken123";
		String generatedRefreshToken = "refreshToken123";
		String existingRefreshToken = "existingRefreshToken123";

		userSessionRepositoryImpl.refreshUserSession(generatedSessionToken, generatedRefreshToken,
				existingRefreshToken);
		verify(userSessionCustomMongoRepository, times(1)).refreshSession(generatedSessionToken, generatedRefreshToken,
				existingRefreshToken);
	}

	@Test
	void testRefreshUserSessionInvalidTokenException() {
		// Arrange
		String generatedSessionToken = "sessionToken123";
		String generatedRefreshToken = "refreshToken123";
		String existingRefreshToken = "existingRefreshToken123";

		// Mock the repository method to throw an InvalidTokenException
		doThrow(new InvalidTokenException(new Exception(), new Errors("a", "a", 33)))
				.when(userSessionCustomMongoRepository)
				.refreshSession(generatedSessionToken, generatedRefreshToken, existingRefreshToken);

		// Act and Assert
		// Here, we expect an InvalidTokenException to be thrown.
		assertThrows(InvalidTokenException.class, () -> {
			userSessionRepositoryImpl.refreshUserSession(generatedSessionToken, generatedRefreshToken,
					existingRefreshToken);
		});
	}

	@Test
	void testRefreshUserSessionDatabaseFailureException() {
		// Arrange
		String generatedSessionToken = "sessionToken123";
		String generatedRefreshToken = "refreshToken123";
		String existingRefreshToken = "existingRefreshToken123";

		// Mock the repository method to throw a generic exception
		doThrow(new RuntimeException("Simulated database exception")).when(userSessionCustomMongoRepository)
				.refreshSession(generatedSessionToken, generatedRefreshToken, existingRefreshToken);

		// Act and Assert
		// Here, we expect a SessionDatabaseFailureException to be thrown.
		assertThrows(SessionDatabaseFailureException.class, () -> {
			userSessionRepositoryImpl.refreshUserSession(generatedSessionToken, generatedRefreshToken,
					existingRefreshToken);
		});
	}

}
