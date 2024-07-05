package main.java.ru.clevertec.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Класс DateTimeUtils предоставляет методы для работы с датой и временем.
 */
public class DateTimeUtils {
    private static final String DATE_TIME_FORMAT = "dd-MM-yyyy;HH:mm:ss";

    /**
     * Возвращает текущую дату и время в формате "dd-MM-yyyy;HH:mm:ss".
     *
     * @return текущая дата и время в строковом представлении
     */
    public static String getCurrentDateTime() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        return currentDateTime.format(formatter);
    }
}
