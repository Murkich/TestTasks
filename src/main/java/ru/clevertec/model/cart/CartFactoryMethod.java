package main.java.ru.clevertec.model.cart;

import main.java.ru.clevertec.exception.InternalServerError;

/**
 * Класс CartFactoryMethod содержит статический метод-фабрику для создания объектов Cart.
 */
public class CartFactoryMethod {
    /**
     * Создает объект Cart на основе данных, хранящихся в CartBuilder.
     *
     * @param cartBuilder объект CartBuilder, содержащий данные для создания Cart
     * @return новый объект Cart
     */
    public static Cart createCart(CartBuilder cartBuilder) {
        if (cartBuilder == null) {
            InternalServerError.writeOtherError("CartBuilder cannot be null");
            throw new IllegalArgumentException("CartBuilder cannot be null");
        }
        return cartBuilder.build();
    }
}
