package main.java.ru.clevertec.model.currency;

/**
 * Интерфейс Currency определяет контракт для классов,
 * реализующих функциональность возврата символа валюты.
 */
public interface Currency {
    /**
     * Возвращает символ валюты в соответствующем формате.
     *
     * @return символ валюты
     */
    String getSymbol();
}
