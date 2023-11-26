package com.integration.auth.entrypoint;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.integration.auth.common.errors.Errors;
import com.integration.auth.entrypoint.validators.AuthServiceValidator;
import com.integration.auth.service.auth.exceptions.AuthServiceException;

@ExtendWith(MockitoExtension.class)
class ModuleMappingEntryPointTest {

	@Mock
	AuthServiceValidator dataInputValidator;

	@InjectMocks
	ModuleMappingEntryPoint moduleMappingEntryPoint;

	@Test
	void moduleMappingTestSuccess() {

		String TENANT_NAME = "exampleTenant";
		String USER_NAME = "exampleUser";
		String TARGET_SYSTEM = "exampleSystem";
		String JSON_DATA = "exampleJsonData";
		ResponseEntity<String> responseEntity = moduleMappingEntryPoint.moduleMapping(TENANT_NAME, USER_NAME,
				TARGET_SYSTEM);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

	}

	@Test
	void testModuleMapping() {
		String tenantName = "testTenant";
		String userName = "testUser";
		String targetSystem = "testSystem";

		doNothing().when(dataInputValidator).validateInputToken(tenantName, userName, targetSystem);

		ResponseEntity<String> response = moduleMappingEntryPoint.moduleMapping(tenantName, userName, targetSystem);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	void testModuleMappingWithAuthServiceException() {
		String tenantName = "testTenant";
		String userName = "testUser";
		String targetSystem = "testSystem";

		doThrow(new AuthServiceException(new Errors(targetSystem, targetSystem, 0))).when(dataInputValidator)
				.validateInputToken(tenantName, userName, targetSystem);

		ResponseEntity<String> response = moduleMappingEntryPoint.moduleMapping(tenantName, userName, targetSystem);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

}
