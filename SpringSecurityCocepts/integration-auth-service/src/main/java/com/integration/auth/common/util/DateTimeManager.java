package com.integration.auth.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeManager {

	private DateTimeManager() {

	}

	private static final String UTC_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.zzz";

	public static final String GMT = "GMT";

	public static final String DB_DATE_FORMAT = "yyyy-MM-dd";

	public static final String DB_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.S";

	/**
	 * Create current date time stamp with specific format yyyy-MM-dd'T'HH:mm:ss.zzz" to
	 * return in the API responses.
	 * @return - String representation for the date.
	 */
	public static String currentTimeStampStringInUTC() {
		final Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat(UTC_DATE_TIME_FORMAT);
		sdf.setTimeZone(TimeZone.getTimeZone(GMT));
		return sdf.format(date);
	}

	public static Date currentTimeStampDateInUTC() {
		final Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat(UTC_DATE_TIME_FORMAT);
		sdf.setTimeZone(TimeZone.getTimeZone(GMT));
		try {
			return sdf.parse(sdf.format(date));
		}
		catch (ParseException e) {
			return null;
		}
	}

	public static boolean isDateTimeFormatInUTC(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat(UTC_DATE_TIME_FORMAT);
		sdf.setTimeZone(TimeZone.getTimeZone(GMT));
		sdf.setLenient(false);
		try {
			sdf.parse(dateStr);
		}
		catch (ParseException e) {
			return false;
		}
		return true;
	}

	/**
	 * validate if string dateStr has valid datetime format as specified in dateTimeFormat
	 * @param dateStr
	 * @param dateTimeFormat
	 * @return
	 */
	public static boolean isValidDateTimeFormat(String dateStr, String dateTimeFormat) {
		DateFormat formatter = new SimpleDateFormat(dateTimeFormat);
		formatter.setLenient(false);
		try {
			formatter.parse(dateStr);
		}
		catch (ParseException e) {
			return false;
		}
		return true;
	}

	/**
	 * Convert a string date in given time zone to UTC time zone & format date object
	 * @param dateStr
	 * @param userTimeZone
	 * @return
	 * @throws ParseException
	 */
	public static Date parseStringToDateFromUserToUTCTimeZone(String dateStr, String userTimeZone)
			throws ParseException {
		Date userDate = convertStringToDate(dateStr, userTimeZone);
		return convertDateToUTCDate(userDate);
	}

	private static Date convertDateToUTCDate(Date userDate) throws ParseException {
		DateFormat formatter = new SimpleDateFormat(UTC_DATE_TIME_FORMAT);
		formatter.setTimeZone(TimeZone.getTimeZone(GMT));
		String formattedDate = formatter.format(userDate);
		return formatter.parse(formattedDate);
	}

	private static Date convertStringToDate(String dateStr, String userTimeZone) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		LocalDateTime localDateTime = LocalDateTime.parse(dateStr, dateTimeFormatter);
		ZonedDateTime zonedDateTime = localDateTime.atZone(getZoneId(userTimeZone));
		return Date.from(zonedDateTime.toInstant());
	}

	public static Date parseDateStringInUTCFormatDate(String dateStr) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(UTC_DATE_TIME_FORMAT);
		sdf.setTimeZone(TimeZone.getTimeZone(GMT));
		sdf.setLenient(false);
		return sdf.parse(dateStr);
	}

	public static String parseDateInUTCFormatString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(UTC_DATE_TIME_FORMAT);
		sdf.setTimeZone(TimeZone.getTimeZone(GMT));
		sdf.setLenient(false);
		return sdf.format(date);
	}

	public static Date parseDateInDBDateTimeFormatDate(Date inputDate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(DateTimeFormats.DB_DATETIME_FORMAT);
		return sdf.parse(sdf.format(inputDate));
	}

	public static boolean isValidDate(Date date) {
		return (date != null && date.toString().trim().length() != 0 && !date.toString().trim().equalsIgnoreCase("null")
				&& !date.toString().trim().equals("-1") && !date.toString().trim().equals("0000-00-00 00:00:00"));
	}

	public static String currentDateRequiredFomat(String formatString) {
		final Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat(formatString);
		return sdf.format(date);
	}

	public static Instant getTodayStartTimestampInUTC(String timeZone) {
		ZoneId zoneId = getZoneId(timeZone);
		LocalDate today = LocalDate.now(zoneId);
		ZonedDateTime zdtStart = today.atStartOfDay(zoneId);
		return zdtStart.toInstant();
	}

	public static Instant getTodayEndTimestampInUTC(String timeZone) {
		ZoneId zoneId = getZoneId(timeZone);
		LocalDate today = LocalDate.now(zoneId);
		ZonedDateTime zdtStart = today.plusDays(1).atStartOfDay(zoneId);
		return zdtStart.toInstant();
	}

	public static Date parseStringDateToUTCTimeZone(String dateStr, String userTimeZone) throws ParseException {
		Date userDate = convertStringDateToDate(dateStr, userTimeZone);
		return convertDateToUTCDateWithoutTime(userDate);
	}

	private static Date convertDateToUTCDateWithoutTime(Date userDate) throws ParseException {
		DateFormat formatter = new SimpleDateFormat(DB_DATE_FORMAT);
		formatter.setTimeZone(TimeZone.getTimeZone(GMT));
		String formattedDate = formatter.format(userDate);
		return formatter.parse(formattedDate);
	}

	private static Date convertStringDateToDate(String dateStr, String userTimeZone) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DB_DATE_TIME_FORMAT);
		LocalDateTime localDateTime = LocalDateTime.parse(dateStr, dateTimeFormatter);
		ZonedDateTime zonedDateTime = localDateTime.atZone(getZoneId(userTimeZone));
		return Date.from(zonedDateTime.toInstant());
	}

	public static Date parseStringDateToUTCTimeZoneForDateField(String dateStr, String userTimeZone)
			throws ParseException {
		Date userDate = convertStringDateToDateForDateField(dateStr, userTimeZone);
		return convertDateToUTCDateWithoutTime(userDate);
	}

	private static Date convertStringDateToDateForDateField(String dateStr, String userTimeZone) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DB_DATE_FORMAT);
		LocalDate localDate = LocalDate.parse(dateStr, dateTimeFormatter);
		LocalDateTime localDateTime = localDate.atStartOfDay();
		ZonedDateTime zonedDateTime = localDateTime.atZone(getZoneId(userTimeZone));
		return Date.from(zonedDateTime.toInstant());
	}

	private static ZoneId getZoneId(String timeZone) {
		try {
			return ZoneId.of(timeZone);
		}
		catch (NullPointerException | DateTimeException ex) {
			return ZoneId.of(GMT);
		}
	}

	public static final class DateTimeFormats {

		private DateTimeFormats() {

		}

		public static final String DB_DATETIME_FORMAT = "yyyy-MM-dd HH:mm";

	}

}
