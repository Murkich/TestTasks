package main.java.ru.clevertec.services;

import main.java.ru.clevertec.model.cart.Cart;
import main.java.ru.clevertec.model.cart.CartItem;
import main.java.ru.clevertec.model.DiscountCard;
import main.java.ru.clevertec.model.currency.factory.Currency;
import main.java.ru.clevertec.model.currency.USDFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class CartPrinter {
    public static void showCart(BigDecimal userBalance, Cart cart) {
        Currency usdCurrency = new USDFactory().createCurrency();

        printDateTime(cart);
        printDiscountCard(cart.getDiscountCard());
        printCartItems(cart.getProductItemList(), usdCurrency);
        printTotalPrices(cart, usdCurrency);
        printBalances(userBalance, cart.getTotalWithDiscountAmount(), usdCurrency);
    }

    private static void printDateTime(Cart cart) {
        System.out.println("┌────────────────────────────────────┐");
        System.out.println("│ Date and Time: " + cart.getCurrentDateTime() + " │");
        System.out.println("└────────────────────────────────────┘");
        System.out.println();
    }

    private static void printDiscountCard(DiscountCard discountCard) {
        if (discountCard != null)
            System.out.println("Discount Card: " + discountCard.getNumber());
    }

    private static void printCartItems(List<CartItem> productItems, Currency currency) {
        System.out.printf("%-20s%10s%10s%10s%12s\n", "Description", "Qty", "Price", "Discount", "Total");
        System.out.println("-----------------------------------------------------------------");

        for (CartItem cartItem : productItems) {
            System.out.printf("%-20s%10d%8.2f%2s%8.2f%2s%10.2f%2s\n",
                    cartItem.product().getDescription(),
                    cartItem.quantity(),
                    cartItem.product().getPrice(),
                    currency.getSymbol(),
                    cartItem.discount(),
                    currency.getSymbol(),
                    cartItem.totalPrice(),
                    currency.getSymbol());
        }
        System.out.println("-----------------------------------------------------------------\n");
    }

    private static void printTotalPrices(Cart cart, Currency currency) {
        System.out.println("Total Price: " + cart.getTotalPrice() + currency.getSymbol());
        System.out.println("Total Discount: " + cart.getDiscountAmount() + currency.getSymbol());
        System.out.println("Total Price with Discount: " + cart.getTotalWithDiscountAmount() + currency.getSymbol());
        System.out.println();
    }

    private static void printBalances(BigDecimal userBalance, BigDecimal totalWithDiscountAmount, Currency currency) {
        System.out.println("Your pre-purchase balance: " + userBalance + currency.getSymbol());
        System.out.println("Your post-purchase balance: " + userBalance.subtract(totalWithDiscountAmount).setScale(2, RoundingMode.FLOOR) + currency.getSymbol());
    }
}
