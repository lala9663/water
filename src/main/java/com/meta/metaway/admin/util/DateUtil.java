package com.meta.metaway.admin.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String formatLocalDateTime(LocalDateTime localDateTime) {
    	if (localDateTime != null) {
    		return localDateTime.format(FORMATTER);
    	}else {
    		return "N/A";
    	}
    }

    public static LocalDateTime parseLocalDateTime(String dateTimeString) {
        return LocalDateTime.parse(dateTimeString, FORMATTER);
    }
}
