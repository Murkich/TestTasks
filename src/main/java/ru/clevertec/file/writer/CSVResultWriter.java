package main.java.ru.clevertec.file.writer;

import main.java.ru.clevertec.file.writer.factory.Writer;
import main.java.ru.clevertec.model.cart.Cart;
import main.java.ru.clevertec.model.cart.CartItem;
import main.java.ru.clevertec.model.currency.factory.Currency;
import main.java.ru.clevertec.model.currency.USDFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static main.java.ru.clevertec.constants.Constants.DELIMITER;
import static main.java.ru.clevertec.constants.Constants.RESULT_FILE;

public class CSVResultWriter implements Writer {
    private static final String DATE_HEADER = "DATE" + DELIMITER + "TIME";
    private static final String PRODUCT_HEADER = "QTY" + DELIMITER + "DESCRIPTION" + DELIMITER + "PRICE" + DELIMITER + "DISCOUNT" + DELIMITER + "TOTAL";
    private static final String TOTAL_HEADER = "TOTAL PRICE" + DELIMITER + "TOTAL DISCOUNT" + DELIMITER + "TOTAL WITH DISCOUNT";

    @Override
    public void writeResult(Cart cart) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RESULT_FILE))) {
            USDFactory usdFactory = new USDFactory();
            Currency usdCurrency = usdFactory.createCurrency();

            writeDateTime(bw, cart.getCurrentDateTime());
            writeProductItems(bw, cart.getProductItemList(), usdCurrency);
            writeTotalInfo(bw, cart, usdCurrency);
        } catch (IOException e) {
            System.out.println("CSVResultWriter has an error");
            throw new RuntimeException(e);
        }
    }

    public static void writeError(String message) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RESULT_FILE))) {
            bw.write(message);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("CSVResultWriter has an error");
            throw new RuntimeException(e);
        }
    }

    private void writeDateTime(BufferedWriter bw, String dateTime) throws IOException {
        bw.write(DATE_HEADER + "\n" + dateTime + "\n\n");
    }

    private void writeProductItems(BufferedWriter bw, List<CartItem> productItems, Currency currency) throws IOException {
        bw.write(PRODUCT_HEADER + "\n");

        StringBuilder sb = new StringBuilder();
        for (CartItem productItem : productItems) {
            sb.append(productItem.quantity()).append(DELIMITER)
                    .append(productItem.product().getDescription()).append(DELIMITER)
                    .append(productItem.product().getPrice()).append(currency.getSymbol()).append(DELIMITER)
                    .append(productItem.discount()).append(currency.getSymbol()).append(DELIMITER)
                    .append(productItem.totalPrice()).append(currency.getSymbol())
                    .append(System.lineSeparator());
        }
        bw.write(sb + "\n");
    }

    private void writeTotalInfo(BufferedWriter bw, Cart cart, Currency currency) throws IOException {
        bw.write(TOTAL_HEADER + "\n");

        String totalLine = String.format("%.2f%s%s%.2f%s%s%.2f%s",
                cart.getTotalPrice(), currency.getSymbol(), DELIMITER,
                cart.getDiscountAmount(), currency.getSymbol(), DELIMITER,
                cart.getTotalWithDiscountAmount(), currency.getSymbol());
        bw.write(totalLine + "\n");
    }
}
