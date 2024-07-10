package main.java.ru.clevertec.file.reader;

import main.java.ru.clevertec.exception.BadRequest;
import main.java.ru.clevertec.exception.InternalServerError;
import main.java.ru.clevertec.model.Product;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static main.java.ru.clevertec.constants.Constants.*;

public class CSVProductReader {
    public static List<Product> getAllProduct(String filePath) throws InternalServerError {
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            return lines.skip(1).map(line -> line.split(DELIMITER))
                    .map(data -> new Product.ProductBuilder()
                            .setId(Integer.parseInt(data[0]))
                            .setDescription(data[1])
                            .setPrice(new BigDecimal(data[2]).setScale(2, ROUND))
                            .setQuantityInStock(Integer.parseInt(data[3]))
                            .setWholesale(Boolean.parseBoolean(data[4]))
                            .build())
                    .collect(Collectors.toList());
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new InternalServerError("Error format in product's file");
        } catch (IOException e) {
            throw new InternalServerError("Error reading products.csv file" + e);
        }
    }

    public static Product findProductById(List<Product> allProducts, int productId) throws BadRequest {
        return allProducts.stream()
                .filter(product -> product.getId() == productId)
                .findFirst()
                .orElseThrow(() -> new BadRequest("Product with ID " + productId + " didn't find"));
    }
}
