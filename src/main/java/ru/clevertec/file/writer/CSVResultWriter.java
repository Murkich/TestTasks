package main.java.ru.clevertec.file.writer;

import main.java.ru.clevertec.model.cart.Cart;
import main.java.ru.clevertec.model.cart.CartItem;
import main.java.ru.clevertec.util.currency.Currency;
import main.java.ru.clevertec.util.currency.USDFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Класс CSVResultWriter предназначен для записи результатов покупки в CSV-файл.
 */
public class CSVResultWriter implements Writer {
    private static final String RESULT_FILE = "./result.csv";
    private static final String DELIMITER = ";";
    private static final String DATE_HEADER = "DATE" + DELIMITER + "TIME";
    private static final String PRODUCT_HEADER = "QTY" + DELIMITER + "DESCRIPTION" + DELIMITER + "PRICE" + DELIMITER + "DISCOUNT" + DELIMITER + "TOTAL";
    private static final String TOTAL_HEADER = "TOTAL PRICE" + DELIMITER + "TOTAL DISCOUNT" + DELIMITER + "TOTAL WITH DISCOUNT";

    /**
     * Записывает информацию о покупке в CSV-файл.
     *
     * @param cart объект Cart, содержащий информацию о корзине покупок
     */
    @Override
    public void writeResult(Cart cart) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RESULT_FILE))) {
            USDFactory usdFactory = new USDFactory();
            Currency usdCurrency = usdFactory.createCurrency();

            // Записываем дату и время
            writeDateTime(bw, cart.getCurrentDateTime());

            // Записываем информацию о продуктах
            writeProductItems(bw, cart.getProductItemList(), usdCurrency);

            // Записываем общую информацию (общая цена, общая скидка, общая цена со скидкой) о покупке
            writeTotalInfo(bw, cart, usdCurrency);

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

    /**
     * Записывает дату и время в CSV-файл.
     *
     * @param bw       BufferedWriter для записи в файл
     * @param dateTime строка с датой и временем
     * @throws IOException если произошла ошибка при записи в файл
     */
    private void writeDateTime(BufferedWriter bw, String dateTime) throws IOException {
        bw.write(DATE_HEADER);
        bw.newLine();

        bw.write(dateTime);
        bw.newLine();
        bw.newLine();
    }

    /**
     * Записывает информацию о продуктах в CSV-файл.
     *
     * @param bw           BufferedWriter для записи в файл
     * @param productItems список продуктов в корзине
     * @param currency     объект Currency для получения символа валюты
     * @throws IOException если произошла ошибка при записи в файл
     */
    private void writeProductItems(BufferedWriter bw, List<CartItem> productItems, Currency currency) throws IOException {
        bw.write(PRODUCT_HEADER);
        bw.newLine();

        StringBuilder sb = new StringBuilder();
        for (CartItem productItem : productItems) {
            sb.append(productItem.quantity()).append(DELIMITER)
                    .append(productItem.product().getDescription()).append(DELIMITER)
                    .append(productItem.product().getPrice()).append(currency.getSymbol()).append(DELIMITER)
                    .append(productItem.discount()).append(currency.getSymbol()).append(DELIMITER)
                    .append(productItem.totalPrice()).append(currency.getSymbol())
                    .append(System.lineSeparator());
        }
        bw.write(sb.toString());
        bw.newLine();
    }

    /**
     * Записывает общую информацию (общая цена, общая скидка, общая цена со скидкой) в CSV-файл.
     *
     * @param bw       BufferedWriter для записи в файл
     * @param cart     объект Cart, содержащий информацию о корзине покупок
     * @param currency объект Currency для получения символа валюты
     * @throws IOException если произошла ошибка при записи в файл
     */
    private void writeTotalInfo(BufferedWriter bw, Cart cart, Currency currency) throws IOException {
        bw.write(TOTAL_HEADER);
        bw.newLine();

        String totalLine = String.format("%.2f%s%s%.2f%s%s%.2f%s",
                cart.getTotalPrice(), currency.getSymbol(), DELIMITER,
                cart.getDiscountAmount(), currency.getSymbol(), DELIMITER,
                cart.getTotalWithDiscountAmount(), currency.getSymbol());
        bw.write(totalLine);
        bw.newLine();
    }
}
