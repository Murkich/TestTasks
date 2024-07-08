package main.java.ru.clevertec.file.writer;

import main.java.ru.clevertec.exception.InternalServerError;
import main.java.ru.clevertec.model.cart.Cart;
import main.java.ru.clevertec.model.cart.CartItem;
import main.java.ru.clevertec.util.currency.Currency;
import main.java.ru.clevertec.util.currency.USDFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Класс CSVResultWriter предназначен для записи результатов покупки в CSV-файл.
 */
public class CSVResultWriter implements Writer {
    private static final String DEFAULT_RESULT_FILE = "./result.csv";
    private static final String DELIMITER = ";";
    private static String filePath;

    /**
     * Создает объект CSVResultWriter с заданным путем к файлу
     *
     * @param filePath путь к CSV-файлу
     */
    public CSVResultWriter(String filePath) {
        CSVResultWriter.filePath = filePath == null ? DEFAULT_RESULT_FILE : filePath;

        createFileIfNotExists();
    }

    /**
     * Записывает информацию о покупке в CSV-файл.
     *
     * @param cart объект Cart, содержащий информацию о покупке
     */
    @Override
    public void writeResult(Cart cart) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            USDFactory usdFactory = new USDFactory();
            Currency usdCurrency = usdFactory.createCurrency();

            // Записываем дату и время
            bw.write("DATE" + DELIMITER + "TIME");
            bw.newLine();
            bw.write(cart.getCurrentDateTime());
            bw.newLine();
            bw.newLine();

            // Записываем заголовок для продуктов
            bw.write("QTY" + DELIMITER +
                    "DESCRIPTION" + DELIMITER +
                    "PRICE" + DELIMITER +
                    "DISCOUNT" + DELIMITER +
                    "TOTAL");
            bw.newLine();

            // Записываем информацию о продуктах
            for (CartItem productItem : cart.getProductItemList()) {
                bw.write(String.format("%d%s%s%s%.2f%s%s%.2f%s%s%.2f%s",
                        productItem.quantity(), DELIMITER,
                        productItem.product().getDescription(), DELIMITER,
                        productItem.product().getPrice(),
                        usdCurrency.getSymbol(), DELIMITER,
                        productItem.discount(),
                        usdCurrency.getSymbol(), DELIMITER,
                        productItem.totalPrice(),
                        usdCurrency.getSymbol()));
                bw.newLine();
            }
            bw.newLine();

            // Записываем заголовок для общей информации
            bw.write("TOTAL PRICE" + DELIMITER +
                    "TOTAL DISCOUNT" + DELIMITER +
                    "TOTAL WITH DISCOUNT");
            bw.newLine();

            bw.write(String.format("%.2f%s%s%.2f%s%s%.2f%s",
                    cart.getTotalPrice(),
                    usdCurrency.getSymbol(), DELIMITER,
                    cart.getDiscountAmount(),
                    usdCurrency.getSymbol(), DELIMITER,
                    cart.getTotalWithDiscountAmount(),
                    usdCurrency.getSymbol()));
            bw.newLine();

        } catch (IOException e) {
            InternalServerError.writeOtherError("CSVResultWriter has an error");
            throw new RuntimeException(e);
        }
    }

    /**
     * Записывает сообщение об ошибке в CSV-файл.
     *
     * @param errorMessage сообщение об ошибке
     */
    public void writeError(String errorMessage) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write(errorMessage);
            bw.newLine();
        } catch (IOException e) {
            InternalServerError.writeOtherError("CSVResultWriter has an error");
            throw new RuntimeException(e);
        }
    }

    /**
     * Создает файл с указанным путем, если его не существует.
     */
    private void createFileIfNotExists() {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                File parentDir = file.getParentFile();
                if (!parentDir.exists()) {
                    parentDir.mkdirs();
                }
                file.createNewFile();
            } catch (IOException e) {
                InternalServerError.writeOtherError("Error creating file: " + e.getMessage());
            }
        }
    }
}
