package main.java.ru.clevertec.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static main.java.ru.clevertec.constants.Constants.*;

public class DateTimeUtils {
    private static final String DATE_TIME_FORMAT = DATE_PATTERN + DELIMITER + TIME_PATTERN;

    public static String getCurrentDateTime() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        return currentDateTime.format(formatter);
    }
}
