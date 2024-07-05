package main.java.ru.clevertec.file.writer;

import main.java.ru.clevertec.model.cart.Cart;
import main.java.ru.clevertec.model.cart.CartItem;
import main.java.ru.clevertec.util.currency.Currency;
import main.java.ru.clevertec.util.currency.USDFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Класс CSVResultWriter предназначен для записи результатов покупки в CSV-файл.
 */
public class CSVResultWriter implements Writer {
    private static final String RESULT_FILE = "./result.csv";

    /**
     * Записывает информацию о покупке в CSV-файл.
     *
     * @param cart объект Cart, содержащий информацию о покупке
     */
    @Override
    public void writeResult(Cart cart) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RESULT_FILE))) {
            USDFactory usdFactory = new USDFactory();
            Currency usdCurrency = usdFactory.createCurrency();

            // Записываем дату и время
            bw.write("DATE;TIME");
            bw.newLine();
            bw.write(cart.getCurrentDateTime());
            bw.newLine();
            bw.newLine();

            // Записываем заголовок для продуктов
            bw.write("QTY;DESCRIPTION;PRICE;DISCOUNT;TOTAL");
            bw.newLine();

            // Записываем информацию о продуктах
            for (CartItem productItem : cart.getProductItemList()) {
                bw.write(String.format("%d;%s;%.2f%s;%.2f%s;%.2f%s",
                        productItem.quantity(),
                        productItem.product().getDescription(),
                        productItem.product().getPrice(),
                        usdCurrency.getSymbol(),
                        productItem.discount(),
                        usdCurrency.getSymbol(),
                        productItem.totalPrice(),
                        usdCurrency.getSymbol()));
                bw.newLine();
            }
            bw.newLine();

            // Записываем заголовок для общей информации
            bw.write("TOTAL PRICE;TOTAL DISCOUNT;TOTAL WITH DISCOUNT");
            bw.newLine();

            bw.write(String.format("%.2f%s;%.2f%s;%.2f%s",
                    cart.getTotalPrice(),
                    usdCurrency.getSymbol(),
                    cart.getDiscountAmount(),
                    usdCurrency.getSymbol(),
                    cart.getTotalWithDiscountAmount(),
                    usdCurrency.getSymbol()));
            bw.newLine();

        } catch (IOException e) {
            System.out.println("CSVResultWriter has an error");
            throw new RuntimeException(e);
        }
    }

    /**
     * Записывает сообщение об ошибке в CSV-файл.
     *
     * @param errorMessage сообщение об ошибке
     */
    public static void writeError(String errorMessage) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RESULT_FILE))) {
            bw.write(errorMessage);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("CSVResultWriter has an error");
            throw new RuntimeException(e);
        }
    }


}
