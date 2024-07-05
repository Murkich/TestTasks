package main.java.ru.clevertec.model.cart;

import main.java.ru.clevertec.model.discountcard.DiscountCard;
import main.java.ru.clevertec.util.DateTimeUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * Класс Cart представляет корзину покупок, содержащую список продуктов, информацию о дисконтной карте,
 * общую стоимость, скидку и итоговую сумму с учетом скидки.
 */
public class Cart {
    private final String currentDateTime;
    private final List<CartItem> productItemList;
    private final DiscountCard discountCard;
    private final BigDecimal totalPrice;
    private final BigDecimal discountAmount;
    private final BigDecimal totalWithDiscountAmount;

    /**
     * Конструктор класса Cart.
     *
     * @param productItemList         список продуктов в корзине
     * @param discountCard            дисконтная карта
     * @param totalPrice              общая стоимость всех продуктов
     * @param discountAmount          общая сумма скидки
     * @param totalWithDiscountAmount итоговая сумма с учетом скидки
     */
    public Cart(List<CartItem> productItemList, DiscountCard discountCard, BigDecimal totalPrice, BigDecimal discountAmount, BigDecimal totalWithDiscountAmount) {
        this.currentDateTime = DateTimeUtils.getCurrentDateTime();
        this.productItemList = List.copyOf(productItemList);
        this.discountCard = discountCard;
        this.totalPrice = totalPrice;
        this.discountAmount = discountAmount;
        this.totalWithDiscountAmount = totalWithDiscountAmount;
    }

    /**
     * Getter methods
     */
    public String getCurrentDateTime() {
        return currentDateTime;
    }

    public List<CartItem> getProductItemList() {
        return List.copyOf(productItemList);
    }

    public DiscountCard getDiscountCard() {
        return discountCard;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public BigDecimal getTotalWithDiscountAmount() {
        return totalWithDiscountAmount;
    }
}
