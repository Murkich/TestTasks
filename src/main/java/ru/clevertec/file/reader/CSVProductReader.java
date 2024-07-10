package main.java.ru.clevertec.file.reader;

import main.java.ru.clevertec.exception.InternalServerError;
import main.java.ru.clevertec.model.Product;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Класс CSVProductReader предназначен для чтения данных о товаре из CSV-файла.
 */
public class CSVProductReader {
    private static final String DEFAULT_PRODUCTS_FILE = "./src/main/resources/products.csv";
    private static final String DELIMITER = ";";

    /**
     * Читает данные о товаре по ее id из CSV-файла.
     *
     * @param filePath  путь к CSV-файлу
     * @param productId id товара
     * @return объект Product, содержащий данные о товаре, или null, если товар не найден
     */
    public static Product getProductById(String filePath, int productId) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Пропускаем заголовок файла
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(DELIMITER);
                if (Integer.parseInt(data[0]) == productId) {
                    return new Product.ProductBuilder()
                            .setId(productId)
                            .setDescription(data[1])
                            .setPrice(new BigDecimal(data[2]).setScale(2, RoundingMode.FLOOR))
                            .setQuantityInStock(Integer.parseInt(data[3]))
                            .setWholesale(Boolean.parseBoolean(data[4]))
                            .build();
                }
            }
        } catch (FileNotFoundException e) {
            InternalServerError.writeOtherError("Products file does not found");
            throw new RuntimeException(e);
        } catch (IOException e) {
            InternalServerError.writeOtherError("CSVProductReader has an error");
            throw new RuntimeException(e);
        }

        // Если товар не найден, возвращаем null
        return null;
    }
}
