package main.java.ru.clevertec.model.cart;

import main.java.ru.clevertec.model.Product;

import java.math.BigDecimal;

/**
 * CartItem представляет собой запись, которая содержит информацию о
 * количестве продукта в корзине, объект класса Product, представляющий сам продукт,
 * информацию о скидке на продукт и информацию об общей стоимости продукта с учетом количества и скидки.
 */
public record CartItem(int quantity, Product product, BigDecimal discount, BigDecimal totalPrice) {

}