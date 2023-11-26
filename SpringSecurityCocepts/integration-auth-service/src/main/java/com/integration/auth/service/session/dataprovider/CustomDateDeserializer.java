package com.integration.auth.service.session.dataprovider;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDateDeserializer extends JsonDeserializer<Date> {

	private SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");

	@Override
	public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
		String dateString = jsonParser.getText();
		try {
			return dateFormat.parse(dateString);
		}
		catch (ParseException e) {
			throw new IOException("Failed to parse date: " + dateString, e);
		}
	}

}
