package main.java.ru.clevertec.controller;

import main.java.ru.clevertec.exception.BadRequest;
import main.java.ru.clevertec.exception.InternalServerError;
import main.java.ru.clevertec.exception.NotEnoughMoney;
import main.java.ru.clevertec.model.cart.Cart;
import main.java.ru.clevertec.model.cart.CartBuilder;
import main.java.ru.clevertec.model.cart.CartFactoryMethod;
import main.java.ru.clevertec.model.DiscountCard;
import main.java.ru.clevertec.model.Product;
import main.java.ru.clevertec.services.CartPrinter;
import main.java.ru.clevertec.services.CartWriter;
import main.java.ru.clevertec.services.DebitCardCalculate;
import main.java.ru.clevertec.util.MoneyValidator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static main.java.ru.clevertec.constants.Constants.ROUND;

public class CheckController {
    public void createCheck(String[] userInput) {
        BigDecimal debitCardBalance;
        try {
            debitCardBalance = CommandLineArgumentsParser.getDebitCardBalanceFromCommandLineArguments(userInput).setScale(2, ROUND);
        } catch (NotEnoughMoney e) {
            // Исключение отрицательности баланса выведено на экран и записано в файл, выходим из программы
            return;
        }

        Map<Integer, Integer> productMap = CommandLineArgumentsParser.getIdProductAndQuantityFromCommandLine(userInput);
        List<Product> productList;
        try {
            productList = CommandLineArgumentsParser.getProductsFromMap(productMap);
        } catch (BadRequest | InternalServerError e) {
            // Исключение, связанное с чтением данных выведено на экран и записано в файл, выходим из программы
            return;
        }

        DiscountCard discountCard;
        try {
            discountCard = CommandLineArgumentsParser.getDiscountCardFromCommandLineArguments(userInput);
        } catch (InternalServerError e) {
            // Ошибка чтения из файла выведено на экран и записано в файл, выходим из программы
            return;
        }

        CartBuilder cartBuilder = new CartBuilder();
        cartBuilder.setDiscountCard(discountCard);

        for (Product product : productList) {
            cartBuilder.addProduct(productMap.get(product.getId()), product);
        }
        Cart cart = CartFactoryMethod.createCart(cartBuilder);

        try {
            MoneyValidator.validatePurchase(debitCardBalance, cart.getTotalWithDiscountAmount());
        } catch (NotEnoughMoney e) {
            return;
        }
        CartWriter.writeResult(cart);
        CartPrinter.showCart(debitCardBalance, cart);
        debitCardBalance = DebitCardCalculate.updateDebitCardBalance(debitCardBalance, cart.getTotalWithDiscountAmount());
    }
}
