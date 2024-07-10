package main.java.ru.clevertec.exception;

import main.java.ru.clevertec.file.writer.Writer;

import java.io.File;
import java.util.Arrays;

import static main.java.ru.clevertec.constants.Constants.*;

public class BadRequest {
    private static final String BAD_REQUEST_PREFIX = "ERROR\nBAD REQUEST\n";
    private static Writer objWriter;

    public static boolean validateInput(String[] args) {
        if (!containsSaveToFile(args)) { return false; }
        if (!containsPathToFile(args)) { return false; }
        if (!containsProductQuantity(args)) { return false; }
        if (!containsValidDebitCardBalance(args)) { return false; }
        if (!containsValidDiscountCard(args)) { return false; }
        return true;
    }

    public static boolean containsPathToFile(String[] args) {
        if (Arrays.stream(args).noneMatch(arg -> arg.contains(PATH_TO_FILE))) {
            writeBadRequest(BAD_REQUEST_PREFIX + "Writhe the " + PATH_TO_FILE);
            return false;
        }
        return checkValidNameFile(args);
    }

    public static boolean containsSaveToFile(String[] args) {
        if (Arrays.stream(args).noneMatch(arg -> arg.contains(SAVE_TO_FILE))) {
            writeBadRequest(BAD_REQUEST_PREFIX + "Writhe the " + SAVE_TO_FILE);
            return false;
        }
        return checkValidNameFile(args);
    }

    public static boolean checkValidNameFile(String[] args) {
        if (Arrays.stream(args).noneMatch(arg -> arg.toLowerCase().endsWith(CSV_FILE))) {
            writeBadRequest(BAD_REQUEST_PREFIX + "Writhe the " + SAVE_TO_FILE + " file");
            return false;
        }
        return true;
    }

    public static boolean containsProductQuantity(String[] args) {
        if (Arrays.stream(args).noneMatch(arg -> arg.contains("-"))) {
            writeBadRequest(BAD_REQUEST_PREFIX + "Choose the product and quantity of product");
            return false;
        }
        return true;
    }

    public static boolean containsValidDebitCardBalance(String[] args) {
        if (Arrays.stream(args)
                .filter(arg -> arg.startsWith("balanceDebitCard="))
                .noneMatch(arg -> arg.matches(DEBIT_CARD_BALANCE_PATTERN))) {
            writeBadRequest(BAD_REQUEST_PREFIX + "Write the balanceDebitCard=xxxx or balanceDebitCard=xxxx.xx");
            return false;
        }
        return true;
    }

    public static boolean containsValidDiscountCard(String[] args) {
        if (!(Arrays.stream(args)
                .filter(arg -> arg.startsWith("discountCard="))
                .peek(arg -> {
                    if (!arg.matches(DISCOUNT_CARD_PATTERN)) {
                        writeBadRequest(BAD_REQUEST_PREFIX + "Write the discountCard=xxxx");
                    }
                })
                .count() <= 1)) {
            writeBadRequest(BAD_REQUEST_PREFIX + "Write the discountCard=xxxx");
            return false;
        }
        return true;
    }

    public static void productNotFound() {
        writeBadRequest(BAD_REQUEST_PREFIX + "Product not found");
    }

    public static boolean quantityNotEnoughInStock(String description,
                                                   int quantityInStock,
                                                   int requestedQuantity) {
        if (requestedQuantity <= quantityInStock) {
            return true;
        }
        writeBadRequest(BAD_REQUEST_PREFIX + "Quantity of " + description + " not enough in stock");
        return false;
    }

    public static boolean fileExist(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            writeBadRequest(BAD_REQUEST_PREFIX + "Write the correct path to the file");
        }
        return file.exists();
    }

    private static void writeBadRequest(String errorMessage) {
        System.out.println(errorMessage);
        objWriter.writeError(errorMessage);
    }

    public static void setObjWriter(Writer objWriter) {
        BadRequest.objWriter = objWriter;
    }
}