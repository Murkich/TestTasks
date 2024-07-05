package main.java.ru.clevertec.model.cart;

import main.java.ru.clevertec.model.discountcard.DiscountCard;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Класс CartCalculate содержит статические методы для вычисления различных значений,
 * связанных с корзиной покупок, таких как общая стоимость продуктов, скидка и итоговая сумма с учетом скидки.
 */
class CartCalculate {
    /**
     * Вычисляет общую стоимость продукта на основе его количества и цены.
     *
     * @param quantity количество продукта
     * @param price    цена продукта
     * @return общая стоимость продукта
     */
    protected static BigDecimal calculateTotalProductPrice(int quantity, BigDecimal price) {
        return price.multiply(BigDecimal.valueOf(quantity)).setScale(2, RoundingMode.FLOOR);
    }

    /**
     * Вычисляет скидку на продукт на основе его количества, признака оптовой продажи,
     * общей стоимости и дисконтной карты.
     *
     * @param quantity          количество продукта
     * @param isWholesale       признак оптовой продажи продукта
     * @param totalProductPrice общая стоимость продукта
     * @param discountCard      дисконтная карта
     * @return скидка на продукт
     */
    protected static BigDecimal calculateDiscountProduct(int quantity, boolean isWholesale, BigDecimal totalProductPrice, DiscountCard discountCard) {
        BigDecimal discountProduct;
        if (isWholesale && quantity >= 5) {
            discountProduct = totalProductPrice.multiply(BigDecimal.valueOf(0.1)).setScale(2, RoundingMode.FLOOR);
        } else if (discountCard != null) {
            discountProduct = totalProductPrice.multiply(discountCard.getDiscount().divide(BigDecimal.valueOf(100), 4, RoundingMode.FLOOR)).setScale(2, RoundingMode.FLOOR);
        } else {
            discountProduct = BigDecimal.ZERO.setScale(2, RoundingMode.FLOOR);
        }
        return discountProduct;
    }

    /**
     * Вычисляет общую сумму скидки для всех продуктов в корзине.
     *
     * @return общая сумма скидки
     */
    protected static BigDecimal calculateDiscountAmount(List<CartItem> productItemList) {
        return productItemList.stream()
                .map(CartItem::discount)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.FLOOR);
    }

    /**
     * Вычисляет общую стоимость всех продуктов в корзине.
     *
     * @return общая стоимость всех продуктов
     */
    protected static BigDecimal calculateTotalPrice(List<CartItem> productItemList) {
        return productItemList.stream()
                .map(CartItem::totalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.FLOOR);
    }

    /**
     * Вычисляет итоговую сумму с учетом скидки.
     *
     * @param totalPrice     общая стоимость всех продуктов
     * @param discountAmount общая сумма скидки
     * @return итоговая сумма с учетом скидки
     */
    protected BigDecimal calculateTotalWithDiscountAmount(BigDecimal totalPrice, BigDecimal discountAmount) {
        return totalPrice.subtract(discountAmount).setScale(2, RoundingMode.FLOOR);
    }
}
