package com.integration.auth.entrypoint.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SignupRequestTest {

	private Validator validator;

	@BeforeEach
	void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	void testSignupRequestValidation() {
		SignupRequest signupRequest = new SignupRequest();
		signupRequest.setUsername("us");
		signupRequest.setPassword("pass");
		signupRequest.setTenantId("testTenant");
		signupRequest.setNativeSystem("nativeSystem");

		Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupRequest);

		assertEquals(2, violations.size());

		for (ConstraintViolation<SignupRequest> violation : violations) {
			String message = violation.getMessage();
			String propertyPath = violation.getPropertyPath().toString();

			switch (propertyPath) {
				case "username":
					assertEquals("size must be between 3 and 20", message);
					break;
				case "password":
					assertEquals("size must be between 6 and 40", message);
					break;
				default:
					break;
			}
		}
	}

	@Test
	void testValidSignupRequest() {
		SignupRequest signupRequest = new SignupRequest();
		signupRequest.setUsername("validUser");
		signupRequest.setPassword("validPassword123");
		signupRequest.setTenantId("testTenant");
		signupRequest.setNativeSystem("nativeSystem");

		Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupRequest);

		assertEquals(0, violations.size());
	}

}
