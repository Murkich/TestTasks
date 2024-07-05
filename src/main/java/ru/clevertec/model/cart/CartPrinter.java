package main.java.ru.clevertec.model.cart;

import main.java.ru.clevertec.model.discountcard.DiscountCard;
import main.java.ru.clevertec.util.currency.Currency;
import main.java.ru.clevertec.util.currency.USDFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Класс CartPrinter предназначен для вывода информации о корзине покупок в консоль.
 */
public class CartPrinter {
    /**
     * Выводит информацию о корзине покупок в консоль.
     *
     * @param userBalance баланс пользователя на дебетовой карте
     * @param cart        объект Cart, содержащий информацию о корзине покупок
     */
    public static void showCart(BigDecimal userBalance, Cart cart) {
        Currency usdCurrency = new USDFactory().createCurrency();

        System.out.println("┌────────────────────────────────────┐");
        System.out.println("│ Date and Time: " + cart.getCurrentDateTime() + " │");
        System.out.println("└────────────────────────────────────┘");
        System.out.println();

        printDiscountCard(cart.getDiscountCard());

        System.out.printf("%-20s%10s%10s%10s%12s\n", "Description", "Qty", "Price", "Discount", "Total");
        System.out.println("-----------------------------------------------------------------");
        for (CartItem cartItem : cart.getProductItemList()) {
            printCartItem(cartItem, usdCurrency);
        }
        System.out.println("-----------------------------------------------------------------");

        System.out.println();
        printTotalPrices(cart, usdCurrency);

        printBalances(userBalance, cart.getTotalWithDiscountAmount(), usdCurrency);
    }

    /**
     * Выводит информацию о дисконтной карте, если она присутствует.
     *
     * @param discountCard объект DiscountCard, представляющий дисконтную карту
     */
    private static void printDiscountCard(DiscountCard discountCard) {
        if (discountCard != null) {
            System.out.println("Discount Card: " + discountCard.getNumber());
        }
    }

    /**
     * Выводит информацию о продукте в корзине.
     *
     * @param cartItem объект CartItem, содержащий информацию о продукте
     * @param currency объект Currency, представляющий валюту
     */
    private static void printCartItem(CartItem cartItem, Currency currency) {
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

    /**
     * Выводит общую стоимость, скидку и итоговую сумму с учетом скидки.
     *
     * @param cart     объект Cart, содержащий информацию о корзине покупок
     * @param currency объект Currency, представляющий валюту
     */
    private static void printTotalPrices(Cart cart, Currency currency) {
        System.out.println("Total Price: " + cart.getTotalPrice() + currency.getSymbol());
        System.out.println("Total Discount: " + cart.getDiscountAmount() + currency.getSymbol());
        System.out.println("Total Price with Discount: " + cart.getTotalWithDiscountAmount() + currency.getSymbol());
        System.out.println();
    }

    /**
     * Выводит баланс пользователя до и после покупки.
     *
     * @param userBalance            баланс пользователя
     * @param totalWithDiscountAmount итоговая сумма с учетом скидки
     * @param currency                объект Currency, представляющий валюту
     */
    private static void printBalances(BigDecimal userBalance, BigDecimal totalWithDiscountAmount, Currency currency) {
        System.out.println("Your pre-purchase balance: " + userBalance + currency.getSymbol());
        System.out.println("Your post-purchase balance: " + userBalance.subtract(totalWithDiscountAmount).setScale(2, RoundingMode.FLOOR) + currency.getSymbol());
    }
}
