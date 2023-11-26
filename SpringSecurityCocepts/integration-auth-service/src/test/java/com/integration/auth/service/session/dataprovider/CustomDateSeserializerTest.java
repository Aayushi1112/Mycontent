package com.integration.auth.service.session.dataprovider;

import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.core.JsonParser;

import com.fasterxml.jackson.databind.DeserializationContext;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Date;

class CustomDateSeserializerTest {

	@Test
	void testDeserializeValidDate() throws IOException {
		// Arrange
		CustomDateDeserializer deserializer = new CustomDateDeserializer();
		JsonParser jsonParser = mock(JsonParser.class);
		DeserializationContext deserializationContext = mock(DeserializationContext.class);

		String dateString = "Tue Oct 25 13:24:45 UTC 2023";

		// Mock the behavior of the JsonParser
		when(jsonParser.getText()).thenReturn(dateString);

		// Act
		Date result = deserializer.deserialize(jsonParser, deserializationContext);

		// Assert
		assertNotNull(result);
		// Perform more specific date assertions here if needed
	}

	@Test
	void testDeserializeInvalidDate() throws IOException {
		// Arrange
		CustomDateDeserializer deserializer = new CustomDateDeserializer();
		JsonParser jsonParser = mock(JsonParser.class);
		DeserializationContext deserializationContext = mock(DeserializationContext.class);

		String invalidDateString = "This is not a valid date";

		// Mock the behavior of the JsonParser
		when(jsonParser.getText()).thenReturn(invalidDateString);

		// Act and Assert
		// Here, we expect an IOException to be thrown since the date is invalid.
		assertThrows(IOException.class, () -> {
			deserializer.deserialize(jsonParser, deserializationContext);
		});
	}

}
