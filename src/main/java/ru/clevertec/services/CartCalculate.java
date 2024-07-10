package main.java.ru.clevertec.services;

import main.java.ru.clevertec.model.cart.CartItem;
import main.java.ru.clevertec.model.DiscountCard;

import java.math.BigDecimal;
import java.util.List;

import static main.java.ru.clevertec.constants.Constants.*;

public class CartCalculate {
    protected static BigDecimal calculateTotalProductPrice(int quantity, BigDecimal price) {
        return price.multiply(BigDecimal.valueOf(quantity)).setScale(2, ROUND);
    }

    protected static BigDecimal calculateDiscountProduct(int quantity,
                                                         boolean isWholesale,
                                                         BigDecimal totalProductPrice,
                                                         DiscountCard discountCard) {
        BigDecimal discountProduct;

        if (isWholesale && quantity >= PROMOTION_QUANTITY) {
            discountProduct = totalProductPrice.multiply(WHOLESALE_DISCOUNT).setScale(2, ROUND);
        } else if (discountCard != null) {
            BigDecimal discount = discountCard.getDiscount().divide(HUNDRED, 4, ROUND);
            discountProduct = totalProductPrice.multiply(discount).setScale(2, ROUND);
        } else {
            discountProduct = ZERO.setScale(2, ROUND);
        }
        return discountProduct;
    }

    protected static BigDecimal calculateDiscountAmount(List<CartItem> productItemList) {
        return productItemList.stream()
                .map(CartItem::discount)
                .reduce(ZERO, BigDecimal::add)
                .setScale(2, ROUND);
    }

    protected static BigDecimal calculateTotalPrice(List<CartItem> productItemList) {
        return productItemList.stream()
                .map(CartItem::totalPrice)
                .reduce(ZERO, BigDecimal::add)
                .setScale(2, ROUND);
    }

    protected BigDecimal calculateTotalWithDiscountAmount(BigDecimal totalPrice, BigDecimal discountAmount) {
        return totalPrice.subtract(discountAmount).setScale(2, ROUND);
    }
}
