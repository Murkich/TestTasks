package main.java.ru.clevertec.model.cart;

public class CartFactoryMethod {
    public static Cart createCart(CartBuilder cartBuilder) {
        if (cartBuilder == null)
            throw new IllegalArgumentException("CartBuilder cannot be null");

        return cartBuilder.build();
    }
}
