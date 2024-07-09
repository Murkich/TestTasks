package main.java.ru.clevertec.exception;

import main.java.ru.clevertec.file.writer.Writer;

import java.io.File;
import java.util.Arrays;

/**
 * Класс BadRequest предназначен для проверки корректности входных данных и обработки ошибок.
 * Он содержит методы для валидации аргументов командной строки.
 */
public class BadRequest {
    private static final String BAD_REQUEST_PREFIX = "ERROR\nBAD REQUEST\n";
    private static final String DISCOUNT_CARD_PATTERN = "discountCard=\\d{4}";
    private static final String DEBIT_CARD_BALANCE_PATTERN = "balanceDebitCard=-?\\d+(\\.\\d{1,2})?$";
    private static final int OBLIGATORY_COUNT_ARGS = 4;
    private static final String PATH_TO_FILE = "pathToFile=";
    private static final String SAVE_TO_FILE = "saveToFile=";
    private static final String CSV_FILE = ".csv";
    private static Writer objWriter;

    /**
     * Проверяет корректность входных данных на основе аргументов командной строки.
     *
     * @param args аргументы командной строки
     * @return "OK", если входные данные корректны, или сообщение об ошибке
     */
    public static boolean validateInput(String[] args) {
        if (!containsFewArgs(args)) { return false; }
        if (!containsSaveToFile(args)) { return false; }
        if (!containsPathToFile(args)) { return false; }
        if (!containsProductQuantity(args)) { return false; }
        if (!containsValidDebitCardBalance(args)) { return false; }
        if (!containsValidDiscountCard(args)) { return false; }
        return true;
    }

    /**
     * Проверяет, содержит ли путь к файлу чтения продуктов.
     *
     * @param args аргументы командной строки
     * @return true, если путь к файлу указан, иначе false
     */
    public static boolean containsPathToFile(String[] args) {
        if (Arrays.stream(args).noneMatch(arg -> arg.contains(PATH_TO_FILE))) {
            writeBadRequest(BAD_REQUEST_PREFIX + "Writhe the " + PATH_TO_FILE);
            return false;
        }
        return checkValidNameFile(args);
    }

    /**
     * Проверяет, содержит ли путь к файлу сохранения для сохранения чека или ошибки.
     *
     * @param args аргументы командной строки
     * @return true, если путь к файлу указан, иначе false
     */
    public static boolean containsSaveToFile(String[] args) {
        if (Arrays.stream(args).noneMatch(arg -> arg.contains(SAVE_TO_FILE))) {
            writeBadRequest(BAD_REQUEST_PREFIX + "Writhe the " + SAVE_TO_FILE);
            return false;
        }
        return checkValidNameFile(args);
    }

    /**
     * Проверяет, расширение файла.
     *
     * @param args аргументы командной строки
     * @return true, если путь к файлу указан, иначе false
     */
    public static boolean checkValidNameFile(String[] args) {
        if (Arrays.stream(args).noneMatch(arg -> arg.toLowerCase().endsWith(CSV_FILE))) {
            writeBadRequest(BAD_REQUEST_PREFIX + "Writhe the " + SAVE_TO_FILE + " file");
            return false;
        }
        return true;
    }

    /**
     * Проверяет количество обязательных аргументов.
     *
     * @param args аргументы командной строки
     * @return true, если аргументов 4 или более, иначе false
     */
    public static boolean containsFewArgs(String[] args) {
        if (args.length < OBLIGATORY_COUNT_ARGS) {
            writeBadRequest(BAD_REQUEST_PREFIX + "Not enough arguments");
            return false;
        }
        return true;
    }

    /**
     * Проверяет, содержат ли аргументы командной строки информацию о продукте и его количестве.
     *
     * @param args аргументы командной строки
     * @return true, если информация о продукте и количестве присутствует, иначе false
     */
    public static boolean containsProductQuantity(String[] args) {
        if (Arrays.stream(args).noneMatch(arg -> arg.contains("-"))) {
            writeBadRequest(BAD_REQUEST_PREFIX + "Choose the product and quantity of product");
            return false;
        }
        return true;
    }

    /**
     * Проверяет, содержат ли аргументы командной строки корректный баланс дебетовой карты.
     *
     * @param args аргументы командной строки
     * @return true, если баланс дебетовой карты присутствует и имеет корректный формат, иначе false
     */
    public static boolean containsValidDebitCardBalance(String[] args) {
        if (Arrays.stream(args)
                .filter(arg -> arg.startsWith("balanceDebitCard="))
                .noneMatch(arg -> arg.matches(DEBIT_CARD_BALANCE_PATTERN))) {
            writeBadRequest(BAD_REQUEST_PREFIX + "Write the balanceDebitCard=xxxx or balanceDebitCard=xxxx.xx");
            return false;
        }
        return true;
    }

    /**
     * Проверяет, содержат ли аргументы командной строки корректный номер дисконтной карты.
     *
     * @param args аргументы командной строки
     * @return true, если номер дисконтной карты имеет корректный формат, иначе false
     */
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

    /**
     * Выводит сообщение об ошибке, если продукт не найден.
     */
    public static void productNotFound() {
        writeBadRequest(BAD_REQUEST_PREFIX + "Product not found");
    }

    /**
     * Проверяет, достаточно ли количество продукта на складе.
     *
     * @param description       наименование продукта
     * @param quantityInStock   количество продукта на складе
     * @param requestedQuantity запрошенное количество продукта
     * @return "OK", если количество продукта на складе достаточно, иначе сообщение об ошибке
     */
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

    /**
     * Выводит сообщение об ошибке в консоль и записывает его в файл результатов.
     *
     * @param errorMessage сообщение об ошибке
     */
    private static void writeBadRequest(String errorMessage) {
        System.out.println(errorMessage);
        objWriter.writeError(errorMessage);
    }

    /**
     * Присваивает объект Writer для написания ошибок в определенный файл
     *
     * @param objWriter объект Writer
     */
    public static void setObjWriter(Writer objWriter) {
        BadRequest.objWriter = objWriter;
    }
}