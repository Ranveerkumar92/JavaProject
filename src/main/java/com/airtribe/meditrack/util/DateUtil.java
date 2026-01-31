package com.airtribe.meditrack.util;

import com.airtribe.meditrack.constants.Constants;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utility class for date and time operations.
 */
public class DateUtil {
    
    private static final DateTimeFormatter DATE_FORMATTER = 
            DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
    private static final DateTimeFormatter DATETIME_FORMATTER = 
            DateTimeFormatter.ofPattern(Constants.DATETIME_FORMAT);
    
    /**
     * Parses a date string to LocalDate.
     *
     * @param dateString the date string in format yyyy-MM-dd
     * @return the parsed LocalDate
     * @throws DateTimeParseException if the string cannot be parsed
     */
    public static LocalDate parseDate(String dateString) throws DateTimeParseException {
        return LocalDate.parse(dateString, DATE_FORMATTER);
    }
    
    /**
     * Parses a date-time string to LocalDateTime.
     *
     * @param dateTimeString the date-time string in format yyyy-MM-dd HH:mm:ss
     * @return the parsed LocalDateTime
     * @throws DateTimeParseException if the string cannot be parsed
     */
    public static LocalDateTime parseDateTime(String dateTimeString) throws DateTimeParseException {
        return LocalDateTime.parse(dateTimeString, DATETIME_FORMATTER);
    }
    
    /**
     * Formats a LocalDate to string.
     *
     * @param date the LocalDate to format
     * @return the formatted date string
     */
    public static String formatDate(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }
    
    /**
     * Formats a LocalDateTime to string.
     *
     * @param dateTime the LocalDateTime to format
     * @return the formatted date-time string
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DATETIME_FORMATTER);
    }
    
    /**
     * Checks if a date is in the past.
     *
     * @param date the date to check
     * @return true if the date is in the past, false otherwise
     */
    public static boolean isInPast(LocalDate date) {
        return date.isBefore(LocalDate.now());
    }
    
    /**
     * Checks if a date is in the future.
     *
     * @param date the date to check
     * @return true if the date is in the future, false otherwise
     */
    public static boolean isInFuture(LocalDate date) {
        return date.isAfter(LocalDate.now());
    }
    
    /**
     * Checks if a date-time is in the past.
     *
     * @param dateTime the date-time to check
     * @return true if the date-time is in the past, false otherwise
     */
    public static boolean isInPast(LocalDateTime dateTime) {
        return dateTime.isBefore(LocalDateTime.now());
    }
    
    /**
     * Checks if a date-time is in the future.
     *
     * @param dateTime the date-time to check
     * @return true if the date-time is in the future, false otherwise
     */
    public static boolean isInFuture(LocalDateTime dateTime) {
        return dateTime.isAfter(LocalDateTime.now());
    }
}
