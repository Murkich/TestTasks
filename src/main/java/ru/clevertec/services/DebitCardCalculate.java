package main.java.ru.clevertec.services;

import java.math.BigDecimal;

import static main.java.ru.clevertec.constants.Constants.ROUND;

public class DebitCardCalculate {
    public static BigDecimal updateDebitCardBalance(BigDecimal debitCardBalance, BigDecimal totalWithDiscountAmount) {
        return debitCardBalance.subtract(totalWithDiscountAmount).setScale(2, ROUND);
    }
}
