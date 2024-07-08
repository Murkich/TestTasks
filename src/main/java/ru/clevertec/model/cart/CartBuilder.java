package main.java.ru.clevertec.model.cart;

import main.java.ru.clevertec.model.discountcard.DiscountCard;
import main.java.ru.clevertec.model.products.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс CartBuilder предназначен для построения объектов Cart с помощью builder.
 * Он содержит методы для добавления продуктов в корзину, установки дисконтной карты
 * и вычисления общей стоимости, скидки и итоговой суммы с учетом скидки.
 */
public class CartBuilder extends CartCalculate {
    private final List<CartItem> productItemList = new ArrayList<>();
    private DiscountCard discountCard;

    /**
     * Добавляет продукт в корзину.
     *
     * @param quantity количество продукта
     * @param product  объект Product, представляющий продукт
     */
    public void addProduct(int quantity, Product product) {
        BigDecimal totalProductPrice = CartCalculate.calculateTotalProductPrice(quantity, product.getPrice());
        BigDecimal discountProduct = CartCalculate.calculateDiscountProduct(quantity, product.isWholesale(), totalProductPrice, discountCard);

        productItemList.add(new CartItem(quantity, product, discountProduct, totalProductPrice));
    }

    /**
     * Устанавливает дисконтную карту для корзины.
     *
     * @param discountCard объект DiscountCard, представляющий дисконтную карту
     */
    public void setDiscountCard(DiscountCard discountCard) {
        this.discountCard = discountCard;
    }

    /**
     * Строит объект Cart на основе данных, хранящихся в CartBuilder.
     *
     * @return новый объект Cart
     */
    public Cart build() {
        BigDecimal totalPrice = calculateTotalPrice(productItemList);
        BigDecimal discountAmount = calculateDiscountAmount(productItemList);
        BigDecimal totalWithDiscountAmount = calculateTotalWithDiscountAmount(totalPrice, discountAmount);

        return new Cart(productItemList, discountCard, totalPrice, discountAmount, totalWithDiscountAmount);
    }
}
