package main.java.ru.clevertec.check;

import main.java.ru.clevertec.exception.BadRequest;
import main.java.ru.clevertec.exception.NotEnoughMoney;
import main.java.ru.clevertec.file.reader.CSVDiscountCardReader;
import main.java.ru.clevertec.file.reader.CSVProductReader;
import main.java.ru.clevertec.model.discountcard.DiscountCard;
import main.java.ru.clevertec.model.products.Product;

import java.math.BigDecimal;
import java.util.*;

/**
 * Класс CommandLineArgumentsParser предназначен для извлечения и обработки данных из аргументов командной строки.
 * Он содержит методы для получения идентификаторов продуктов и их количества, списка продуктов, дисконтной карты
 * и баланса дебетовой карты из аргументов командной строки.
 */
public class CommandLineArgumentsParser {
    /**
     * Извлекает идентификаторы продуктов и их количество из аргументов командной строки.
     *
     * @param args определяет аргументы командной строки
     * @return map, содержащую идентификаторы продуктов и соответствующие им количества
     */
    static Map<Integer, Integer> getIdProductAndQuantityFromCommandLine(String[] args) {
        Map<Integer, Integer> products = new LinkedHashMap<>();

        Arrays.stream(args)
                .filter(arg -> arg.contains("-"))
                .forEach(productAndQuantityString -> {
                    String[] productIdAndQuantity = productAndQuantityString.split("-");
                    int productId = Integer.parseInt(productIdAndQuantity[0]);
                    int quantity = Integer.parseInt(productIdAndQuantity[1]);
                    addProduct(products, productId, quantity);
                });

        return products;
    }

    /**
     * Извлекает список продуктов из заданной карты продуктов.
     *
     * @param filePath путь к файлу списка продуктов
     * @param productMap map, содержащая идентификаторы продуктов и их соответствующие количества
     * @return список продуктов или пустой список, если какой-либо продукт не найден или его недостаточно на складе
     */
    static List<Product> getProductsFromMap(String filePath, Map<Integer, Integer> productMap) {
        List<Product> products = new ArrayList<>();

        for (Map.Entry<Integer, Integer> productIdAndQuantity : productMap.entrySet()) {
            int productId = productIdAndQuantity.getKey();
            int quantity = productIdAndQuantity.getValue();

            Product product = CSVProductReader.getProductById(filePath, productId);

            if (product != null) {
                products.add(product);
            } else {
                BadRequest.productNotFound();
                return null;
            }

            if (!BadRequest.quantityNotEnoughInStock(product.getDescription(), product.getQuantityInStock(), quantity)) {
                return null;
            }
        }
        return products;
    }

    /**
     * Добавляет продукт в map или обновляет его количество, если он уже существует.
     *
     * @param products  map, содержащая продукты и их количество
     * @param productId идентификатор продукта
     * @param quantity  количество продукта
     */
    private static void addProduct(Map<Integer, Integer> products, int productId, int quantity) {
        products.merge(productId, quantity, Integer::sum);
    }

    /**
     * Извлекает дисконтную карту из аргументов командной строки.
     *
     * @param args определяет аргументы командной строки
     * @return дисконтную карту
     */
    static DiscountCard getDiscountCardFromCommandLineArguments(String[] args) {
        String discountCardArgument = Arrays.stream(args)
                .filter(arg -> arg.startsWith("discountCard="))
                .findFirst()
                .orElse(null);

        if (discountCardArgument == null) {
            return null;
        }

        String cardNumber = discountCardArgument.split("=")[1];

        return CSVDiscountCardReader.readDiscountCardByNumber(cardNumber);
    }

    /**
     * Извлекает баланс дебетовой карты из аргументов командной строки.
     *
     * @param args определяет аргументы командной строки
     * @return баланс дебетовой карты или BigDecimal.ZERO
     */
    static BigDecimal getDebitCardBalanceFromCommandLineArguments(String[] args) {
        String balanceArgument = Arrays.stream(args)
                .filter(arg -> arg.startsWith("balanceDebitCard="))
                .findFirst()
                .orElse(null);

        if (balanceArgument == null) {
            return BigDecimal.ZERO;
        }

        String balanceString = balanceArgument.split("=")[1];

        BigDecimal balance = new BigDecimal(balanceString);
        return NotEnoughMoney.validateAmount(balance);
    }

    /**
     * Извлекает путь к файлу сохранения из аргументов командной строки.
     *
     * @param args определяет аргументы командной строки
     * @return путь к файлу сохранения
     */
    static String getPathToFile(String[] args) {
        String pathToFile = Arrays.stream(args)
                .filter(arg -> arg.startsWith("pathToFile="))
                .findFirst()
                .orElse(null);

        String nameFile = pathToFile.split("=")[1];

        return BadRequest.fileExist(nameFile) ? nameFile : null;
    }

    /**
     * Извлекает путь к файлу сохранения из аргументов командной строки.
     *
     * @param args определяет аргументы командной строки
     * @return путь к файлу сохранения
     */
    static String getSaveToFile(String[] args) {
        String saveToFile = Arrays.stream(args)
                .filter(arg -> arg.startsWith("saveToFile="))
                .findFirst()
                .orElse(null);

        return saveToFile == null ? null : saveToFile.split("=")[1];
    }
}