package com.integration.auth.common.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.TimeZone;

import org.junit.jupiter.api.Test;

class DateTimeManagerTest {

	private static final String UTC_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.zzz";

	static final String GMT = "GMT";

	static final String DB_DATE_FORMAT = "yyyy-MM-dd";

	static final String DB_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.S";

	@Test
	void testCurrentTimeStampStringInUTC() {
		// Set the expected date format
		final String expectedDateFormat = "yyyy-MM-dd'T'HH:mm:ss.zzz";

		// Call the method to be tested
		String result = DateTimeManager.currentTimeStampStringInUTC();

		// Get the current date and time in UTC using the expected format
		SimpleDateFormat sdf = new SimpleDateFormat(expectedDateFormat);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		String expected = sdf.format(new Date(System.currentTimeMillis()));

		// Assert that the result matches the expected date and time string
		assertEquals(expected, result);
	}

	@Test
	void testCurrentTimeStampDateInUTC() throws ParseException {
		// Set the expected date format
		final String expectedDateFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'";

		// Call the method to be tested
		Date result = DateTimeManager.currentTimeStampDateInUTC();

		// Get the current date and time in UTC using the expected format
		SimpleDateFormat sdf = new SimpleDateFormat(expectedDateFormat);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		String dateStr = sdf.format(new Date(System.currentTimeMillis()));

		// Parse the expected date string back to a Date object
		Date expected = sdf.parse(dateStr);

		// Assert that the result matches the expected Date object
		assertEquals(expected, result);
	}

	@Test
	void testInvalidUTCDateFormat() {
		String invalidDateStr = "2023-10-23 12:34:56";
		boolean result = DateTimeManager.isDateTimeFormatInUTC(invalidDateStr);
		assertFalse(result);
	}

	@Test
	void testParseStringToDateFromUserToUTCTimeZone() throws ParseException {
		String dateStr = "2023-10-23T12:34:56";
		String userTimeZone = "America/New_York";

		Date result = DateTimeManager.parseStringToDateFromUserToUTCTimeZone(dateStr, userTimeZone);

		// Perform assertions on the result
		// You can convert the result back to a string in UTC format for comparison
		SimpleDateFormat sdf = new SimpleDateFormat(UTC_DATE_TIME_FORMAT);
		sdf.setTimeZone(TimeZone.getTimeZone(DateTimeManager.GMT));
		String expectedResultStr = sdf.format(result);

		assertEquals("2023-10-23T16:34:56.GMT", expectedResultStr);
	}

	@Test
	void testInvalidDateStr() throws ParseException {
		String invalidDateStr = "invalid-date";
		String userTimeZone = "America/New_York";

		assertThrows(Exception.class,
				() -> DateTimeManager.parseStringToDateFromUserToUTCTimeZone(invalidDateStr, userTimeZone));
	}

	@Test
	void testInValidUTCDateFormat() throws ParseException {
		String validDateStr = "2023-10-23T12:34:56.Z";

		assertThrows(ParseException.class, () -> {
			DateTimeManager.parseDateStringInUTCFormatDate(validDateStr);
		});
	}

	@Test
	void testFormatValidDateToUTCFormat() throws ParseException {
		// Create a Date object with a known timestamp (in milliseconds)
		Date validDate = new Date(1634966096000L); // For example, "2022-10-23T12:34:56Z"

		String result = DateTimeManager.parseDateInUTCFormatString(validDate);

		// Check if the result matches the expected UTC date-time string
		assertEquals("2021-10-23T05:14:56.GMT", result);
	}

	@Test
	void testFormatCurrentDateToUTCFormat() {
		// Get the current date and time as a Date object
		Date currentDate = new Date();

		String result = DateTimeManager.parseDateInUTCFormatString(currentDate);

		// Create a SimpleDateFormat with the same UTC date-time format
		SimpleDateFormat sdf = new SimpleDateFormat(UTC_DATE_TIME_FORMAT);
		sdf.setTimeZone(TimeZone.getTimeZone(DateTimeManager.GMT));

		// Format the current date and time
		String formattedResult = sdf.format(currentDate);

		// Check if the result matches the formatted current date and time
		assertEquals(formattedResult, result);
	}

	@Test
	void testFormatAndParseValidDate() throws ParseException {
		// Create a Date object with a known timestamp (in milliseconds)
		Date inputDate = new Date(1634966096000L); // For example, "2022-10-23 12:34:56"

		Date result = DateTimeManager.parseDateInDBDateTimeFormatDate(inputDate);
		Date expected = new Date(1634966040000L);
		// Check if the result matches the input Date object
		assertEquals(expected, result);
	}

	@Test
	void testValidDate() {
		// Create a valid Date object (e.g., the current date and time)
		Date validDate = new Date();

		boolean result = DateTimeManager.isValidDate(validDate);

		// Assert that the result is true for a valid Date
		assertTrue(result);
	}

	@Test
	void testCurrentDateWithDateFormat() {
		String formatString = "yyyy-MM-dd HH:mm:ss";

		String result = DateTimeManager.currentDateRequiredFomat(formatString);

		SimpleDateFormat sdf = new SimpleDateFormat(formatString);
		String expected = sdf.format(new Date(System.currentTimeMillis()));

		assertEquals(expected, result);
	}

	@Test
	void testGetTodayStartTimestampInUTCWithDefaultTimeZone() {
		String timeZone = "UTC";

		Instant result = DateTimeManager.getTodayStartTimestampInUTC(timeZone);

		// Calculate the expected result based on the current date in UTC
		ZoneId zoneId = ZoneId.of("UTC");
		LocalDate today = LocalDate.now(zoneId);
		ZonedDateTime zdtStart = today.atStartOfDay(zoneId);
		Instant expected = zdtStart.toInstant();

		assertEquals(expected, result);
	}

	@Test
	void testGetTodayEndTimestampInUTCWithDefaultTimeZone() {
		String timeZone = "UTC";

		Instant result = DateTimeManager.getTodayEndTimestampInUTC(timeZone);

		// Calculate the expected result based on the current date in UTC
		ZoneId zoneId = ZoneId.of("UTC");
		LocalDate today = LocalDate.now(zoneId);
		ZonedDateTime zdtEnd = today.plusDays(1).atStartOfDay(zoneId);
		Instant expected = zdtEnd.toInstant();

		assertEquals(expected, result);
	}

}
