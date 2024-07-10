package main.java.ru.clevertec.model.currency;

/**
 * Интерфейс CurrencyFactory определяет контракт для классов,
 * реализующих фабричный метод для создания объектов Currency.
 */
public interface CurrencyFactory {
    /**
     * Создает и возвращает новый объект Currency.
     *
     * @return новый объект Currency
     */
    Currency createCurrency();
}
