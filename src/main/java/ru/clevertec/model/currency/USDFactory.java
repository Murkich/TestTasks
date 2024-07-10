package main.java.ru.clevertec.model.currency;

/**
 * Класс USDFactory реализует интерфейс CurrencyFactory и
 * предназначен для создания объектов валюты USD (доллар США).
 */
public class USDFactory implements CurrencyFactory {
    /**
     * Создает и возвращает новый объект валюты USD.
     *
     * @return новый объект валюты USD
     */
    @Override
    public Currency createCurrency() {
        return new USD();
    }
}