package com.integration.auth.common.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class GsonUTCDateAdapter implements JsonSerializer<Date> {

	private final DateFormat dateFormat;

	private static final String UTC_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public GsonUTCDateAdapter(String timeZone) {
		dateFormat = new SimpleDateFormat(UTC_DATE_FORMAT);
		dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
	}

	@Override
	public synchronized JsonElement serialize(Date date, Type type, JsonSerializationContext jsonSerializationContext) {
		return new JsonPrimitive(dateFormat.format(date));
	}

}
