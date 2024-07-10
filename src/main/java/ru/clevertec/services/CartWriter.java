package main.java.ru.clevertec.services;

import main.java.ru.clevertec.file.writer.CSVResultWriterFactory;
import main.java.ru.clevertec.file.writer.factory.Writer;
import main.java.ru.clevertec.model.cart.Cart;

public class CartWriter {
    public static void writeResult(Cart cart) {
        Writer csvResultWriter = new CSVResultWriterFactory().createWriter();

        csvResultWriter.writeResult(cart);
    }
}
