package main.java.ru.clevertec.util;

import main.java.ru.clevertec.exception.NotEnoughMoney;

import java.math.BigDecimal;

import static main.java.ru.clevertec.constants.Constants.ZERO;


public class MoneyValidator {

    public static BigDecimal validateAmount(BigDecimal money) throws NotEnoughMoney {
        if (money.compareTo(ZERO) <= 0)
            throw new NotEnoughMoney("");

        return money;
    }

    public static void validatePurchase(BigDecimal availableAmount, BigDecimal totalPrice) throws NotEnoughMoney {
        if (availableAmount.compareTo(totalPrice) >= 0)
            return;

        throw new NotEnoughMoney("");
    }
}
