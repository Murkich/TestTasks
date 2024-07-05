package main.java.ru.clevertec.check;

import main.java.ru.clevertec.exception.BadRequest;
import main.java.ru.clevertec.exception.NotEnoughMoney;
import main.java.ru.clevertec.model.cart.*;
import main.java.ru.clevertec.model.discountcard.DiscountCard;
import main.java.ru.clevertec.model.products.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

/**
 * Класс CheckRunner является точкой входа в приложение и отвечает за обработку аргументов командной строки,
 * формирование корзины покупок, вывод информации о корзине и запись результатов в файл.
 */
public class CheckRunner {

    public static void main(String[] args) {
        // Проверка данных на валидность
        if (!BadRequest.validateInput(args)) return;

        // Получение баланса на счету пользователя
        BigDecimal debitCardBalance = CommandLineArgumentsParser.getDebitCardBalanceFromCommandLineArguments(args).setScale(2, RoundingMode.FLOOR);

        // Проверка на отрицательность баланса
        if (debitCardBalance.compareTo(BigDecimal.valueOf(-1)) == 0) {
            return;
        }

        // Получение продуктов и их количества
        Map<Integer, Integer> productMap = CommandLineArgumentsParser.getIdProductAndQuantityFromCommandLine(args);

        // Получение продуктов, которые выбрал пользователь
        List<Product> productList = CommandLineArgumentsParser.getProductsFromMap(productMap);
        if (productList == null) {
            return;
        }

        // Получение скидки с дисконтной карты
        DiscountCard discountCard = CommandLineArgumentsParser.getDiscountCardFromCommandLineArguments(args);

        // Формирование чека
        CartBuilder cartBuilder = new CartBuilder();
        cartBuilder.setDiscountCard(discountCard);

        // Добавляем продукты в корзину
        for (Product product : productList) {
            cartBuilder.addProduct(productMap.get(product.getId()), product);
        }

        Cart cart = CartFactoryMethod.createCart(cartBuilder);

        if (!NotEnoughMoney.getNotEnoughMoney(debitCardBalance, cart.getTotalWithDiscountAmount())) return;

        // Вывод и запись чека
        CartWriter.writeResult(cart);
        CartPrinter.showCart(debitCardBalance, cart);

        debitCardBalance = updateDebitCardBalance(debitCardBalance, cart.getTotalWithDiscountAmount());
    }

    /**
     * Обновляет баланс на счету пользователя после покупки.
     *
     * @param debitCardBalance        текущий баланс на счету пользователя
     * @param totalWithDiscountAmount итоговая сумма с учетом скидки
     * @return обновленный баланс на счету пользователя
     */
    private static BigDecimal updateDebitCardBalance(BigDecimal debitCardBalance, BigDecimal totalWithDiscountAmount) {
        return debitCardBalance.subtract(totalWithDiscountAmount).setScale(2, RoundingMode.FLOOR);
    }
}