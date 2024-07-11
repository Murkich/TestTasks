package main.java.ru.clevertec.exception;

import main.java.ru.clevertec.file.writer.CSVResultWriter;

public class InternalServerError extends Exception {
    private static final String INTERNAL_SERVER_MESSAGE = "ERROR\nINTERNAL SERVER ERROR\n";

    public InternalServerError(String message) {
        super(INTERNAL_SERVER_MESSAGE + message);
        System.out.println(INTERNAL_SERVER_MESSAGE + message);
        CSVResultWriter.writeError(INTERNAL_SERVER_MESSAGE + message);
    }
}