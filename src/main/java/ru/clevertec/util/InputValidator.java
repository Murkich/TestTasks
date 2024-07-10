package main.java.ru.clevertec.util;

import main.java.ru.clevertec.exception.BadRequest;

import java.util.Arrays;

import static main.java.ru.clevertec.constants.Constants.*;

public class InputValidator {
    public static void validateInput(String[] args) throws BadRequest {
        containsSaveToFile(args);
        containsPathToFile(args);
        containsProductQuantity(args);
        containsValidDebitCardBalance(args);
        containsValidDiscountCard(args);
    }

    public static void containsPathToFile(String[] args) throws BadRequest {
        if (Arrays.stream(args).noneMatch(arg -> arg.contains(PATH_TO_FILE))) {
            throw new BadRequest(PATH_TO_FILE + " didn't write");
        }
        checkValidNameFile(args);
    }

    public static void containsSaveToFile(String[] args) throws BadRequest {
        if (Arrays.stream(args).noneMatch(arg -> arg.contains(SAVE_TO_FILE))) {
            throw new BadRequest(SAVE_TO_FILE + " didn't write");
        }
        checkValidNameFile(args);
    }

    private static void checkValidNameFile(String[] args) throws BadRequest {
        if (Arrays.stream(args).noneMatch(arg -> arg.toLowerCase().endsWith(CSV_FILE))) {
            throw new BadRequest("Error type of file");
        }
    }

    private static void containsProductQuantity(String[] args) throws BadRequest {
        if (Arrays.stream(args).noneMatch(arg -> arg.contains(ID_QTY_SPLIT_CHAR))) {
            throw new BadRequest("No product and quantity selected");
        }
    }

    private static void containsValidDebitCardBalance(String[] args) throws BadRequest {
        if (Arrays.stream(args)
                .filter(arg -> arg.startsWith(BALANCE_CARD_START))
                .noneMatch(arg -> arg.matches(DEBIT_CARD_BALANCE_PATTERN))) {
            throw new BadRequest("Debit card balance is not written or is written in the wrong format\n" +
                    "The correct format is balanceDebitCard=xxxx.xx");
        }
    }

    private static void containsValidDiscountCard(String[] args) throws BadRequest {
        if (Arrays.stream(args).anyMatch(arg -> arg.startsWith(DISCOUNT_CARD_START))) {
            if (Arrays.stream(args).noneMatch(arg -> arg.matches(DISCOUNT_CARD_PATTERN))) {
                throw new BadRequest("Incorrect format of the discount card.\n" +
                        "The correct format is discountCard=xxxx (xxxx - 4 numbers)");
            }
        }
    }
}
