package main.java.ru.clevertec.model.cart;

import main.java.ru.clevertec.model.DiscountCard;
import main.java.ru.clevertec.model.Product;
import main.java.ru.clevertec.services.CartCalculate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CartBuilder extends CartCalculate {
    private final List<CartItem> productItemList = new ArrayList<>();
    private DiscountCard discountCard;

    public void addProduct(int quantity, Product product) {
        BigDecimal totalProductPrice = CartCalculate.calculateTotalProductPrice(quantity, product.getPrice());
        BigDecimal discountProduct = CartCalculate.calculateDiscountProduct(quantity, product.isWholesale(), totalProductPrice, discountCard);
        productItemList.add(new CartItem(quantity, product, discountProduct, totalProductPrice));
    }

    public void setDiscountCard(DiscountCard discountCard) {
        this.discountCard = discountCard;
    }

    public Cart build() {
        BigDecimal totalPrice = calculateTotalPrice(productItemList);
        BigDecimal discountAmount = calculateDiscountAmount(productItemList);
        BigDecimal totalWithDiscountAmount = calculateTotalWithDiscountAmount(totalPrice, discountAmount);

        return new Cart(productItemList, discountCard, totalPrice, discountAmount, totalWithDiscountAmount);
    }
}
