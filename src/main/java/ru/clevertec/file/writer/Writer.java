package main.java.ru.clevertec.file.writer;

import main.java.ru.clevertec.model.cart.Cart;

/**
 * Интерфейс Writer определяет контракт для классов,
 * реализующих функциональность записи информации о корзине покупок.
 */
public interface Writer {
    /**
     * Записывает информацию о корзине покупок в соответствующий формат.
     *
     * @param cart объект Cart, содержащий информацию о корзине покупок
     */
    void writeResult(Cart cart);

    /**
     * Записывает информацию ою ошибке.
     *
     * @param errorMessage сообщение об ошибке
     */
    void writeError(String errorMessage);
}
