package main.java.ru.clevertec.exception;

import main.java.ru.clevertec.file.writer.CSVResultWriter;

/**
 * Класс InternalServerError предназначен для обработки других ситуаций.
 */
public class InternalServerError {
    private static final String INTERNAL_SERVER_MESSAGE = "ERROR\nINTERNAL SERVER ERROR\n";

    /**
     * Выводит сообщение об ошибке в консоль и записывает его в файл результатов.
     *
     * @param errorMessage сообщение об ошибке
     */
    public static void writeOtherError(String errorMessage) {
        System.out.println(INTERNAL_SERVER_MESSAGE + errorMessage);
        CSVResultWriter.writeError(INTERNAL_SERVER_MESSAGE + errorMessage);
    }
}