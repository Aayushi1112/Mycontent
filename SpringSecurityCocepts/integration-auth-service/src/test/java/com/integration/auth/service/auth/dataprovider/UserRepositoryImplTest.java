package com.integration.auth.service.auth.dataprovider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.integration.auth.service.auth.dataprovider.mongo.UserMongoRepository;
import com.integration.auth.service.auth.domain.User;
import com.integration.auth.service.auth.exceptions.AuthDatabaseFailureException;

@ExtendWith(MockitoExtension.class)
class UserRepositoryImplTest {

	@Mock
	UserMongoRepository userMongoRepository;

	@InjectMocks
	UserRepositoryImpl userRepositoryImpl;

	@Test
	void findByUsernameTest() {
		String username = "xyz";
		User user = new User();
		Mockito.when(userMongoRepository.findByUsername(username)).thenReturn(user);
		User expecteduser = userRepositoryImpl.findByUsername(username);
		assertEquals(user, expecteduser);

	}

	@Test
	void findByUsernameTestFail() {
		String username = "xyz";
		User user = new User();
		Mockito.when(userMongoRepository.findByUsername(username))
				.thenThrow(new RuntimeException("Simulated database exception"));
		assertThrows(AuthDatabaseFailureException.class, () -> {
			userRepositoryImpl.findByUsername(username);
		});

	}

	@Test
	void findByUsernameAndTenantIdTest() {
		String username = "abc";
		String tenandId = "123";
		// String username = "xyz";
		User user = new User();
		Mockito.when(userMongoRepository.findByUsernameAndTenantId(username, tenandId)).thenReturn(user);
		User expecteduser = userRepositoryImpl.findByUsernameAndTenantId(username, tenandId);

		assertEquals(user, expecteduser);

	}

	@Test
	void findByUsernameAndTenantIdTestFail() {
		String username = "abc";
		String tenandId = "123";
		// String username = "xyz";
		User user = new User();
		Mockito.when(userMongoRepository.findByUsernameAndTenantId(username, tenandId))
				.thenThrow(new RuntimeException("Simulated database exception"));
		assertThrows(AuthDatabaseFailureException.class, () -> {
			userRepositoryImpl.findByUsernameAndTenantId(username, tenandId);
		});

	}

	@Test
	void existsByUsernameAndTenantIdTest() {
		String username = "abc";
		String tenandId = "123";
		Mockito.when(userMongoRepository.existsByUsernameAndTenantId(username, tenandId)).thenReturn(true);
		Boolean result = userRepositoryImpl.existsByUsernameAndTenantId(username, tenandId);
		assertEquals(true, result);
	}

	@Test
	void existsByUsernameAndTenantIdTestFail() {
		String username = "abc";
		String tenandId = "123";
		Mockito.when(userMongoRepository.existsByUsernameAndTenantId(username, tenandId))
				.thenThrow(new RuntimeException("Simulated database exception"));
		assertThrows(AuthDatabaseFailureException.class, () -> {
			userRepositoryImpl.existsByUsernameAndTenantId(username, tenandId);
		});
	}

	@Test
	void saveTest() {
		User user = new User();
		Mockito.when(userMongoRepository.save(user)).thenReturn(user);
		User userExpected = userRepositoryImpl.save(user);
		assertEquals(user, userExpected);

	}

	@Test

	void saveTestFail() {
		User user = new User();
		Mockito.when(userMongoRepository.save(user)).thenThrow(new RuntimeException("Simulated database exception"));
		assertThrows(AuthDatabaseFailureException.class, () -> {
			userRepositoryImpl.save(user);
		});
	}

}
