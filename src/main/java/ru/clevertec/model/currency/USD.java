package main.java.ru.clevertec.model.currency;

import main.java.ru.clevertec.model.currency.factory.Currency;

public class USD implements Currency {
    @Override
    public String getSymbol() {
        return "$";
    }
}