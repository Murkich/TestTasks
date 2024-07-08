package main.java.ru.clevertec.exception;

import main.java.ru.clevertec.file.writer.Writer;

/**
 * Класс InternalServerError предназначен для обработки других ситуаций.
 */
public class InternalServerError {
    private static final String INTERNAL_SERVER_MESSAGE = "ERROR\nINTERNAL SERVER ERROR\n";
    private static Writer objWriter;

    /**
     * Выводит сообщение об ошибке в консоль и записывает его в файл результатов.
     *
     * @param errorMessage сообщение об ошибке
     */
    public static void writeOtherError(String errorMessage) {
        System.out.println(INTERNAL_SERVER_MESSAGE + errorMessage);
        objWriter.writeError(INTERNAL_SERVER_MESSAGE + errorMessage);
    }

    /**
     * Присваивает объект Writer для написания ошибок в определенный файл
     *
     * @param objWriter объект Writer
     */
    public static void setObjWriter(Writer objWriter) {
        InternalServerError.objWriter = objWriter;
    }
}