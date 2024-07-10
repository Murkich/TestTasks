package main.java.ru.clevertec.model.currency;

import main.java.ru.clevertec.model.currency.factory.Currency;
import main.java.ru.clevertec.model.currency.factory.CurrencyFactory;

public class USDFactory implements CurrencyFactory {
    @Override
    public Currency createCurrency() {
        return new USD();
    }
}