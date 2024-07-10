package main.java.ru.clevertec.check;

import main.java.ru.clevertec.exception.BadRequest;
import main.java.ru.clevertec.exception.InternalServerError;
import main.java.ru.clevertec.exception.NotEnoughMoney;
import main.java.ru.clevertec.file.writer.Writer;
import main.java.ru.clevertec.file.writer.WriterFactory;
import main.java.ru.clevertec.model.cart.*;
import main.java.ru.clevertec.model.DiscountCard;
import main.java.ru.clevertec.model.Product;
import main.java.ru.clevertec.services.CartPrinter;
import main.java.ru.clevertec.services.CartWriter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

public class CheckRunner {

    public static void main(String[] args) {
        String saveToFile = CommandLineArgumentsParser.getSaveToFile(args);

        Writer objWriter = WriterFactory.createWriter(saveToFile);

        setWriter(objWriter);

        if (!BadRequest.validateInput(args)) return;

        BigDecimal debitCardBalance = CommandLineArgumentsParser.getDebitCardBalanceFromCommandLineArguments(args).setScale(2, RoundingMode.FLOOR);

        if (debitCardBalance.compareTo(BigDecimal.valueOf(-1)) == 0) {
            return;
        }

        Map<Integer, Integer> productMap = CommandLineArgumentsParser.getIdProductAndQuantityFromCommandLine(args);

        String pathToFile = CommandLineArgumentsParser.getPathToFile(args);
        if (pathToFile == null) {
            return;
        }

        List<Product> productList = CommandLineArgumentsParser.getProductsFromMap(pathToFile, productMap);
        if (productList == null) {
            return;
        }

        DiscountCard discountCard = CommandLineArgumentsParser.getDiscountCardFromCommandLineArguments(args);

        CartBuilder cartBuilder = new CartBuilder();
        cartBuilder.setDiscountCard(discountCard);

        for (Product product : productList) {
            cartBuilder.addProduct(productMap.get(product.getId()), product);
        }

        Cart cart = CartFactoryMethod.createCart(cartBuilder);

        if (!NotEnoughMoney.getNotEnoughMoney(debitCardBalance, cart.getTotalWithDiscountAmount())) return;

        CartWriter.writeResult(objWriter, cart);
        CartPrinter.showCart(debitCardBalance, cart);

        debitCardBalance = updateDebitCardBalance(debitCardBalance, cart.getTotalWithDiscountAmount());
    }

    private static BigDecimal updateDebitCardBalance(BigDecimal debitCardBalance, BigDecimal totalWithDiscountAmount) {
        return debitCardBalance.subtract(totalWithDiscountAmount).setScale(2, RoundingMode.FLOOR);
    }

    private static void setWriter(Writer objWriter) {
        BadRequest.setObjWriter(objWriter);
        NotEnoughMoney.setObjWriter(objWriter);
        InternalServerError.setObjWriter(objWriter);
    }
}