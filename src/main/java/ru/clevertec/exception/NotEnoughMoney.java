package main.java.ru.clevertec.exception;

import main.java.ru.clevertec.file.writer.CSVResultWriter;

public class NotEnoughMoney extends Exception {
    private static final String NOT_ENOUGH_MONEY_MESSAGE = "ERROR\nNOT ENOUGH MONEY\n";

    public NotEnoughMoney(String message) {
        System.out.println(NOT_ENOUGH_MONEY_MESSAGE + message);
        CSVResultWriter.writeError(NOT_ENOUGH_MONEY_MESSAGE + message);
    }
}
