package main.java.ru.clevertec.constants;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class Constants {
    public static final String RESULT_FILE = "./result.csv";
    public static final String PRODUCTS_FILE = "./src/main/resources/products.csv";
    public static final String DISCOUNT_CARDS_FILE = "./src/main/resources/discountCards.csv";
    public static final String ID_QTY_SPLIT_CHAR = "-";
    public static final String SPLIT_CHAR = "=";
    public static final String DISCOUNT_CARD_START = "discountCard=";
    public static final String BALANCE_CARD_START = "balanceDebitCard=";
    public static final String DISCOUNT_CARD_PATTERN = DISCOUNT_CARD_START + "\\d{4}";
    public static final String DEBIT_CARD_BALANCE_PATTERN = BALANCE_CARD_START + "-?\\d+(\\.\\d{1,2})?$";
    public static final String DELIMITER = ";";
    public static final String DATE_PATTERN = "dd.MM.yyyy";
    public static final String TIME_PATTERN = "hh:mm:ss";
    public static final BigDecimal WHOLESALE_DISCOUNT = BigDecimal.valueOf(0.1);
    public static final BigDecimal HUNDRED = BigDecimal.valueOf(100);
    public static final BigDecimal ZERO = BigDecimal.ZERO;
    public static final RoundingMode ROUND = RoundingMode.FLOOR;
    public static final BigDecimal DEFAULT_DISCOUNT = BigDecimal.valueOf(2).setScale(2, ROUND);
    public static final int PROMOTION_QUANTITY = 5;
}
