package main.java.ru.clevertec.model.cart;

import main.java.ru.clevertec.model.Product;

import java.math.BigDecimal;

public record CartItem(int quantity, Product product, BigDecimal discount, BigDecimal totalPrice) {}