package main.java.ru.clevertec.model.cart;

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
            throw new IllegalArgumentException("CartBuilder cannot be null");
        }
        return cartBuilder.build();
    }
}
