package main.java.ru.clevertec.controller;

import main.java.ru.clevertec.exception.BadRequest;
import main.java.ru.clevertec.exception.InternalServerError;
import main.java.ru.clevertec.exception.NotEnoughMoney;
import main.java.ru.clevertec.file.reader.CSVDiscountCardReader;
import main.java.ru.clevertec.file.reader.CSVProductReader;
import main.java.ru.clevertec.model.DiscountCard;
import main.java.ru.clevertec.model.Product;
import main.java.ru.clevertec.util.MoneyValidator;
import main.java.ru.clevertec.util.QuantityValidator;

import java.math.BigDecimal;
import java.util.*;

import static main.java.ru.clevertec.constants.Constants.*;

public class CommandLineArgumentsParser {
    static Map<Integer, Integer> getIdProductAndQuantityFromCommandLine(String[] args) {
        Map<Integer, Integer> products = new LinkedHashMap<>();

        Arrays.stream(args)
                .filter(arg -> arg.contains(ID_QTY_SPLIT_CHAR))
                .forEach(productAndQuantityString -> {
                    String[] productIdAndQuantity = productAndQuantityString.split(ID_QTY_SPLIT_CHAR);
                    int productId = Integer.parseInt(productIdAndQuantity[0]);
                    int quantity = Integer.parseInt(productIdAndQuantity[1]);
                    addProduct(products, productId, quantity);
                });
        return products;
    }

    static List<Product> getProductsFromMap(Map<Integer, Integer> productMap) throws BadRequest, InternalServerError {
        List<Product> products = new ArrayList<>();
        List<Product> productsFromFile = CSVProductReader.getAllProduct();

        if (productsFromFile.isEmpty()) {
            throw new InternalServerError("products.csv is null");
        }

        for (Map.Entry<Integer, Integer> productIdAndQuantity : productMap.entrySet()) {
            int productId = productIdAndQuantity.getKey();
            int quantity = productIdAndQuantity.getValue();
            Product product = CSVProductReader.findProductById(productsFromFile, productId);
            products.add(product);
            QuantityValidator.quantityNotEnoughInStock(product.getDescription(), product.getQuantityInStock(), quantity);
        }
        return products;
    }

    private static void addProduct(Map<Integer, Integer> products, int productId, int quantity) {
        products.merge(productId, quantity, Integer::sum);
    }

    static DiscountCard getDiscountCardFromCommandLineArguments(String[] args) throws InternalServerError {
        String discountCardArgument = Arrays.stream(args)
                .filter(arg -> arg.startsWith(DISCOUNT_CARD_START))
                .findFirst()
                .orElse(null);

        if (discountCardArgument == null) {
            return null;
        }
        String cardNumber = discountCardArgument.split(SPLIT_CHAR)[1];

        return CSVDiscountCardReader.readDiscountCardByNumber(cardNumber);
    }

    static BigDecimal getDebitCardBalanceFromCommandLineArguments(String[] args) throws NotEnoughMoney {
        String balanceArgument = Arrays.stream(args)
                .filter(arg -> arg.startsWith(BALANCE_CARD_START))
                .findFirst()
                .orElse(null);
        String balanceString = balanceArgument.split(SPLIT_CHAR)[1];
        BigDecimal balance = new BigDecimal(balanceString);

        return MoneyValidator.validateAmount(balance);
    }
}