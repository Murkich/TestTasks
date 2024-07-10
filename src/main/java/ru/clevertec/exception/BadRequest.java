package main.java.ru.clevertec.exception;

import main.java.ru.clevertec.file.writer.factory.Writer;
import main.java.ru.clevertec.file.writer.CSVResultWriter;

public class BadRequest extends Exception {
    private static final String BAD_REQUEST_PREFIX = "ERROR\nBAD REQUEST\n";
    private static Writer objWriter;

    public BadRequest(String message) {
        super(BAD_REQUEST_PREFIX + message);
        System.out.println(BAD_REQUEST_PREFIX + message);
        CSVResultWriter.writeError(BAD_REQUEST_PREFIX + message);
    }

    public static void setObjWriter(Writer objWriter) {
        BadRequest.objWriter = objWriter;
    }
}